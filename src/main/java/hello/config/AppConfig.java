package hello.config;

import hello.service.TokenHandler;
import hello.service.impl.TokenHandlerImpl;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by orbot on 02.12.15.
 */
@Configuration
public class AppConfig {

//    @Value("${token.secret}")
    private String secret;

//    @Bean
    public TokenHandler tokenHandler() {
        TokenHandlerImpl tokenHandler =
                new TokenHandlerImpl(DatatypeConverter.parseBase64Binary(secret));
        return tokenHandler;
    }
}
