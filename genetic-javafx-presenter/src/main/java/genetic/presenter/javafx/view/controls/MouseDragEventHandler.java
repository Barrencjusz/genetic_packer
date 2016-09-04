package genetic.presenter.javafx.view.controls;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class MouseDragEventHandler implements EventHandler<MouseEvent> {

    @Autowired
    private MousePositions mousePositions;

    @Autowired
    private CameraHolder cameraHolder;

    @Value("${camera.mouse.multiplier.control}")
    private Double controlMultiplier;

    @Value("${camera.mouse.multiplier.shift}")
    private Double shiftMultiplier;

    @Value("${camera.mouse.speed}")
    private Double mouseSpeed;

    @Value("${camera.rotation.speed}")
    private Double rotationSpeed;

    @Override //TODO some x y separation refactorings
    public void handle(MouseEvent event) {
        mousePositions.setMouseOldX(mousePositions.getMouseNewX());
        mousePositions.setMouseOldY(mousePositions.getMouseNewY());
        mousePositions.setMouseNewX(event.getSceneX());
        mousePositions.setMouseNewY(event.getSceneY());
        double mouseDeltaX = mousePositions.getMouseNewX() - mousePositions.getMouseOldX();
        double mouseDeltaY = mousePositions.getMouseNewY() - mousePositions.getMouseOldY();

        double modifier = 1.0;

        if (event.isControlDown()) {
            modifier = controlMultiplier;
        }
        if (event.isShiftDown()) {
            modifier = shiftMultiplier;
        }
        final double xAngle = cameraHolder.getxRotation().getAngle() - mouseDeltaY * mouseSpeed * modifier * rotationSpeed;
        final double yAngle = cameraHolder.getyRotation().getAngle() + mouseDeltaX * mouseSpeed * modifier * rotationSpeed;
        cameraHolder.getyRotation().setAngle(yAngle);
        cameraHolder.getxRotation().setAngle(xAngle);
    }
}
