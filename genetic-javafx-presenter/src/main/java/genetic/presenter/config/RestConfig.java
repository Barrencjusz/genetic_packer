package genetic.presenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author piotr.larysz
 */
@Configuration
public class RestConfig {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
