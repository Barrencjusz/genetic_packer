package genetic.presenter.javafx.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author piotr.larysz
 */
@Component
public class BaseViewCreator implements Consumer<Stage> {

    @Autowired
    private Scene scene;

    @Override
    public void accept(Stage stage) {

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setTitle("Genetic Packer Application");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}
