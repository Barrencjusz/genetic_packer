package genetic.selectors.roulette.impl;

import genetic.selectors.PairSelector;
import genetic.selectors.dto.RatedIndividual;
import genetic.selectors.roulette.RouletteDistancer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
public class RoulettePairSelectorCreator implements PairSelector.Creator<Double> {

    @Autowired
    private RouletteDistancer rouletteDistancer;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    private double fitnessSum; //fixme possible concurrency issue

    @Override
    public <T> PairSelector<Double, T> from(Collection<RatedIndividual<Double, T>> ratedIndividuals) {
        return sizeFunction -> {
            fitnessSum = ratedIndividuals.stream().mapToDouble(RatedIndividual::getFitness).sum();
            TreeMap<Double, RatedIndividual<Double, T>> treeMap = rouletteDistancer.distance(ratedIndividuals);

            return Stream.generate(getPair(treeMap))
                .limit(sizeFunction.apply(ratedIndividuals.size()))
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