package com.fmi.hotelreviewboard.repository.impl;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;

import java.util.*;

public class InMemoryHotelProfileRepository implements HotelProfileRepository {

    private final Map<String, HotelProfile> profiles;

    public InMemoryHotelProfileRepository(Map<String, HotelProfile> profiles) {
        this.profiles = new HashMap<>();
        this.profiles.putAll(profiles);
    }

    @Override
    public Collection<HotelProfile> findAll() {
        if (profiles.isEmpty()) {
            return Collections.emptyList();
        }

        return profiles.values();
    }

    @Override
    public Optional<HotelProfile> findById(String id) {
        return Optional.ofNullable(profiles.get(id));
    }

    @Override
    public HotelProfile add(HotelProfile profile) {
        profiles.put(profile.getId(), profile);
        return profile;
    }
}
