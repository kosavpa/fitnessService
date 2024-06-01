package com.owl.auth_service.repository.service.user;


import com.owl.auth_service.repository.entity.Role;
import com.owl.auth_service.repository.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("userService")
public class UserService implements UserDetailsService {
    private final EntityManager entityManager;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(EntityManager entityManager, PasswordEncoder encoder) {
        this.entityManager = entityManager;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(getUserByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User with this username not found"));
    }

    private User getUserByUsername(String username) {
        return entityManager
                .createQuery("select u from user u where u.username = :name", User.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    public void saveUser(User user) {
        if (isUserExist(user.getUsername())) {
            throw new UsernameIsExistException();
        } else {
            prepareUser(user);

            entityManager.persist(user);
        }
    }

    private boolean isUserExist(String username) {
        return entityManager
                .createQuery("select count(u.id) from user u where u.username = :name", Integer.class)
                .setParameter("name", username)
                .getSingleResult() != 0;
    }

    private void prepareUser(User user) {
        user.setRole(Role.USER);

        setEncodedPassword(user);
    }

    private void setEncodedPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }
}