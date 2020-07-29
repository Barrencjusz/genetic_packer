package genetic.presenter.javafx.view.controls;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MousePressEventHandler implements EventHandler<MouseEvent> {

    @Autowired
    private MousePositions mousePositions;

    @Override
    public void handle(MouseEvent event) {
        mousePositions.setMouseNewX(event.getSceneX());
        mousePositions.setMouseNewY(event.getSceneY());
    }
}
