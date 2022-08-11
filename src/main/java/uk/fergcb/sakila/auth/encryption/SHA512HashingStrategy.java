package uk.fergcb.sakila.auth.encryption;

import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA512HashingStrategy extends HashingStrategy {
    @Override
    public String hash(String password) {
        SecureRandom random = new SecureRandom();
        final byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hash(password, salt);
    }

    @Override
    protected String hash(String password, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);

        final byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        final String encodedHash = Base64.getEncoder().encodeToString(bytes);
        final String encodedSalt = Base64.getEncoder().encodeToString(salt);

        return encodedSalt + "." + encodedHash;
    }

    @Override
    public boolean validate(String value, String trustedHash) {
        String[] parts = StringUtils.split(trustedHash, ".");
        if (parts == null || parts.length != 2) {
            throw new IllegalArgumentException("Trusted hash is malformed.");
        }
        String encodedSalt = parts[0];

        byte[] salt = Base64.getDecoder().decode(encodedSalt);
        String hashedValue = hash(value, salt);

        return hashedValue.equals(trustedHash);
    }
}
