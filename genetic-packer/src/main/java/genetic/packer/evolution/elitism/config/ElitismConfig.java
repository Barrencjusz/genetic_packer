package genetic.packer.evolution.elitism.config;

import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.evolution.elitism.impl.ElitistPickerImpl;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import javafx.scene.shape.Box;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author piotr.larysz
 */
@Configuration
public class ElitismConfig {

    @Bean
    public ElitistPicker<SimpleIndividual<Box>> elitistPicker() {
        return new ElitistPickerImpl<>();
    }
}
