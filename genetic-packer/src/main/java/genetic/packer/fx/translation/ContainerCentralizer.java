package genetic.packer.fx.translation;

import java.util.function.Consumer;

import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

@Component
public class ContainerCentralizer implements Consumer<Box> {

    private static final int DIVIDER = 2;

    @Override
    public void accept(Box box) {
        box.setTranslateX(box.getWidth() / DIVIDER);
        box.setTranslateY(box.getHeight() / DIVIDER);
        box.setTranslateZ(box.getDepth() / DIVIDER);
    }
}
