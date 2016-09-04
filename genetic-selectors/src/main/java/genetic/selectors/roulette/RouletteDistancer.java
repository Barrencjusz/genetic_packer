package genetic.selectors.roulette;

import genetic.selectors.dto.RatedIndividual;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author piotr.larysz
 */
public interface RouletteDistancer {
    <T> TreeMap<Double, T> distance(Collection<RatedIndividual<Double, T>> entries);
}
