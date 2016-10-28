package genetic.selectors.roulette.impl;


import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import genetic.api.fitness.Fitness;
import genetic.api.individual.FitnessTested;
import genetic.selectors.PairSelector;
import genetic.selectors.roulette.RouletteDistancer;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Stream;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
public class RoulettePairSelectorCreator implements PairSelector.Creator<Double> {

    @Autowired
    private RouletteDistancer<Double> rouletteDistancer;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    private double fitnessSum; //fixme possible concurrency issue

    @Override
    public <M extends FitnessTested<Double>> PairSelector<M> from(Traversable<M> fitnessTesteds) {
        return sizeFunction -> {
            fitnessSum = fitnessTesteds.toList().map(FitnessTested::getFitness).map(Fitness::get).sum().doubleValue();
            TreeMap<Double, M> treeMap = rouletteDistancer.distance(fitnessTesteds);

            return Stream.continually(getPair(treeMap)).take(sizeFunction.apply(fitnessTesteds.size()));
        };
    }

    private <T> Supplier<Tuple2<T, T>> getPair(TreeMap<Double, T> treeMap) {
        return () -> Tuple.of(getSingle(treeMap), getSingle(treeMap));
    }

    private <T> T getSingle(TreeMap<Double, T> treeMap) {
        return treeMap.ceilingEntry(random.get().nextDouble(fitnessSum)).getValue();
    }
}