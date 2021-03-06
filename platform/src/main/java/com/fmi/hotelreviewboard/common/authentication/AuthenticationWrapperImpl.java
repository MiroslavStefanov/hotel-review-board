package com.fmi.hotelreviewboard.common.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationWrapperImpl implements AuthenticationWrapper {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isAuthenticated() {
        return (this.getAuthentication() != null) && !(this.getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
