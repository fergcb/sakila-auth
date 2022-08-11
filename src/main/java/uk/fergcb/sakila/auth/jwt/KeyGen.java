package uk.fergcb.sakila.auth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class KeyGen {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secret = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secret);
    }
}
