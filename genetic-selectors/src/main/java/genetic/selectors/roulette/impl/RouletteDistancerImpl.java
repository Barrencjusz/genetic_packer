package genetic.selectors.roulette.impl;

import java.util.Collection;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import genetic.selectors.dto.FitnessTested;
import genetic.selectors.roulette.RouletteDistancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class RouletteDistancerImpl implements RouletteDistancer<Double> {

    @Autowired
    private KeyCreator<Double> distancedKeyCreator;

    @Override
    public <M extends FitnessTested<Double>> TreeMap<Double, M> distance(Collection<M> entries) {
        return entries.stream()
            .collect(Collectors.toMap(this.distancedKeyCreator.get(), Function.identity(), (a, b) -> a, TreeMap::new));
    }

    @Component
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)//fixme possible concurrency issue
    public static class DistancedKeyCreator implements KeyCreator<Double> {

        private Double currentKeyDistance = 0.0;

        @Override
        public Function<FitnessTested<Double>, Double> get() {
            return fitnessTested -> {
                currentKeyDistance += fitnessTested.getFitness();
                return currentKeyDistance;
            };
        }
    }
}