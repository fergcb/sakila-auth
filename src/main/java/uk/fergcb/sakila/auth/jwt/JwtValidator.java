package uk.fergcb.sakila.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import uk.fergcb.sakila.auth.user.User;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtValidator {

    /**
     * Extract the subject from a JWS
     * @param token The JWS
     * @return The subject value
     */
    public String getSubject(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Extract the expiration timestamp from a JWS
     * @param token The JWS
     * @return The expiration timestamp
     */
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Check whether a JWS' expiry data has elapsed
     * @param token The JWS
     * @return true if the token is expired, else false
     */
    private boolean isTokenExpired(String token) {
        final Date now = new Date();
        return getExpiration(token).before(now);
    }

    /**
     * Extract a claim from a JWS
     * @param token the JWS
     * @param claimsResolver a function that takes a Claims and extracts a single claim (e.g. io.jsonwebtoken.Claims::getSubject)
     * @return The extracted claim
     * @param <T> The type of the claim's value
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parse a JWS and return its claims
     * @param token The JWS
     * @return Its claims
     */
    public Claims getAllClaims(String token) {
        final JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(JwtKeys.getPrivateKey())
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    /**
     * Confirm whether a JWS is in-date and corresponds to the expected subject
     * @param token The JWS
     * @param user The expected subject
     * @return true if the token is valid, else false
     */
    public boolean validateToken(String token, User user) {
        final String subject = getSubject(token);
        return !isTokenExpired(token) &&
                subject.equals(user.getUserId());
    }
}
