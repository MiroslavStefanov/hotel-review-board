package com.fmi.hotelreviewboard.repository;

import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelProfileRepository extends JpaRepository<HotelProfile, String> {

    Page<HotelProfile> findByNameContaining(String nameLike, Pageable pageable);
}
