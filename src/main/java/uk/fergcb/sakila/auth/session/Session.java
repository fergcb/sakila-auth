package uk.fergcb.sakila.auth.session;

import uk.fergcb.sakila.auth.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private String userId;

    public Session(String sessionId, String refreshToken, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public Session() {}

    public static Session create(String refreshToken, String userId) {
        String sessionId = UUID.randomUUID().toString();
        return new Session(sessionId, refreshToken, userId);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
