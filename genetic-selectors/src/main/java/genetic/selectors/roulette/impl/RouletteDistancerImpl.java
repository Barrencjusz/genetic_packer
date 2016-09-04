package genetic.selectors.roulette.impl;

import genetic.selectors.dto.RatedIndividual;
import genetic.selectors.roulette.RouletteDistancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class RouletteDistancerImpl implements RouletteDistancer {

    @Autowired
    private Supplier<Function<RatedIndividual<Double, ?>, Double>> distancedKeyCreator;

    @Override
    public <T> TreeMap<Double, T> distance(Collection<RatedIndividual<Double, T>> entries) {
        return entries.stream().collect(Collectors.toMap(this.distancedKeyCreator.get(), RatedIndividual::get, (a, b) -> a, TreeMap::new));
    }

    @Component
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public static class DistancedKeyCreator implements Supplier<Function<RatedIndividual<Double, ?>, Double>> {

        private Double currentKeyDistance = 0.0;

        @Override
        public Function<RatedIndividual<Double, ?>, Double> get() {
            return individual -> {
                currentKeyDistance += individual.getFitness();
                return currentKeyDistance;
            };
        }
    }
}