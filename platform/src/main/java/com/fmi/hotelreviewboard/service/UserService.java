package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.dto.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

public interface UserService extends UserDetailsService {

    String saveUser(UserServiceModel userModel);

    UserServiceModel findUserByUsername(String username);

    void updateProfilePicture(UserServiceModel userServiceModel, MultipartFile pictureFile) throws IllegalArgumentException;

    String changeUserRole(String action, String username);

    void updateActivity(String username, LocalDateTime lastActive);

    void kickUser(String username);
}
