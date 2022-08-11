package uk.fergcb.sakila.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {
    public InvalidTokenException(String tokenName) {
        super(HttpStatus.UNAUTHORIZED, String.format("Invalid access or refresh token '%s'.", tokenName));
    }
}
