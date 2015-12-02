package hello.service;

import hello.model.User;

/**
 * Created by orbot on 02.12.15.
 */
public interface TokenHandler {
    User parseUserFromToken(String token);

    String createTokenForUser(User user);
}
