package genetic.selectors;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import genetic.selectors.dto.RatedIndividual;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author piotr.larysz
 */
public interface PairSelector<V extends Comparable<V>, T> {

    List<Pair<RatedIndividual<V, T>, RatedIndividual<V, T>>> select(SizeFunction sizeFunction);

    interface Creator<V extends Comparable<V>> {

        <P> PairSelector<V, P> from(Collection<RatedIndividual<V, P>> ratedIndividuals);
    }

    interface SizeFunction extends Function<Integer, Integer> {}

    SizeFunction HALF = size -> size / 2;
}
