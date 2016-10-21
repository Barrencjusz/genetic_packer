package genetic.selectors.roulette.impl;

import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genetic.selectors.PairSelector;
import genetic.selectors.dto.FitnessTested;
import genetic.selectors.roulette.RouletteDistancer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
    public <M extends FitnessTested<Double>> PairSelector<Double, M> from(Collection<M> fitnessTesteds) {
        return sizeFunction -> {
            fitnessSum = fitnessTesteds.stream().mapToDouble(FitnessTested::getFitness).sum();
            TreeMap<Double, M> treeMap = rouletteDistancer.distance(fitnessTesteds);

            return Stream.generate(getPair(treeMap))
                .limit(sizeFunction.apply(fitnessTesteds.size()))
                .collect(Collectors.toList());
        };
    }

    private <T> Supplier<Pair<T, T>> getPair(TreeMap<Double, T> treeMap) {
        return () -> new ImmutablePair<>(getSingle(treeMap), getSingle(treeMap));
    }

    private <T> T getSingle(TreeMap<Double, T> treeMap) {
        return treeMap.ceilingEntry(random.get().nextDouble(fitnessSum)).getValue();
    }
}