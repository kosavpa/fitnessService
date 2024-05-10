package com.owl.fitness_service.auth.userservice;


import com.owl.fitness_service.auth.jwtservice.JwtTokenService;
import com.owl.fitness_service.auth.userdetails.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings("unused")
@Service("authService")
public class AuthService {
    private final JwtTokenService jwtService;

    @Autowired
    public AuthService(JwtTokenService jwtService) {
        this.jwtService = jwtService;
    }

    private UserDetails createUserDetails(List<String> roles, String username) {
        return JwtUserDetails.builder()
                .username(username)
                .roles(roles)
                .build();
    }

    public Authentication createAuthByJwt(String jwt) {

        UserDetails principal = createUserDetails(
                jwtService.getRoleListByToken(jwt),
                jwtService.getUsernameByToken(jwt));

        return new UsernamePasswordAuthenticationToken(
                principal,
                principal.getPassword(),
                principal.getAuthorities());
    }
}