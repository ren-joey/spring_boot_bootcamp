package example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key = Keys
            .hmacShaKeyFor(System.getProperty("JWT_SECRET").getBytes(StandardCharsets.UTF_8));

    public String extractUsername(String token) {
        return extractUsername(token, extractAllClaims(token));
    }

    public String extractUsername(String token, Claims claims) {
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractExpiration(token, extractAllClaims(token));
    }

    public Date extractExpiration(String token, Claims claims) {
        return claims.getExpiration();
    }

    public String generateToken(String username) {
        // 1 day in milliseconds
        long EXPIRATION_TIME = 86400000;

        ClaimsBuilder claims = Jwts.claims();
        claims.add("sub", username);
        claims.add("role", "user");
        claims.add("iat", new Date());
        claims.add("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        return Jwts.builder()
                .claims()
                .add(claims.build())
                .and()
                .signWith(key)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        Claims claims = extractAllClaims(token);
        return extractUsername(token, claims) != null
                && extractExpiration(token, claims).after(new Date());
    }
}
