package genetic.presenter.javafx.view.config;

import javafx.scene.Camera;
import javafx.scene.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author piotr.larysz
 */
@Configuration
public class GroupConfig {

    @Bean
    public Group group() {
        return new Group();
    }

}
