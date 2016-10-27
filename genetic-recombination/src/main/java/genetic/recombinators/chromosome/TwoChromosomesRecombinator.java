package genetic.recombinators.chromosome;

import genetic.api.individual.Cell;
import javaslang.collection.Seq;
import javaslang.collection.Stream;
import javaslang.collection.Traversable;
import org.springframework.stereotype.Component;

@Component
public class TwoChromosomesRecombinator<T1> extends ChromosomeRecombinator<T1> {

    @Override
    protected Traversable<Seq<Cell<T1>>> recombine(Seq<Cell<T1>> first, Seq<Cell<T1>> second, int exchangePoint) {
        final Seq<Cell<T1>> malesFirstPart = first.subSequence(0, exchangePoint);
        final Seq<Cell<T1>> femalesSecondPart = second.subSequence(exchangePoint, second.size());

        final Seq<Cell<T1>> femalesFirstPart = second.subSequence(0, exchangePoint);
        final Seq<Cell<T1>> malesSecondPart = first.subSequence(exchangePoint, first.size());

        final Stream<Cell<T1>> firstChildChromosome = Stream.<Cell<T1>>empty().appendAll(malesFirstPart).appendAll(femalesSecondPart);
        final Stream<Cell<T1>> secondChildChromosome = Stream.<Cell<T1>>empty().appendAll(femalesFirstPart).appendAll(malesSecondPart);

        return Stream.of(firstChildChromosome, secondChildChromosome);
    }

    @Override
    protected Traversable<Seq<Cell<T1>>> resolveDefault(Seq<Cell<T1>> first, Seq<Cell<T1>> second) {
        return Stream.of(first, second);
    }
}
