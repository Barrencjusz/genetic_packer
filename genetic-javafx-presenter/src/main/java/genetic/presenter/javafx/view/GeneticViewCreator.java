package genetic.presenter.javafx.view;

import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.ResponseDto;
import genetic.presenter.javafx.view.controls.CameraHolder;
import genetic.presenter.mapper.ResponseJavaFXMapper;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author piotr.larysz
 */
@Component
public class GeneticViewCreator implements Consumer<ResponseDto> {

    @Autowired
    private Group group;

    @Autowired
    private ResponseJavaFXMapper mapper;

    @Autowired
    private Consumer<Box> containerCentralizer;

    @Autowired
    private CameraHolder cameraHolder;

    @Override
    public void accept(ResponseDto responseDto) {
        final IndividualDto topIndividual = responseDto.getTopIndividuals().get(0);
        group.getChildren().clear();
        group.getChildren().addAll(mapper.map(topIndividual));
        final Box container = mapper.map(responseDto.getContainer()); //TODO disable color randomizing for container
        container.setDrawMode(DrawMode.LINE);
        container.setCullFace(CullFace.NONE);//FIXME
        containerCentralizer.accept(container);
        group.getChildren().add(container);

        cameraHolder.getTranslate().setX(90); //FIXME
        cameraHolder.getTranslate().setZ(200);
        cameraHolder.getTranslate().setY(110);
    }
}
