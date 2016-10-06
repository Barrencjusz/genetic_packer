package genetic.packer.factory;

import genetic.packer.evolution.generation.dto.Generation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class StatefulGenerationCreatorFactory implements Supplier<Supplier<Generation>> {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Supplier<Generation> get() {
        return (Supplier<Generation>) beanFactory.getBean("statefulGenerationCreator", "");
    }
}
