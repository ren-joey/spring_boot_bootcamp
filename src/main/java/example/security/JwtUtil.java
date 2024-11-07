package example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final SecretKey key = Keys.hmacShaKeyFor("your-secure-key".getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username) {
        // 1 day in milliseconds
        long EXPIRATION_TIME = 86400000;

        ClaimsBuilder claims = Jwts.claims();
        claims.add("sub", username);
        claims.add("role", "user");
        claims.add("iat", new Date());
        claims.add("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME));;

        return Jwts.builder()
                .claims()
                .add(claims.build())
                .and()
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        // TODO: Implement the method
        // https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/latest/index.html
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parse(token)
            .getPayload();
    }

    public boolean isTokenValid(String token) {
        return extractUsername(token) != null;
    }
}
