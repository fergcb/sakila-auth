package uk.fergcb.sakila.auth.session;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, String> {
    Optional<Session> findByRefreshToken(String refreshToken);
}
