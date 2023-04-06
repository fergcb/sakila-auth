package uk.fergcb.sakila.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uk.fergcb.sakila.auth.encryption.HashingStrategy;

@Component
public class UserService implements IUserService {

    private static final String HASHING_STRATEGY = HashingStrategy.SHA512;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exists with that id."));
    }

    @Override
    public void createUser(CredentialsDTO data) {
        final String username = data.getUsername();
        final String password = data.getPassword();
        final String passwordHash = HashingStrategy.get(HASHING_STRATEGY).hash(password);
        final String group = "USER";

        final User user = User.create(username, passwordHash, HASHING_STRATEGY, group);
        userRepository.save(user);
    }
}
