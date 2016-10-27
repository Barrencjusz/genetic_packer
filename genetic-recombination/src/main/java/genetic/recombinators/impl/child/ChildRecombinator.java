package genetic.recombinators.impl.child;

import java.util.function.Function;
import java.util.function.Supplier;

import genetic.api.individual.Cell;
import genetic.api.individual.Individual;
import genetic.api.individual.Organism;
import genetic.api.individual.impl.SimpleIndividual;
import genetic.recombinators.Recombinator;
import genetic.recombinators.chromosome.ChromosomeRecombinator;
import javaslang.Tuple2;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public abstract class ChildRecombinator<T1> implements Recombinator<Organism<T1>, Individual<Double, T1>> {

    private ChromosomeRecombinator<T1> chromosomeRecombinator;

    public ChildRecombinator(ChromosomeRecombinator<T1> chromosomeRecombinator) {
        this.chromosomeRecombinator = chromosomeRecombinator;
    }

    @Override
    public Traversable<Individual<Double, T1>> apply(Tuple2<? extends Organism<T1>, ? extends Organism<T1>> tuple) {
        return chromosomeRecombinator.apply(tuple.map(this::extractCells, this::extractCells))
                .map((Function<Seq<Cell<T1>>, SimpleIndividual<Double, T1>>) SimpleIndividual::new); //fixme bad cast
    }

    private Supplier<Seq<Cell<T1>>> extractCells(Organism<T1> organism) {
        return organism::getCells;
    }
}
