package genetic.packer.fx.calculation.impl;

import java.util.Collection;

import genetic.packer.fx.calculation.BoundingSizeCalculator;
import javafx.geometry.Bounds;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class BoundingSizeCalculatorCreator implements BoundingSizeCalculator.Creator {

    @Override
    public BoundingSizeCalculator from(Collection<Bounds> allBoxesBounds) {
        return tuple ->  tuple.transform((minimumProperty, maximumProperty) -> {
            final double max = allBoxesBounds.stream().map(maximumProperty).max(Double::compare).orElse(0.0);//todo maybe throw some exception?
            final double min = allBoxesBounds.stream().map(minimumProperty).min(Double::compare).orElse(0.0);
            return max - min;
        });
    }
}
