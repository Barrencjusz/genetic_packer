package genetic.selectors.dto;

/**
 * @author piotr.larysz
 */
public interface FitnessTested<T extends Comparable<T>> {

    T getFitness();
}
