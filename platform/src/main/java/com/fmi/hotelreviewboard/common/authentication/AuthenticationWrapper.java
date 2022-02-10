package com.fmi.hotelreviewboard.common.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationWrapper {
    Authentication getAuthentication();

    boolean isAuthenticated();
}
