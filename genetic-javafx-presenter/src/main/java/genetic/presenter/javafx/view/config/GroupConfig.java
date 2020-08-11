package genetic.presenter.javafx.view.config;

import javafx.scene.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupConfig {

    @Bean
    public Group group() {
        return new Group();
    }

}
