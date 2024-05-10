package com.owl.fitness_service.auth.jwtservice;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


@SuppressWarnings("unused")
@Service("jwtTokenService")
public class JwtTokenService {
    private final String jwtSigningKey;

    public JwtTokenService(@Value("${token.signing.key}") String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public void checkTokenExpired(String token) {
        if (extractExpiration(token).before(new Date())) {
            throw new AccessDeniedException("Token is expired!");
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <R> R extractClaim(String token, Function<Claims, R> claimsResolvers) {
        Claims claims = extractAllClaims(token);

        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));
    }

    public String getUsernameByToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> getRoleListByToken(String token) {
        List<String> roleList = new ArrayList<>();

        roleList.add(extractClaim(token, claims -> claims.get("role", String.class)));

        return roleList;
    }
}