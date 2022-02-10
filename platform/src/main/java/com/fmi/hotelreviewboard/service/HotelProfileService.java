package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;

import java.util.Collection;

public interface HotelProfileService {

    Collection<HotelProfile> getProfiles();

    HotelProfile addProfile(HotelProfile profile);

}
