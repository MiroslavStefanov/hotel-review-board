package com.fmi.hotelreviewboard.service;

import com.fmi.hotelreviewboard.model.UserRole;
import com.fmi.hotelreviewboard.model.entity.HotelProfile;
import com.fmi.hotelreviewboard.model.entity.Role;
import com.fmi.hotelreviewboard.model.entity.User;
import com.fmi.hotelreviewboard.repository.HotelProfileRepository;
import com.fmi.hotelreviewboard.repository.RoleRepository;
import com.fmi.hotelreviewboard.repository.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DatabaseSeeder {
    private static final String ROOT_USER_USERNAME = "root";
    private static final String ROOT_USER_PASSWORD = "rootPsw1234";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HotelProfileRepository hotelProfileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DatabaseSeeder(UserRepository userRepository, RoleRepository roleRepository, HotelProfileRepository hotelProfileRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hotelProfileRepository = hotelProfileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener
    public void handleContextRefreshListener(ContextRefreshedEvent event) throws IOException {
        this.seedRoles();
        this.seedRootUser();
        this.seedHotels();
    }

    private void seedRoles() {
        if(this.roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(UserRole.values())
                    .filter(r -> !r.equals(UserRole.ROLE_INVALID))
                    .map(role->new Role(role.toString()))
                    .collect(Collectors.toList());

            roles = this.roleRepository.saveAll(roles);
        }
    }

    private void seedRootUser(){
        if(this.userRepository.count() == 0) {
            User user = new User();
            user.setUsername(ROOT_USER_USERNAME);
            user.setPassword(this.bCryptPasswordEncoder.encode(ROOT_USER_PASSWORD));
            user.setFirstName("Root");
            user.setLastName("Rootov");
            Set<Role> rootRoleSet = new HashSet<>(){{
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_ADMIN.toString()));
                add(roleRepository.findFirstByAuthority(UserRole.ROLE_ROOT.toString()));
            }};
            user.setAuthorities(rootRoleSet);
            this.userRepository.saveAndFlush(user);
        }
    }

    private void seedHotels() {
        if(this.roleRepository.count() == 0) {
            List<HotelProfile> hotels = Arrays.stream(new String[] {"a", "b", "c"})
                    .map(name -> new HotelProfile(null, name, null))
                    .collect(Collectors.toList());

            hotels = this.hotelProfileRepository.saveAll(hotels);
        }
    }
}
