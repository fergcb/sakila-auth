package uk.fergcb.sakila.auth.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password_hash")
    private String passwordHash;

    @JsonIgnore
    @Column(name = "hashing_strategy")
    private String hashingStrategy;

    @Column(name = "`group`")
    private String group;

    public User (String userId, String username, String passwordHash, String hashingStrategy, String group) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.hashingStrategy = hashingStrategy;
        this.group = group;
    }

    public User () {}

    public static User create(String username, String passwordHash, String hashingStrategy, String group) {
        final String userId = UUID.randomUUID().toString();
        return new User(userId, username, passwordHash, hashingStrategy, group);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getHashingStrategy() {
        return hashingStrategy;
    }

    public void setHashingStrategy(String hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
