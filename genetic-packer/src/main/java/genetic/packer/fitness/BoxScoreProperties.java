package genetic.packer.fitness;

import javafx.scene.shape.Box;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class BoxScoreProperties {

    private boolean intersects;

    private Box box;

    public BoxScoreProperties(Box box) {
        this.box = box;
    }

    public boolean isIntersects() {
        return intersects;
    }

    public void setIntersects(boolean intersects) {
        this.intersects = intersects;
    }

    public Box getBox() {
        return box;
    }
}
