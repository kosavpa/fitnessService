package com.owl.auth_service.repository.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "User")
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Логин не должен быть пустым")
    @Size(min = 4, max = 255, message = "Логин должен содержать от 4 до 20 символов")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Логин должен состоять из букв и/или цифр")

    @Column(name = "username")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 4, max = 255, message = "Пароль должен содержать от 4 до 255 символов")

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
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