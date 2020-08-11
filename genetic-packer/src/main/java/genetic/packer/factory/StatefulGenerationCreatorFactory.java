package genetic.packer.factory;

import java.util.function.Supplier;

import genetic.api.generation.Generation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
