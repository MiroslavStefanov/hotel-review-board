package com.fmi.hotelreviewboard.service.impl;

import com.fmi.hotelreviewboard.errorHandling.exceptions.EntityNotFoundException;
import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.model.entity.Review;
import com.fmi.hotelreviewboard.model.entity.User;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.repository.ReviewRepository;
import com.fmi.hotelreviewboard.repository.UserRepository;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
public class HotelProfileServiceImpl implements HotelProfileService {

    private final HotelProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public HotelProfileServiceImpl(HotelProfileRepository profileRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Collection<HotelProfile> getProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public HotelProfile addProfile(HotelProfile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public HotelProfile getProfile(String id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel id: " + id));

    }

    @Override
    public void deleteProfile(String id) {
        profileRepository.deleteById(id);
    }

    @Override
    public void addReview(Principal principal, String hotelId, String content) {
        User user = userRepository.findFirstByUsername(principal.getName());
        HotelProfile hotel = profileRepository.findById(hotelId).orElseThrow();
        Review review = new Review();
        review.setHotel(hotel);
        review.setPublisher(user);
        review.setContent(content);

        //TODO: rating

        reviewRepository.save(review);
    }

}
