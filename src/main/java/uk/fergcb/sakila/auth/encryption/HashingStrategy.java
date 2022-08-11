package uk.fergcb.sakila.auth.encryption;

public abstract class HashingStrategy {

    public static final String SHA512 = "SHA512";

    public static HashingStrategy get(String strategyName) {
        return switch (strategyName) {
            case SHA512 -> new SHA512HashingStrategy();
            default -> throw new IllegalArgumentException(String.format("No such hashing strategy '%s'.", strategyName));
        };
    }

    public abstract String hash(String password);
    protected abstract String hash(String password, byte[] salt);
    public abstract boolean validate(String unknown, String known);
}
