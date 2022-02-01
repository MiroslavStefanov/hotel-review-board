package com.fmi.hotelreviewboard.repository;

import com.fmi.hotelreviewboard.model.HotelProfile;

import java.util.Collection;
import java.util.Optional;

public interface HotelProfileRepository {

    Collection<HotelProfile> findAll();

    Optional<HotelProfile> findById(String id);

    HotelProfile add(HotelProfile profile);
}
