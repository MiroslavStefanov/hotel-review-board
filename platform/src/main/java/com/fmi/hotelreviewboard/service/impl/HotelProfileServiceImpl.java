package com.fmi.hotelreviewboard.service.impl;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class HotelProfileServiceImpl implements HotelProfileService {

    private final HotelProfileRepository profileRepository;

    public HotelProfileServiceImpl(HotelProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Collection<HotelProfile> getProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public HotelProfile addProfile(HotelProfile profile) {
        return profileRepository.save(profile);
    }
}
