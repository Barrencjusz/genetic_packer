package genetic.selectors;

import genetic.selectors.dto.RatedIndividual;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
public interface PairSelector<V extends Comparable<V>> {

    <T> Collection<Map.Entry<T, T>> select(Context<V, T> context);

    class Context<V extends Comparable<V>, T> {

        public static final Function<Context<?, ?>, Integer> HALF = context -> context.getRatedIndividuals().size() / 2;

        private Function<Context<?, ?>, Integer> selectionSizeStrategy;

        private Collection<RatedIndividual<V, T>> ratedIndividuals;

        public Context(Collection<RatedIndividual<V, T>> ratedIndividuals, Function<Context<?, ?>, Integer> selectionSizeStrategy) {
            this.selectionSizeStrategy = selectionSizeStrategy;
            this.ratedIndividuals = ratedIndividuals;
        }

        public Integer getPairsToSelect() {
            return this.selectionSizeStrategy.apply(this);
        }

        public Collection<RatedIndividual<V, T>> getRatedIndividuals() {
            return ratedIndividuals;
        }
    }
}
