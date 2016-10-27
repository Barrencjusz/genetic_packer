package genetic.packer.fx.copy;


import javafx.scene.shape.Box;

/**
 * @author piotr.larysz
 */
public abstract class BoxSizeCloner {

    public Box clone(Box box) {
        return new Box(box.getWidth(), box.getHeight(), box.getDepth());
    }
}
