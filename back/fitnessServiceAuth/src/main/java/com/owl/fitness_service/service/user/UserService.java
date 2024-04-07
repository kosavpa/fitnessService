package com.owl.fitness_service.service.user;


import com.owl.fitness_service.data.UserRepository;
import com.owl.fitness_service.entity.Role;
import com.owl.fitness_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("userService")
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(repository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found"));
    }

    public void saveUser(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UsernameIsExistException();
        } else {
            prepareUser(user);

            repository.saveAndFlush(user);
        }
    }

    private void prepareUser(User user) {
        user.setRole(Role.USER);

        setEncodedPassword(user);
    }

    private void setEncodedPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }
}