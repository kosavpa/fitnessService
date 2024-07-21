package owl.home.fitnessService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service("authService")
public class AuthService {
    private final JwtTokenService jwtService;

    @Autowired
    public AuthService(JwtTokenService jwtService) {
        this.jwtService = jwtService;
    }

    private UserDetails createUserDetails(List<String> roles, String username) {
        return new JwtUserDetails(username, roles);
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

    private record JwtUserDetails(String username, List<String> roles) implements UserDetails {
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
        public String getUsername() {
            return username;
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
}