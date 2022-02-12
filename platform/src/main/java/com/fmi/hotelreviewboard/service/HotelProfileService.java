package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;

import java.security.Principal;
import java.util.Collection;

public interface HotelProfileService {

    Collection<HotelProfile> getProfiles();

    HotelProfile addProfile(HotelProfile profile);

    HotelProfile getProfile(String id);

    void deleteProfile(String id);

    void addReview(Principal principal, String hotelId, String content);
}
