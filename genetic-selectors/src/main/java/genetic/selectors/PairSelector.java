package genetic.selectors;

import java.util.function.Function;

import genetic.api.individual.FitnessTested;
import javaslang.Tuple2;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public interface PairSelector<B> {

    Seq<Tuple2<B, B>> select(SizeFunction sizeFunction); // fixme somethings wrong with generics

    interface Creator<V extends Comparable<V>> {

        <M extends FitnessTested<V>> PairSelector<M> from(Traversable<M> fitnessTesteds);
    }

    interface SizeFunction extends Function<Integer, Integer> {}

    SizeFunction HALF = size -> size / 2;
}
