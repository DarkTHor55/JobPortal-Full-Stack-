package com.JobPortal.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements AuthenticationProvider {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = (String) authentication.getCredentials();
        String email = jwtUtils.extractUsername(jwt);
        System.out.println(jwt);
        System.out.println(email);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (jwtUtils.validateToken(jwt, userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid JWT token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}
