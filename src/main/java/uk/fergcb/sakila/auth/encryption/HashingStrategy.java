package uk.fergcb.sakila.auth.encryption;

public abstract class HashingStrategy {

    public static final String SHA512 = "SHA512";

    public static HashingStrategy get(String strategyName) {
        return switch (strategyName) {
            case SHA512 -> new SHA512HashingStrategy();
            default -> throw new IllegalArgumentException(String.format("No such hashing strategy '%s'.", strategyName));
        };
    }

    /**
     * Take a plain-text password and return its hash
     * @param password The plain-text password to hash
     * @return The hashed password
     */
    public abstract String hash(String password);

    /**
     * Take a plain-text password and a salt, and return a salted hash of the password
     * @param password The plain-text password to hash
     * @param salt The salt
     * @return The hashed & salted password
     */
    protected abstract String hash(String password, byte[] salt);

    /**
     * Validate a plain-text password against a trusted hash
     * @param value The plain-text value to validate
     * @param trustedHash The hash to validate against
     * @return true if the password is valid, otherwise false
     */
    public abstract boolean validate(String value, String trustedHash);
}
