package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.Collection;

public interface HotelProfileService {

    Collection<String> getAllNames();

    Page<HotelProfile> searchProfiles(String nameLike, Pageable pageable);

    HotelProfile addProfile(HotelProfile profile);

    HotelProfile getProfile(String id);

    void deleteProfile(String id);

    String addReview(Principal principal, String hotelId, String content);

    Page<Review> getReviews(String hotelId, Pageable pageable);
}
