package genetic.packer.evolution.fitness.impl;

import genetic.packer.evolution.fitness.FitnessComponents;
import genetic.packer.evolution.fitness.FitnessTester;
import genetic.packer.evolution.fitness.FitnessTranslator;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class NaturalFitnessTranslator extends BaseFitnessTranslator implements FitnessTranslator<FitnessComponents, String> {

    private static final String TEXT = "%d boxes intersects, intersection score: %e, volume: %e";

    @Override
    public String apply(FitnessComponents fitnessComponents) {
        final long intersectionCount = fitnessComponents.getIntersections().stream()
                .filter(BooleanUtils::isTrue)
                .count();
        return String.format(
            TEXT,
            intersectionCount,
            this.rateIntersections(fitnessComponents.getIntersections()),
            fitnessComponents.getVolume()
        );
    }
}
