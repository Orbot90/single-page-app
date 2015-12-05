package hello.controller;

import hello.error.InvalidRequestException;
import hello.model.*;
import hello.repository.UserRepository;
import hello.service.TokenHandler;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by orbot on 21.11.15.
 */
@Controller
public class HelloController {

    @Autowired
    TokenHandler tokenHandler;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap model) {
        return "home";
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof UserAuthentication) {
            return ((UserAuthentication)authentication).getDetails();
        }

        User user = new User();
        user.setUsername(authentication.getName());
        return user;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public RegistrationResponseDto join(@Valid UserDto user, BindingResult errors) {
        RegistrationResponseDto response = new RegistrationResponseDto();
        if(errors.hasErrors()) {
            throw new InvalidRequestException("Invalid register form", errors);
        }
        String token = userService.registerNewUserAndGetToken(user);
        response.setSuccess(true);
        response.setToken(token);
        return response;
    }
}
