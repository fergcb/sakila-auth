package uk.fergcb.sakila.auth.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtKeys {
    protected static Key getPrivateKey() {
        String encoded = Dotenv.load().get("JWT_PRIVATE_KEY");
        byte[] decoded = Decoders.BASE64.decode(encoded);
        return Keys.hmacShaKeyFor(decoded);
    }
}
