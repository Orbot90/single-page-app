package hello.controller;

import hello.model.*;
import hello.service.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by orbot on 21.11.15.
 */
@Controller
public class HelloController {

    @Autowired
    TokenHandler tokenHandler;

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
    public RegistrationResponseDto join(@RequestBody @Valid UserDto user, BindingResult errors) {
        RegistrationResponseDto response = new RegistrationResponseDto();
        if(errors.hasErrors()) {
            response.setSuccess(false);
            response.setFieldErrors(errors);
            return response;
        }
        User newUser = new User(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.grantRole(UserRole.USER);
        String token = tokenHandler.createTokenForUser(newUser);
        UserAuthentication authentication = new UserAuthentication(newUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.setSuccess(true);
        response.setToken(token);

        return response;
    }
}
