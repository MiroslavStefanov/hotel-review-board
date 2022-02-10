package com.fmi.hotelreviewboard.repository;

import com.fmi.hotelreviewboard.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByAuthority(String name);
}
