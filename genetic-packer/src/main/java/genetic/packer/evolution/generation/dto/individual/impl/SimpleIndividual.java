package genetic.packer.evolution.generation.dto.individual.impl;

import java.util.List;
import java.util.function.Function;

import genetic.packer.config.HasBuilder;
import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.Evaluated;
import genetic.packer.evolution.generation.dto.individual.Individual;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class SimpleIndividual<T1 extends Comparable<T1>, T2> implements Individual<T2>, Evaluated<T1, T2> {

    private List<Cell<T2>> cells;

    @HasBuilder
    public SimpleIndividual(List<Cell<T2>> cells) {
        this.cells = cells;
    }

    @Override
    public List<Cell<T2>> getCells() {
        return cells;
    }

    @Override
    public RatedIndividual<T1, T2> evaluate(Function<Individual<T2>, T1> testingFunction) {
        return new RatedIndividualBuilder<T1, T2>()
            .withIndividual(this)
            .withFitness(testingFunction.apply(this))
            .build();
    }
}
