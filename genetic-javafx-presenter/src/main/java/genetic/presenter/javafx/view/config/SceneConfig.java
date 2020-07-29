package genetic.presenter.javafx.view.config;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SceneConfig {

    @Autowired
    private Camera controlledPerspectiveCamera;

    @Autowired
    private EventHandler<? super MouseEvent> mouseDragEventHandler;

    @Autowired
    private EventHandler<? super MouseEvent> mousePressEventHandler;

    @Bean
    @Lazy
    public Scene scene(Group group) {
        final Scene scene = new Scene(group, -1, -1, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.LIGHTGREY);
        scene.setCamera(controlledPerspectiveCamera);
        scene.setOnMousePressed(mousePressEventHandler);
        scene.setOnMouseDragged(mouseDragEventHandler);
        return scene;
    }
}
