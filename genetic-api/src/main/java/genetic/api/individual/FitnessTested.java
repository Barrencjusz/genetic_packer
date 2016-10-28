package genetic.api.individual;

import genetic.api.fitness.Fitness;

/**
 * @author piotr.larysz
 */
public interface FitnessTested<T extends Comparable<T>> {

    Fitness<T> getFitness();
}
