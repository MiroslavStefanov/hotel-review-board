package com.fmi.hotelreviewboard.configuration;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.repository.impl.InMemoryHotelProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class BasicConfiguration {

    @Bean
    public HotelProfileRepository provideHotelProfileRepository() {
        return new InMemoryHotelProfileRepository(initialProfiles());
    }

    private static Map<String, HotelProfile> initialProfiles() {
        Map<String, HotelProfile> initData = new HashMap<>();
        for(int i = 0; i<5; i++)
        {
            UUID id = UUID.randomUUID();
            initData.put(id.toString(), new HotelProfile(id.toString(), "Hotel " + String.valueOf(i), "Review"));
        }
        return initData;
    }
}
