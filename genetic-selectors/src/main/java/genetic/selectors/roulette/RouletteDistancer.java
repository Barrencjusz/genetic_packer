package genetic.selectors.roulette;

import java.util.Collection;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.selectors.dto.FitnessTested;

/**
 * @author piotr.larysz
 */
public interface RouletteDistancer<T extends Comparable<T>> {

    <M extends FitnessTested<T>> TreeMap<T, M> distance(Collection<M> entries);

    interface KeyCreator<T extends Comparable<T>> extends Supplier<Function<FitnessTested<T>, T>> {

    }
}
