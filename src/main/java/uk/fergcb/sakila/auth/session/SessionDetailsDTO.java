package uk.fergcb.sakila.auth.session;

import uk.fergcb.sakila.auth.user.UserController;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SessionDetailsDTO {

    private final String userId;
    private String accessToken;
    private String refreshToken;

    public SessionDetailsDTO(String userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Map<String, Link> get_links() {
        return Map.of(
                "userinfo", new Link(
                        linkTo(methodOn(UserController.class).getUserInfo(userId))
                                .withRel("userinfo")
                                .getHref())
        );
    }

    private record Link(String href) {}
}
