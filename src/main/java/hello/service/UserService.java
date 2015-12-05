package hello.service;

import hello.model.UserDto;

/**
 * Created by orbot on 06.12.15.
 */
public interface UserService {
    public String registerNewUserAndGetToken(UserDto user);
}
