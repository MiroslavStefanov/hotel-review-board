package com.fmi.hotelreviewboard.controller;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.model.view.HotelProfileViewModel;
import com.fmi.hotelreviewboard.model.view.ReviewViewModel;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

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
        this.hotelProfileService.addProfile(model);
    }

    @PutMapping("/{id}")
    @PreAuthorize(hotel_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public void edit(@Valid @RequestBody HotelProfile model, BindingResult bindingResult, @PathVariable("id") String id) {
        this.hotelProfileService.addProfile(model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(hotel_ACTION_AUTHORIZATION_EXPRESSION_STRING)
    public void delete(@PathVariable("id") String id) {
        hotelProfileService.deleteProfile(id);
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id) {
        HotelProfile profile = hotelProfileService.getProfile(id);
        HotelProfileViewModel viewModel = modelMapper.map(profile, HotelProfileViewModel.class);
        return super.view("hotels/details", viewModel);
    }

    @GetMapping()
    public ModelAndView all() {
        Collection<String> allHotelNames = hotelProfileService.getAllNames();
        return super.view("hotels/all", allHotelNames);
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<HotelProfileViewModel> search(@Param("name") String name, Pageable pageable) {
        return hotelProfileService.searchProfiles(name, pageable)
                .map(h -> modelMapper.map(h, HotelProfileViewModel.class));
    }

    @PostMapping("/review/{id}")
    @ResponseBody
    public String addReview(@PathVariable("id") String hotelId, @RequestBody String content, Principal principal) {
        return hotelProfileService.addReview(principal, hotelId, content);
//        return super.view("hotels/details", hotelProfileService.getProfile(id));
    }

    @GetMapping("/review/{id}")
    @ResponseBody
    public Page<ReviewViewModel> getReviews(@PathVariable("id") String hotelId, Pageable pageable) {
        return hotelProfileService.getReviews(hotelId, pageable)
                .map(r -> modelMapper.map(r, ReviewViewModel.class));
    }
}
