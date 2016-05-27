package hello.service.impl;

import hello.model.User;
import hello.model.UserAuthentication;
import hello.service.TokenAuthenticationService;
import hello.service.TokenHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by orbot on 01.12.15.
 */
@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final String AUTH_HEADER = "X-AUTH-TOKEN";
    private static final Long ONE_DAY = 1000l * 3600 * 24;

//    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + ONE_DAY);
        response.addHeader(AUTH_HEADER, tokenHandler.createTokenForUser(user));
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER);
        if(token != null) {
            final User user = tokenHandler.parseUserFromToken(token);
            if(user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
