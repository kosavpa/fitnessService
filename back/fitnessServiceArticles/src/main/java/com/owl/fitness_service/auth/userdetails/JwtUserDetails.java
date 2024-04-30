package com.owl.fitness_service.auth.userdetails;


import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@SuppressWarnings("unused")

@Builder
@Getter
public class JwtUserDetails implements UserDetails {
    private String username;

    private List<String> roles;

    public void setRoles(List<String> roles) {
        if (Objects.isNull(roles) || roles.isEmpty()) {
            throw new RuntimeException("Incorrect roles!");
        }

        this.roles = roles;
    }

    public void setUsername(String username) {
        if (Objects.isNull(username) || username.isBlank()) {
            throw new RuntimeException("Incorrect username!");
        }

        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}