package com.fmi.hotelreviewboard.controller;

import com.fmi.hotelreviewboard.configuration.WebConstants;
import com.fmi.hotelreviewboard.model.binding.UserEditBindingModel;
import com.fmi.hotelreviewboard.model.binding.UserRegisterBindingModel;
import com.fmi.hotelreviewboard.model.dto.UserServiceModel;
import com.fmi.hotelreviewboard.model.view.UserProfileViewModel;
import com.fmi.hotelreviewboard.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

import static com.fmi.hotelreviewboard.configuration.WebConstants.LOGIN_URL;

@Controller
public class UserController extends BaseController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return super.view("user/register", new UserRegisterBindingModel());
    }

    @PostMapping("/register")
    public ModelAndView registerPost(
            @Valid @ModelAttribute(DEFAULT_MODEL_NAME) UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return super.view("user/register", userRegisterBindingModel);
        } else {
            this.userService.saveUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
            return super.redirect("/login");
        }
    }

    @GetMapping(LOGIN_URL)
    public ModelAndView login(@RequestParam(required = false, name = "error") String error) {
        return super.view("user/login", error, "error");
    }

    @GetMapping("/profile/{username}")
    public ModelAndView profile(@PathVariable("username") String username) {
        return super.view(
                "user/profile",
                this.modelMapper.map(this.userService.findUserByUsername(username), UserProfileViewModel.class),
                WebConstants.USER_PROFILE_VIEW_MODEL_NAME
        );
    }

    @GetMapping("/profile/edit")
    public ModelAndView editProfile(Principal principal) {
        UserServiceModel user = this.userService.findUserByUsername(principal.getName());
        UserEditBindingModel model = this.modelMapper.map(user, UserEditBindingModel.class);
        return super.view("user/edit", model);
    }

    @PostMapping("/profile/edit")
    public ModelAndView editProfilePost(@Valid @ModelAttribute(DEFAULT_MODEL_NAME) UserEditBindingModel userEditBindingModel, BindingResult bindingResult, Principal principal) throws IllegalAccessException {
        if(bindingResult.hasErrors()) {
            return super.view("user/edit", userEditBindingModel);
        } else {
            UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
            this.modelMapper.map(userEditBindingModel, userServiceModel);
            this.userService.updateProfilePicture(userServiceModel, userEditBindingModel.getProfilePictureFile());
            this.userService.saveUser(userServiceModel);
            return super.redirect("/profile/"+principal.getName());
        }
    }

    @RequestMapping(path = "/users/{action}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(com.fmi.hotelreviewboard.specific.UserRole).ROLE_ADMIN.name())")
    public ResponseEntity<String> changeRole(@RequestBody Map<String, Object> updates, @PathVariable("action") String action) {
        return new ResponseEntity<>(this.userService.changeUserRole(action, updates.get("username").toString()), HttpStatus.OK);
    }
}
