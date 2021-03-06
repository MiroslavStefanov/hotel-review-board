package com.fmi.hotelreviewboard.model.entity;

import com.fmi.hotelreviewboard.configuration.WebConstants;
import com.fmi.hotelreviewboard.model.UserRole;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = WebConstants.UNIQUE_USERNAME_CONSTRAINT_NAME, columnNames = "username"),
                @UniqueConstraint(name = WebConstants.UNIQUE_EMAIL_CONSTRAINT_NAME, columnNames = "email")
        })
public class User implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureLink;
    private LocalDateTime lastActive;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Set<Role> authorities;

    public User() {
        this.authorities = new HashSet<>();
        isCredentialsNonExpired = true;
        isAccountNonExpired = true;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }

    public void setProfilePictureLink(String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name="username", nullable = false, length = 20)
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "authority_id")
    )
    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Transient
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Transient
    public UserRole getRole() {
        return this.authorities
                .stream()
                .map(r -> UserRole.valueOf(r.getAuthority()))
                .max(Comparator.comparingInt(Enum::ordinal))
                .orElse(UserRole.ROLE_INVALID);
    }
}
