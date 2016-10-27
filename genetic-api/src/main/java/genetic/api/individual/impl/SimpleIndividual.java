package genetic.api.individual.impl;

import genetic.api.builder.HasBuilder;
import genetic.api.elitism.EliteAcknowledged;
import genetic.api.fitness.Evaluator;
import genetic.api.individual.Cell;
import genetic.api.individual.Individual;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
public class SimpleIndividual<T1 extends Comparable<T1>, T2> implements Individual<T1, T2> {

    private Seq<Cell<T2>> cells;

    @HasBuilder
    public SimpleIndividual(Seq<Cell<T2>> cells) {
        this.cells = cells;
    }

    @Override
    public Seq<Cell<T2>> getCells() {
        return cells;
    }

    @Override
    public <M extends Evaluator<T1, T2> & EliteAcknowledged> RatedIndividual<T1, T2> evaluate(M evaluator) {
        return evaluator.apply(this);
    }
}
