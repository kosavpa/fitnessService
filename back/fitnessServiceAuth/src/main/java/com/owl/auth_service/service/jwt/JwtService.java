package com.owl.auth_service.service.jwt;


import com.owl.auth_service.repository.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service("jwtService")
public class JwtService {
    @SuppressWarnings("unused")
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of("role", ((User) userDetails).getRole().getRoleName()), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 604800000L))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));
    }
}