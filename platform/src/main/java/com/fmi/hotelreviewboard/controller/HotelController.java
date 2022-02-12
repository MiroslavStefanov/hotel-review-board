package com.fmi.hotelreviewboard.controller;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/hotels")
public class HotelController extends BaseController {
    private static final String hotel_ACTION_AUTHORIZATION_EXPRESSION_STRING =
            "hasRole(T(com.fmi.hotelreviewboard.model.UserRole).ROLE_ADMIN.name()) ";

    private final HotelProfileService hotelProfileService;
    private final ModelMapper modelMapper;

    public HotelController(HotelProfileService hotelProfileService, ModelMapper modelMapper) {
        this.hotelProfileService = hotelProfileService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public void create(@Valid @RequestBody HotelProfile model, BindingResult bindingResult, Principal principal) {
//        if(bindingResult.hasErrors()) {
//            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
//            return super.view("hotels/form", model);
//        }
        this.hotelProfileService.addProfile(model);
    }

    @PutMapping("/{id}")
    @PreAuthorize(hotel_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public void edit(@Valid @RequestBody HotelProfile model, BindingResult bindingResult, @PathVariable("id") String id) {
//        if(bindingResult.hasErrors()) {
//            model.setAllDestinations(this.destinationService.getAllDestinationsNames());
//            return super.view("hotels/form", model);
//        }
        this.hotelProfileService.addProfile(model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(hotel_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public void delete(@PathVariable("id") String id) {
        hotelProfileService.deleteProfile(id);
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable("id") String id) {
        return super.view("hotels/details", hotelProfileService.getProfile(id));
    }

    @GetMapping()
    public ModelAndView all() {
        return super.view("hotels/all", null);
    }

    @PostMapping("/review/{id}")
    public ModelAndView addReview(@PathVariable("id") String id, @RequestBody String content, Principal principal) {
        hotelProfileService.addReview(principal, id, content);
        return super.view("hotels/details", hotelProfileService.getProfile(id));
    }
}
