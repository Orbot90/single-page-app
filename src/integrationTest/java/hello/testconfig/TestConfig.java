package hello.testconfig;

import hello.Application;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * Created by orbot on 28.05.16.
 */
@Configuration
@Import({Application.class})
@ComponentScan(basePackages = "hello",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                value = Configuration.class))
public class TestConfig {
}
