package uk.fergcb.sakila.auth.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtKeys {

    private static final String VAR_NAME = "JWT_PRIVATE_KEY";

    protected static Key getPrivateKey() {
        String encoded;
        try {
            encoded = Dotenv.configure().load().get(VAR_NAME);
        } catch (DotenvException ex) {
            encoded = System.getenv(VAR_NAME);
        }

        final byte[] decoded = Decoders.BASE64.decode(encoded);

        return Keys.hmacShaKeyFor(decoded);
    }
}
