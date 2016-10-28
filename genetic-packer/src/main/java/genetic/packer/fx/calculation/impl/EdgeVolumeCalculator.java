package genetic.packer.fx.calculation.impl;


import java.util.function.Function;

import genetic.packer.fx.calculation.BoundingSizeCalculator;
import genetic.packer.fx.specification.BoundsGetter;
import javafx.geometry.Bounds;
import javaslang.Tuple2;
import javaslang.collection.Set;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class EdgeVolumeCalculator implements Function<Traversable<Bounds>, Double> {

    @Autowired
    private Set<Tuple2<BoundsGetter, BoundsGetter>> boundsGetters;

    @Autowired
    private BoundingSizeCalculator.Creator boundingSizeCalculatorCreator;

    @Override
    public Double apply(Traversable<Bounds> allBoxesBounds) {
        return boundsGetters
            .map(boundingSizeCalculatorCreator.from(allBoxesBounds))
            .map(Double::valueOf)
            .reduce((a, b) -> a * b);
    }
}
