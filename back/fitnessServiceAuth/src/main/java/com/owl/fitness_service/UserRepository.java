package com.owl.fitness_service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String userName);
    boolean existsByUsername(String username);
}