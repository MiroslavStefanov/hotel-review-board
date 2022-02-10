package com.fmi.hotelreviewboard.service.impl;

import com.fmi.hotelreviewboard.model.UserRole;
import com.fmi.hotelreviewboard.model.entity.Role;
import com.fmi.hotelreviewboard.repository.RoleRepository;
import com.fmi.hotelreviewboard.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRole getRole(String roleName) {
        Role role = this.roleRepository.findFirstByAuthority(roleName);
        if(role == null)
            return UserRole.ROLE_INVALID;
        return UserRole.valueOf(role.getAuthority());
    }
}
