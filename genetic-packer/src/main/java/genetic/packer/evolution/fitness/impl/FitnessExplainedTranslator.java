package genetic.packer.evolution.fitness.impl;

import genetic.packer.evolution.fitness.FitnessComponents;
import genetic.packer.evolution.fitness.FitnessTranslator;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessExplainedTranslator extends BaseFitnessTranslator implements FitnessTranslator<FitnessComponents, String> {

    private static final String TEXT = "%d boxes intersects, intersection score: %e, volume: %e";

    @Override
    public String apply(FitnessComponents fitnessComponents) {
        final long intersectionCount = fitnessComponents.getIntersections().filter(BooleanUtils::isTrue).size();
        return String.format(
            TEXT,
            intersectionCount,
            this.rateIntersections(fitnessComponents.getIntersections()),
            fitnessComponents.getVolume()
        );
    }
}
