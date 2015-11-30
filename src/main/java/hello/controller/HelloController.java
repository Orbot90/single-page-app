package hello.controller;

import hello.model.User;
import hello.model.UserAuthentication;
import hello.model.UserAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by orbot on 21.11.15.
 */
@Controller
public class HelloController {
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
}
