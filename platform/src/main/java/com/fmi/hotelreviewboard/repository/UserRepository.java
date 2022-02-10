package com.fmi.hotelreviewboard.repository;

import com.fmi.hotelreviewboard.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findFirstByUsername(@Param("username") String username);
}
