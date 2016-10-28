package genetic.selectors.roulette.impl;

import java.util.TreeMap;
import java.util.function.Function;

import genetic.api.individual.FitnessTested;
import genetic.selectors.roulette.RouletteDistancer;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Traversable;
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
    public <M extends FitnessTested<Double>> TreeMap<Double, M> distance(Traversable<M> entries) {
        return entries.toJavaMap(TreeMap::new, this.distancedKeyCreator.get());
    }

    @Component
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)//fixme possible concurrency issue
    public static class DistancedKeyCreator implements KeyCreator<Double> {

        private Double currentKeyDistance = 0.0;

        @Override
        public <M extends FitnessTested<Double>> Function<M, Tuple2<Double, M>> get() {
            return fitnessTested -> Tuple.of(currentKeyDistance += fitnessTested.getFitness().get(), fitnessTested);
        }
    }
}