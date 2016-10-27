package genetic.recombinators.chromosome;


import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.api.individual.Cell;
import genetic.recombinators.Recombinator;
import javaslang.Tuple2;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author piotr.larysz
 */
public abstract class ChromosomeRecombinator<T1> implements Recombinator<Supplier<Seq<Cell<T1>>>, Seq<Cell<T1>>> {

    @Autowired
    protected Supplier<ThreadLocalRandom> random;

    @Autowired
    protected Function<Cell<T1>, Cell<T1>> cellCloningMapper;

    private final Supplier<Boolean> recombination = () -> random.get().nextDouble() <= 0.7;

    @Override
    public Traversable<Seq<Cell<T1>>> apply(Tuple2<? extends Supplier<Seq<Cell<T1>>>, ? extends Supplier<Seq<Cell<T1>>>> tuple) {
        return tuple.map(Supplier::get, Supplier::get).transform(this::proceed).map(seq -> seq.map(cellCloningMapper));
    }
    private Traversable<Seq<Cell<T1>>> proceed(Seq<Cell<T1>> first, Seq<Cell<T1>> second) {
        if(recombination.get()) {
            return this.recombine(first, second, random.get().nextInt(first.size()));
        }
        return this.resolveDefault(first, second);
    }

    protected abstract Traversable<Seq<Cell<T1>>> recombine(Seq<Cell<T1>> first, Seq<Cell<T1>> second, int exchangePoint);

    protected abstract Traversable<Seq<Cell<T1>>> resolveDefault(Seq<Cell<T1>> first, Seq<Cell<T1>> second);
}
