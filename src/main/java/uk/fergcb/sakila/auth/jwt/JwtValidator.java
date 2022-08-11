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

    public String getSubject(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String getGroup(String token) {
        return getClaim(token, claims -> claims.get(token, String.class));
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        Date now = new Date();
        return getExpiration(token).before(now);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaims(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(JwtKeys.getPrivateKey())
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, User user) {
        final String subject = getSubject(token);
        return !isTokenExpired(token) &&
                subject.equals(user.getUserId());
    }
}
