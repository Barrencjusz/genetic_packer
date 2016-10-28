package genetic.api.individual.impl;

import genetic.api.builder.HasBuilder;
import genetic.api.elitism.EliteAcknowledged;
import genetic.api.elitism.Promoter;
import genetic.api.fitness.Fitness;
import genetic.api.individual.Cell;
import genetic.api.individual.FitnessTested;
import genetic.api.individual.Organism;
import genetic.api.individual.Promoted;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
public class RatedIndividual<V extends Comparable<V>, T> implements Organism<T>, FitnessTested<V>, Promoted<V, T> {

    private Fitness<V> fitness;

    private Organism<T> organism;

    @HasBuilder
    public RatedIndividual(Fitness<V> fitness, Organism<T> organism) {
        this.fitness = fitness;
        this.organism = organism;
    }

    @Override
    public Fitness<V> getFitness() {
        return fitness;
    }


    @Override
    public Seq<Cell<T>> getCells() {
        return organism.getCells();
    }

    @Override
    public <M extends Promoter & EliteAcknowledged> Elite<V, T> promote(M promoter) {
        return promoter.apply(this);
    }
}
