package genetic.packer.evolution.generation.dto;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class Embryo {

    private Bounds bounds;

    private List<Box> boxes;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
