package uk.fergcb.sakila.auth.session;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uk.fergcb.sakila.auth.encryption.HashingStrategy;
import uk.fergcb.sakila.auth.exception.InvalidCredentialsException;
import uk.fergcb.sakila.auth.exception.InvalidTokenException;
import uk.fergcb.sakila.auth.jwt.JwtGenerator;
import uk.fergcb.sakila.auth.jwt.JwtValidator;
import uk.fergcb.sakila.auth.user.CredentialsDTO;
import uk.fergcb.sakila.auth.user.User;
import uk.fergcb.sakila.auth.user.UserRepository;

@Component
public class SessionService implements ISessionService {
    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final UserRepository userRepository;

    private final JwtGenerator jwtGenerator;
    private final JwtValidator jwtValidator;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository, JwtGenerator jwtGenerator, JwtValidator jwtValidator) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public SessionDetailsDTO createSession(CredentialsDTO credentials) {
        // Find a user with the given username
        final User user = userRepository.findByUsername(credentials.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        System.out.println("Found user " + user.getUsername());

        // Check that given password matches stored password
        final String password = credentials.getPassword();

        HashingStrategy hasher = HashingStrategy.get(user.getHashingStrategy());
        if (!hasher.validate(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // Generate access & refresh tokens
        final String accessToken = jwtGenerator.generateAccessToken(user);
        final String refreshToken = jwtGenerator.generateRefreshToken(user);

        Session session = Session.create(refreshToken, user.getUserId());
        sessionRepository.save(session);

        return new SessionDetailsDTO(accessToken, refreshToken);
    }

    @Override
    public SessionDetailsDTO refreshSession(SessionDetailsDTO sessionDetails) {
        Session session = sessionRepository.findByRefreshToken(sessionDetails.getRefreshToken())
                .orElseThrow(() -> new InvalidTokenException("refreshToken"));

        User user = session.getUser();

        boolean valid;
        try {
            valid = jwtValidator.validateToken(session.getRefreshToken(), user);
        } catch (JwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Could not validate access token.");
        }

        if (!valid) {
            throw new InvalidTokenException("refreshToken");
        }

        final String accessToken = jwtGenerator.generateAccessToken(user);
        final String refreshToken = jwtGenerator.generateRefreshToken(user);

        session.setRefreshToken(refreshToken);
        sessionRepository.save(session);

        return new SessionDetailsDTO(accessToken, refreshToken);
    }

    @Override
    public User validateSession(SessionDetailsDTO sessionDetails) {
        final String accessToken = sessionDetails.getAccessToken();
        try {
            final String userId = jwtValidator.getSubject(accessToken);
            final User user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidTokenException("accessToken"));

            if (!jwtValidator.validateToken(accessToken, user)) {
                throw new InvalidTokenException("accessToken");
            }

            return user;
        } catch (JwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Could not validate access token.");
        }
    }
}
