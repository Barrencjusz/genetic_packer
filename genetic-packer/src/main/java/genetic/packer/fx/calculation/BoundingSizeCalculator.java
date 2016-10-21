package genetic.packer.fx.calculation;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.packer.fx.specification.BoundsGetter;
import javafx.geometry.Bounds;
import javaslang.Tuple2;

/**
 * @author piotr.larysz
 */
public interface BoundingSizeCalculator extends Function<Tuple2<BoundsGetter, BoundsGetter>, Double> {

    interface Creator {

        BoundingSizeCalculator from(Collection<Bounds> bounds);
    }
}
