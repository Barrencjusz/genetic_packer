package genetic.packer.fx.calculation.impl;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import genetic.packer.fx.calculation.BoundingSizeCalculator;
import genetic.packer.fx.specification.BoundsGetter;
import javafx.geometry.Bounds;
import javaslang.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class EdgeVolumeCalculator implements Function<Collection<Bounds>, Double> {

    @Autowired
    private Set<Tuple2<BoundsGetter, BoundsGetter>> boundsGetters;

    @Autowired
    private BoundingSizeCalculator.Creator boundingSizeCalculatorCreator;

    @Override
    public Double apply(Collection<Bounds> allBoxesBounds) {
        return boundsGetters.stream()
            .map(boundingSizeCalculatorCreator.from(allBoxesBounds))
            .mapToDouble(Double::valueOf)
            .reduce(1, (a, b) -> a * b);
    }
}
