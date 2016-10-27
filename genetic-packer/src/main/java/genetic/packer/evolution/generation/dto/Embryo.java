package genetic.packer.evolution.generation.dto;

import genetic.api.builder.HasBuilder;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class Embryo {

    private Bounds bounds;

    private Seq<Box> boxes;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Seq<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(Seq<Box> boxes) {
        this.boxes = boxes;
    }
}
