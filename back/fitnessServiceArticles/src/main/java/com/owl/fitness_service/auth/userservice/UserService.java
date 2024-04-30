package com.owl.fitness_service.auth.userservice;


import com.owl.fitness_service.auth.userdetails.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings("unused")
@Service("userService")
public class UserService {
    public UserDetails createUserDetails(List<String> roles, String username) {
        return JwtUserDetails.builder()
                .username(username)
                .roles(roles)
                .build();
    }
}