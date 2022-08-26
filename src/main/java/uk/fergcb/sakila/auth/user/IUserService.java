package uk.fergcb.sakila.auth.user;

public interface IUserService {
    User getUser(String userId);

    void createUser(CredentialsDTO data);
}
