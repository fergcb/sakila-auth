package uk.fergcb.sakila.auth;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.fergcb.sakila.auth.session.SessionController;
import uk.fergcb.sakila.auth.user.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class OptionsController {

    @RequestMapping(method = { RequestMethod.OPTIONS, RequestMethod.GET })
    public Options getOptions() {
        Options options = new Options();

        options.add(linkTo(UserController.class).withRel("users").expand());
        options.add(linkTo(SessionController.class).withRel("sessions").expand());

        return options;
    }

}
