package genetic.packer.fx.copy;

import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

@Component
public class BoxSizeCloner {

    public Box clone(Box box) {
        return new Box(box.getWidth(), box.getHeight(), box.getDepth());
    }
}
