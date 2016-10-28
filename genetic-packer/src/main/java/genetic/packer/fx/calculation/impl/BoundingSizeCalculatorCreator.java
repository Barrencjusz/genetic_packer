package genetic.packer.fx.calculation.impl;

import genetic.packer.fx.calculation.BoundingSizeCalculator;
import javafx.geometry.Bounds;
import javaslang.collection.Traversable;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class BoundingSizeCalculatorCreator implements BoundingSizeCalculator.Creator {

    @Override
    public BoundingSizeCalculator from(Traversable<Bounds> allBoxesBounds) {
        return tuple ->  tuple.transform((minimumProperty, maximumProperty) -> {
            final double max = allBoxesBounds.map(maximumProperty).max().getOrElse(0.0);//todo maybe throw some exception?
            final double min = allBoxesBounds.map(minimumProperty).min().getOrElse(0.0);
            return max - min;
        });
    }
}
