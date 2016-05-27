package hello.service.impl;

import hello.model.User;
import hello.model.UserAuthentication;
import hello.model.UserDto;
import hello.model.UserRole;
import hello.repository.UserRepository;
import hello.service.TokenHandler;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by orbot on 06.12.15.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Long ONE_DAY = 1000l * 3600 * 24;

    @Autowired
    UserRepository userRepository;
//    @Autowired
    TokenHandler tokenHandler;

    @Override
    public String registerNewUserAndGetToken(UserDto user) {
        User newUser = new User(user.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.grantRole(UserRole.USER);
        newUser.setExpires(System.currentTimeMillis() + ONE_DAY);
        userRepository.save(newUser);
        String token = tokenHandler.createTokenForUser(newUser);
        UserAuthentication authentication = new UserAuthentication(newUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }
}
