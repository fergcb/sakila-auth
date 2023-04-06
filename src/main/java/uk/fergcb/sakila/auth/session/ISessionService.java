package uk.fergcb.sakila.auth.session;

import uk.fergcb.sakila.auth.user.CredentialsDTO;
import uk.fergcb.sakila.auth.user.User;

public interface ISessionService {
    SessionDetailsDTO createSession(CredentialsDTO credentials);
    SessionDetailsDTO refreshSession(SessionDetailsDTO credentialsDTO);
    User validateSession(SessionDetailsDTO credentialsDTO);
}
