package uk.fergcb.sakila.auth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;

public class KeyGen {
    public static void main(String[] args) {
        KeyPair keys = Keys.keyPairFor(SignatureAlgorithm.RS512);
        String publicKey = "public," + Encoders.BASE64.encode(keys.getPublic().getEncoded());
        String privateKey = "private," + Encoders.BASE64.encode(keys.getPrivate().getEncoded());
        System.out.println(publicKey + "\n" + privateKey);
    }
}
