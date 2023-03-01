package com.pragma.powerup.infrastructure.configuration.security.jwt;

import com.pragma.powerup.infrastructure.configuration.security.userdetails.CustomUserDetails;
import com.pragma.powerup.infrastructure.configuration.security.exception.exceptions.JwtException;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final String jwtSecret = "SECRET";
    private final int jwtExpirationMinutes = 120;

    public String generateJwtToken(Authentication authentication) {

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ( jwtExpirationMinutes * 1000) * 60))
                //.claim("roles", userPrincipal.getAuthorities().stream()
                //        .map(role -> role.getAuthority())
                //        .collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            throw new JwtException("Invalid Jwt signature");
        } catch (MalformedJwtException e) {
            throw new JwtException("Invalid Jwt token");
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty");
        }
    }
}
