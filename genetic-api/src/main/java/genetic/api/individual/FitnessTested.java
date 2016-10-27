package genetic.api.individual;

/**
 * @author piotr.larysz
 */
public interface FitnessTested<T extends Comparable<T>> {

    T getFitness();
}
