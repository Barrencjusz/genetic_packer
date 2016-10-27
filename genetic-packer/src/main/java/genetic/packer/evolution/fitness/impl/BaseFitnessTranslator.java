package genetic.packer.evolution.fitness.impl;

import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public abstract class BaseFitnessTranslator {

    protected double rateIntersections(Traversable<Boolean> intersections) {
        return intersections
                .map(this::rateSingleIntersection)
                .reduce((left, right) -> left * right);
    }

    private double rateSingleIntersection(boolean intersects) {
        return intersects ? 1 : 1.5;
    }
}
