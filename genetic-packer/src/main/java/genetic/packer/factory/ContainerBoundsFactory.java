package genetic.packer.factory;

import javafx.beans.NamedArg;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class ContainerBoundsFactory {

    @GeneratePojoBuilder(withName = "ContainerBoundsBuilder")
    public static Bounds createBounds(double width, double height, double depth) {
        return new BoundingBox(0, 0, 0, width, height, depth);
    }
}
