package genetic.selectors;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import genetic.selectors.dto.FitnessTested;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author piotr.larysz
 */
public interface PairSelector<V extends Comparable<V>, B> {

    List<Pair<B, B>> select(SizeFunction sizeFunction);

    interface Creator<V extends Comparable<V>> {

        <M extends FitnessTested<V>> PairSelector<V, M> from(Collection<M> fitnessTesteds);
    }

    interface SizeFunction extends Function<Integer, Integer> {}

    SizeFunction HALF = size -> size / 2;
}
