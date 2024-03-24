package com.owl.fitness_service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


@Component
public class UserService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Stream.of(repository.findByUsername(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found"));
    }
}