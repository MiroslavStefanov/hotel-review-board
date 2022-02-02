package com.fmi.hotelreviewboard.controller;

import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelProfileController {

    private final HotelProfileService profileService;

    @Autowired
    public HotelProfileController(HotelProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profiles")
    public String viewProfiles(Model model) {
        model.addAttribute("profiles", profileService.getProfiles());
        return "view-profiles";
    }
}
