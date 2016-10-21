package genetic.packer.evolution.fitness.impl;

import java.util.Collection;

/**
 * @author piotr.larysz
 */
public abstract class BaseFitnessTranslator {

    protected double rateIntersections(Collection<Boolean> intersections) {
        return intersections.stream()
                .map(this::rateSingleIntersection)
                .mapToDouble(Double::valueOf)
                .reduce(1, (left, right) -> left * right);
    }

    private double rateSingleIntersection(boolean intersects) {
        return intersects ? 1 : 1.5;
    }
}
