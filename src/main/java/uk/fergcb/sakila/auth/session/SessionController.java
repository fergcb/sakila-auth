package uk.fergcb.sakila.auth.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.fergcb.sakila.auth.user.CredentialsDTO;
import uk.fergcb.sakila.auth.user.User;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionDetailsDTO> createSession(@RequestBody CredentialsDTO credentials) {
        SessionDetailsDTO tokens = sessionService.createSession(credentials);

        return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
    }

    @PatchMapping
    public ResponseEntity<SessionDetailsDTO> refreshSession(@RequestBody SessionDetailsDTO sessionDetails) {
        SessionDetailsDTO tokens = sessionService.refreshSession(sessionDetails);

        return ResponseEntity.ok(tokens);
    }

    @PutMapping
    public ResponseEntity<User> validateSession(@RequestBody SessionDetailsDTO sessionDetailsDTO) {
        User user = sessionService.validateSession(sessionDetailsDTO);

        return ResponseEntity.ok(user);
    }
}
