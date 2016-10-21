package genetic.recombinators;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.Individual;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author piotr.larysz
 */
public abstract class BaseRecombinator<T1, T2> implements Recombinator<Individual<T1>, T2> {

    @Autowired
    protected Supplier<ThreadLocalRandom> random;

    @Autowired
    protected Function<Cell<T1>, Cell<T1>> cellCloningMapper;

    private final Supplier<Boolean> recombination = () -> random.get().nextDouble() <= 0.7;

    @Override
    public T2 apply(Pair<Individual<T1>, Individual<T1>> pair) {
        if(recombination.get()) {
            return this.recombine(pair.getLeft().getCells(), pair.getRight().getCells());
        }
        return this.resolveDefault(pair);
    }

    protected abstract T2 recombine(List<Cell<T1>> left, List<Cell<T1>> right);

    protected abstract T2 resolveDefault(Pair<Individual<T1>, Individual<T1>> pair);
}
