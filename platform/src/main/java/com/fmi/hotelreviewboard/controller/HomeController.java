package com.fmi.hotelreviewboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.fmi.hotelreviewboard.configuration.WebConstants.HOME_URL;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("home/index");
    }

    @GetMapping(HOME_URL)
    public ModelAndView home() {
        return super.view("home/home");
    }
}
