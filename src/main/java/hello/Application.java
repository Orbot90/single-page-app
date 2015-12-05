package hello;

import hello.model.User;
import hello.model.UserRole;
import hello.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by orbot on 21.11.15.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@Slf4j
public class Application {
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public InitializingBean initializeDefaultUsers() {
        return new InitializingBean() {
            @Override
            public void afterPropertiesSet() throws Exception {
                addUser("admin", "admin");
                addUser("user", "user");
            }

            public void addUser(String username, String password) {
                log.info("Adding user {}", username);
                User user = new User();
                user.setUsername(username);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                user.grantRole(username.equals("admin") ? UserRole.ADMIN : UserRole.USER);
                userRepository.save(user);
                log.info("User {} added successfully");
            }
        };
    }
}
