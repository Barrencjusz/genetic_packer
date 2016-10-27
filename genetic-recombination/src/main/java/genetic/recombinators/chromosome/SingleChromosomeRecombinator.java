package genetic.recombinators.chromosome;

import genetic.api.individual.Cell;
import javaslang.collection.Seq;
import javaslang.collection.Stream;
import javaslang.collection.Traversable;
import org.springframework.stereotype.Component;

@Component//fixme won't work with generics
public class SingleChromosomeRecombinator<T1> extends ChromosomeRecombinator<T1> {

    @Override
    protected Traversable<Seq<Cell<T1>>> recombine(Seq<Cell<T1>> first, Seq<Cell<T1>> second, int exchangePoint) {
        final Seq<Cell<T1>> leftsFirstPart = first.subSequence(0, exchangePoint);
        final Seq<Cell<T1>> rightsSecondPart = second.subSequence(exchangePoint, second.size());

        final Stream<Cell<T1>> cells = Stream.<Cell<T1>>empty().appendAll(leftsFirstPart).appendAll(rightsSecondPart);
        return Stream.of(cells);
    }

    @Override
    protected Traversable<Seq<Cell<T1>>> resolveDefault(Seq<Cell<T1>> first, Seq<Cell<T1>> second) {
        final Seq<Cell<T1>> cells = random.get().nextBoolean() ? first : second;
        return Stream.of(cells);
    }
}
