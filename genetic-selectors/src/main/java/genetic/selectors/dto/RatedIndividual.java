package genetic.selectors.dto;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
public class RatedIndividual<V extends Comparable<V>, T> implements Supplier<T> {

    private V fitness;

    private T individual;

    @GeneratePojoBuilder
    public RatedIndividual(T individual) {
        this.individual = individual;
    }

    public V getFitness() {
        return fitness;
    }

    public void setFitness(V fitness) {
        this.fitness = fitness;
    }

    @Override
    public T get() {
        return individual;
    }
}
