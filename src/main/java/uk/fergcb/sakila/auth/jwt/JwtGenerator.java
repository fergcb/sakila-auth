package uk.fergcb.sakila.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import uk.fergcb.sakila.auth.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtGenerator {
    private static final long ACCESS_TOKEN_MAX_LIFETIME = 60 * 60 * 1000; // 1 hour
    private static final long REFRESH_TOKEN_MAX_LIFETIME = 28 * 24 * 60 * 60 * 1000L; // 28 days

    /**
     * Generate a signed JWT with a given subject, lifetime, and payload
     * @param subject An identifier of the subject of the JWT (e.g. the user)
     * @param maxLifetime Time in milliseconds after which the JWT should expire
     * @param payload A map of additional claims to include in the JWT body
     * @return The generated JWS
     */
    public String generate(String subject, long maxLifetime, Map<String, ?> payload) {
        JwtBuilder builder = Jwts.builder();

        long now = System.currentTimeMillis();

        return builder.setClaims(payload)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + maxLifetime))
                .signWith(JwtKeys.getPrivateKey())
                .compact();
    }

    public String generate(String subject, long maxLifetime) {
        return generate(subject, maxLifetime, new HashMap<>());
    }

    public String generateAccessToken(User user) {
        return generate(
                user.getUserId(),
                ACCESS_TOKEN_MAX_LIFETIME,
                Map.of(
                        "group", user.getGroup()
                )
        );
    }

    public String generateRefreshToken(User user) {
        return generate(
                user.getUserId(),
                REFRESH_TOKEN_MAX_LIFETIME
        );
    }
}
