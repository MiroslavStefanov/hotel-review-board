package com.fmi.hotelreviewboard.repository;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelProfileRepository extends JpaRepository<HotelProfile, String> {

}
