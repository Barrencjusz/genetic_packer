package genetic.packer.evolution.fitness.impl;

import genetic.packer.evolution.fitness.FitnessComponents;
import genetic.packer.evolution.fitness.FitnessTranslator;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class TotalFitnessTranslator extends BaseFitnessTranslator implements FitnessTranslator<FitnessComponents, Double> {

    @Override
    public Double apply(FitnessComponents fitnessComponents) {
        return this.rateIntersections(fitnessComponents.getIntersections()) + 1 / (fitnessComponents.getVolume() * 100);
    }
}
