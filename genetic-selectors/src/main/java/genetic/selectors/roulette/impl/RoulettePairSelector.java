package genetic.selectors.roulette.impl;

import genetic.selectors.PairSelector;
import genetic.selectors.dto.RatedIndividual;
import genetic.selectors.roulette.RouletteDistancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
public class RoulettePairSelector implements PairSelector<Double> {

    @Autowired
    private RouletteDistancer rouletteDistancer;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    private double fitnessSum;

    @Override
    public <T> Collection<Map.Entry<T, T>> select(Context<Double, T> context) {
        fitnessSum = context.getRatedIndividuals().stream().mapToDouble(RatedIndividual::getFitness).sum();
        TreeMap<Double, T> treeMap = rouletteDistancer.distance(context.getRatedIndividuals());

        return Stream.generate(getPair(treeMap)).limit(context.getPairsToSelect()).collect(Collectors.toList());
    }

    private <T> Supplier<Map.Entry<T, T>> getPair(TreeMap<Double, T> treeMap) {
        return () -> new AbstractMap.SimpleEntry<>(getSingle(treeMap), getSingle(treeMap));
    }

    private <T> T getSingle(TreeMap<Double, T> treeMap) {
        return treeMap.ceilingEntry(random.get().nextDouble(fitnessSum)).getValue();
    }
}