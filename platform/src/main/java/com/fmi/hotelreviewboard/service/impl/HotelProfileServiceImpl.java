package com.fmi.hotelreviewboard.service.impl;

import com.fmi.hotelreviewboard.errorHandling.exceptions.EntityNotFoundException;
import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.model.entity.Review;
import com.fmi.hotelreviewboard.model.entity.User;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.repository.ReviewRepository;
import com.fmi.hotelreviewboard.repository.UserRepository;
import com.fmi.hotelreviewboard.service.HotelProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class HotelProfileServiceImpl implements HotelProfileService {

    private final HotelProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final KafkaTemplate<String, String> reviewScoreTemplate;

    public HotelProfileServiceImpl(HotelProfileRepository profileRepository, UserRepository userRepository, ReviewRepository reviewRepository, KafkaTemplate<String, String> reviewScoreTemplate) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.reviewScoreTemplate = reviewScoreTemplate;
    }

    @Override
    public Collection<String> getAllNames() {
        return profileRepository.findAll().stream()
                .map(HotelProfile::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HotelProfile> searchProfiles(String nameLike, Pageable pageable) {
        return profileRepository.findByNameContaining(nameLike, pageable);
    }

    @Override
    public HotelProfile addProfile(HotelProfile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public HotelProfile getProfile(String id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel id: " + id));

    }

    @Override
    public void deleteProfile(String id) {
        profileRepository.deleteById(id);
    }

    @Override
    public String addReview(Principal principal, String hotelId, String content) {
        User user = userRepository.findFirstByUsername(principal.getName());
        HotelProfile hotel = profileRepository.findById(hotelId).orElseThrow();
        Review review = new Review();
        review.setHotel(hotel);
        review.setPublisher(user);
        review.setContent(content);
        review.setPublishedAt(Timestamp.from(Instant.now()));


        Review savedReview = reviewRepository.save(review);
        reviewScoreTemplate.send("review", savedReview.getId());
        return savedReview.getId();
    }

    @Override
    public Page<Review> getReviews(String hotelId, Pageable pageable) {
        HotelProfile hotel = profileRepository.findById(hotelId).orElseThrow();
        return reviewRepository.findByHotelOrderByPublishedAtDesc(hotel, pageable);
    }

    @KafkaListener(
            topics = "review",
            containerFactory = "fooKafkaListenerContainerFactory")
    public void greetingListener(String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setScore((int) Math.floor(Math.random()*10+1));
        reviewRepository.save(review);
    }

}
