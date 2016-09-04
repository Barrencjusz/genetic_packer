package genetic.packer.generation.dto;

import javafx.scene.Group;
import javafx.scene.shape.Box;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class Individual {

    private List<Box> boxes;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
