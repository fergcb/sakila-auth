package uk.fergcb.sakila.auth.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.fergcb.sakila.auth.Options;
import uk.fergcb.sakila.auth.user.CredentialsDTO;
import uk.fergcb.sakila.auth.user.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<SessionDetailsDTO> createSession(@RequestBody CredentialsDTO credentials) {
        SessionDetailsDTO tokens = sessionService.createSession(credentials);

        return ResponseEntity.status(HttpStatus.CREATED).body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SessionDetailsDTO> refreshSession(@RequestBody SessionDetailsDTO sessionDetails) {
        SessionDetailsDTO tokens = sessionService.refreshSession(sessionDetails);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/validate")
    public ResponseEntity<User> validateSession(@RequestBody SessionDetailsDTO sessionDetailsDTO) {
        User user = sessionService.validateSession(sessionDetailsDTO);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET})
    public ResponseEntity<Options> getOptions() {
        Options options = new Options();

        options.add(linkTo(SessionController.class).withSelfRel().expand());
        options.add(linkTo(methodOn(SessionController.class).createSession(null)).withRel("login").expand());
        options.add(linkTo(methodOn(SessionController.class).refreshSession(null)).withRel("refresh").expand());
        options.add(linkTo(methodOn(SessionController.class).validateSession(null)).withRel("validate").expand());

        return ResponseEntity.ok(options);
    }
}
