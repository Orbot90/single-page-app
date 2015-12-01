package hello.service.impl;

import hello.model.User;
import hello.model.UserAuthentication;
import hello.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by orbot on 01.12.15.
 */
@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final String AUTH_HEADER = "X-AUTH-TOKEN";
    private static final Long ONE_DAY = 1000l * 3600 * 24;

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationServiceImpl(@Value("${token.secret}") String secret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    @Override
    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + ONE_DAY);
        response.addHeader(AUTH_HEADER, tokenHandler.createTokenForUser(user));
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        return null;
    }
}
