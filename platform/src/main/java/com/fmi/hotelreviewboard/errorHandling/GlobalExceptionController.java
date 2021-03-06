package com.fmi.hotelreviewboard.errorHandling;

import com.fmi.hotelreviewboard.controller.BaseController;
import com.fmi.hotelreviewboard.model.binding.UserEditBindingModel;
import com.fmi.hotelreviewboard.model.binding.UserRegisterBindingModel;
import com.fmi.hotelreviewboard.model.dto.UserServiceModel;
import com.fmi.hotelreviewboard.service.UserService;
import org.modelmapper.ModelMapper;
import com.fmi.hotelreviewboard.common.authentication.AuthenticationWrapper;
import com.fmi.hotelreviewboard.errorHandling.exceptions.EntityNotFoundException;
import com.fmi.hotelreviewboard.errorHandling.exceptions.UserEmailAlreadyUsedException;
import com.fmi.hotelreviewboard.errorHandling.exceptions.UserException;
import com.fmi.hotelreviewboard.errorHandling.exceptions.UsernameAlreadyUsedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@PropertySource("classpath:ValidationMessages.properties")
public class GlobalExceptionController extends BaseController {
    private final ModelMapper modelMapper;
    private final AuthenticationWrapper authenticationWrapper;
    private final UserService userService;

    @Value("${com.fmi.hotelreviewboard.validation.File.too-large}")
    private String tooLargeFileMessage;

    public GlobalExceptionController(ModelMapper modelMapper, AuthenticationWrapper authenticationWrapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.authenticationWrapper = authenticationWrapper;
        this.userService = userService;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView duplicateUsername(UserException e) {
        UserRegisterBindingModel model = this.modelMapper.map(e.getServiceModel(), UserRegisterBindingModel.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(model, DEFAULT_MODEL_NAME);

        if(e instanceof UserEmailAlreadyUsedException)
            bindingResult.rejectValue("email", "", e.getMessage());
        else if(e instanceof UsernameAlreadyUsedException)
            bindingResult.rejectValue("username", "", e.getMessage());
        else
            return super.page("error/error-500");

        return super.view("user/register", model, DEFAULT_MODEL_NAME, bindingResult);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView accessDenied(AccessDeniedException e) {
        return super.page("error/error-403");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFount(EntityNotFoundException e) {
        return super.page("error/error-404");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public ModelAndView fileTooLarge(MaxUploadSizeExceededException e) {
        if(this.authenticationWrapper.isAuthenticated()) {
            UserServiceModel userServiceModel = this.userService.findUserByUsername(this.authenticationWrapper.getAuthentication().getName());
            UserEditBindingModel model = this.modelMapper.map(userServiceModel, UserEditBindingModel.class);
            BindingResult bindingResult = new BeanPropertyBindingResult(model, DEFAULT_MODEL_NAME);
            bindingResult.reject("", tooLargeFileMessage);
            return super.view("user/edit", model, DEFAULT_MODEL_NAME, bindingResult);
        }
        return super.page("error/error-500");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView getException(Exception e){
        e.printStackTrace();
        return super.page("error/error-500");
    }
}
