package com.hyecheon.photouploader.security;

import com.hyecheon.photouploader.domain.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static com.hyecheon.photouploader.security.SecurityConstants.EXPIRATION_TIME;
import static com.hyecheon.photouploader.security.SecurityConstants.SECRET;

@Slf4j
@Component
public class JwtTokenProvider {

    //Generator the token
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiryDate = now.plusSeconds(EXPIRATION_TIME);
        String userId = Long.toString(user.getId());
        return Jwts.builder()
                .setSubject(userId)
                .claim("id", Long.toString(user.getId()))
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //validate the token
    public boolean validateToken(String token) {
        return getJwsClaims(token) != null;
    }

    private Jws<Claims> getJwsClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }
        return null;
    }

    public Long getUserIdFromJWT(String token) {
        final Jws<Claims> claimsJws = getJwsClaims(token);
        return Long.parseLong(String.valueOf(Objects.requireNonNull(claimsJws).getBody().get("id")));
    }
}