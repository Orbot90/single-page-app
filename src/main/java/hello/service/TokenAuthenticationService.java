package hello.service;

import hello.model.UserAuthentication;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by orbot on 01.12.15.
 */
public interface TokenAuthenticationService {
    void addAuthentication(HttpServletResponse response, UserAuthentication authentication);

    Authentication getAuthentication(HttpServletRequest request);
}
