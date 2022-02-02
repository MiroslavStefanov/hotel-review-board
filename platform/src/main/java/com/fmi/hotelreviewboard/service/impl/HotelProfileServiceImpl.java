package com.fmi.hotelreviewboard.service.impl;

import com.fmi.hotelreviewboard.model.HotelProfile;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
//        final Optional<HotelProfile> existingProfile = profileRepository.findById(profile.getId());
//        if (existingProfile.isPresent()) {
//            throw new DuplicateBookException(book);
//        }

        final HotelProfile savedProfile = profileRepository.add(profile);
        return savedProfile;
    }
}
