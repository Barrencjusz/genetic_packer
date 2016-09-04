package genetic.presenter.javafx.view.controls;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class ControlledPerspectiveCamera extends PerspectiveCamera {

    @Autowired
    public ControlledPerspectiveCamera(@Value("${camera.clip.near}") Double nearClip,
                                       @Value("${camera.initial.distance}") Double initialDistance)
                                       /*@Value("#{T(ControlledPerspectiveCamera$RotationType).valueOf(${camera.rotation.type})}") RotationType rotationType)*/ {
        super(true);
        //cameraHolder.translate.setZ(50);
        this.setNearClip(nearClip);
        this.setFarClip(Double.MAX_VALUE);
        this.setTranslateZ(initialDistance);
    }
}
