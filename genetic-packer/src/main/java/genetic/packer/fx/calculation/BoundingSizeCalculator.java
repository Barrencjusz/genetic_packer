package genetic.packer.fx.calculation;

import java.util.function.Function;

import genetic.packer.fx.specification.BoundsGetter;
import javafx.geometry.Bounds;
import javaslang.Tuple2;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public interface BoundingSizeCalculator extends Function<Tuple2<BoundsGetter, BoundsGetter>, Double> {

    interface Creator {

        BoundingSizeCalculator from(Traversable<Bounds> bounds);
    }
}
