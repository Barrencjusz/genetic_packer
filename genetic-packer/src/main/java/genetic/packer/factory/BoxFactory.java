package genetic.packer.factory;

import javafx.scene.shape.Box;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class BoxFactory {

    @GeneratePojoBuilder(withName = "BoxBuilder", withSetterNamePattern = "*", includeProperties = {"width", "height", "depth"})
    public static Box createBox(double width, double height, double depth) {
        return new Box(width, height, depth);
    }
}
