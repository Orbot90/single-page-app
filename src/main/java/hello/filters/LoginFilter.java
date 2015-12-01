package hello.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.User;
import hello.model.UserAuthentication;
import hello.service.TokenAuthenticationService;
import hello.service.impl.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by orbot on 01.12.15.
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationService tokenAuthenticationService;

    public LoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                          UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(urlMapping));

        this.userDetailsService = userDetailsService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());

        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain filterChain, Authentication authentication) {

        final User authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        tokenAuthenticationService.addAuthentication(response, userAuthentication);

        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}
