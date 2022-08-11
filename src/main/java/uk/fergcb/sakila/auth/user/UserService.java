package uk.fergcb.sakila.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.fergcb.sakila.auth.encryption.HashingStrategy;

@Component
public class UserService implements IUserService {

    private static final String HASHING_STRATEGY = "SHA512";

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(CredentialsDTO data) {
        final String username = data.getUsername();

        final String password = data.getPassword();
        final String passwordHash = HashingStrategy.get(HASHING_STRATEGY).hash(password);

        final String group = "USER";

        User user = User.create(username, passwordHash, HASHING_STRATEGY, group);
        userRepository.save(user);
    }
}
