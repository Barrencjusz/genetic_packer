package genetic.selectors.roulette;

import java.util.TreeMap;
import java.util.function.Function;

import genetic.api.individual.FitnessTested;
import javaslang.Tuple2;
import javaslang.collection.Traversable;


/**
 * @author piotr.larysz
 */
public interface RouletteDistancer<T extends Comparable<T>> {

    <M extends FitnessTested<T>> TreeMap<T, M> distance(Traversable<M> entries);

    interface KeyCreator<T extends Comparable<T>> {

        <M extends FitnessTested<T>> Function<M, Tuple2<T, M>> get();
    }
}
