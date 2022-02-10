package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.UserRole;

public interface RoleService {
    UserRole getRole(String roleName);
}
