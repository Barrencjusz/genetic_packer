package genetic.packer.evolution.generation.dto.individual.impl;

import java.util.List;

import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.Individual;
import genetic.selectors.dto.FitnessTested;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class RatedIndividual<V extends Comparable<V>, T> implements Individual<T>, FitnessTested<V> {

    private V fitness;

    private Individual<T> individual;

    @GeneratePojoBuilder
    public RatedIndividual(V fitness, Individual<T> individual) {
        this.fitness = fitness;
        this.individual = individual;
    }

    @Override
    public V getFitness() {
        return fitness;
    }

    @Override
    public List<Cell<T>> getCells() {
        return this.individual.getCells();
    }
}
