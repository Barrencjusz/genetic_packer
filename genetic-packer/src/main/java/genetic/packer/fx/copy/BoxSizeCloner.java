package genetic.packer.fx.copy;

import javafx.scene.shape.Box;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class BoxSizeCloner {

    public Box clone(Box box) {
        return new Box(box.getWidth(), box.getHeight(), box.getDepth());
    }

    public abstract List<Box> clone(List<Box> boxes);
}
