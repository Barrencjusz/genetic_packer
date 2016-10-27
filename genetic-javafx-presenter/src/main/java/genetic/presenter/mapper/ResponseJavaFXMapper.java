package genetic.presenter.mapper;

import genetic.packer.dto.BoxDto;
import genetic.packer.dto.BoxDtoBuilder;
import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.ResponseDto;
import genetic.packer.dto.response.TranslatedBoxDto;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class ResponseJavaFXMapper {

    @Autowired
    private BiConsumer<Box, Integer> xTranslator;

    @Autowired
    private BiConsumer<Box, Integer> yTranslator;

    @Autowired
    private BiConsumer<Box, Integer> zTranslator;

    @Autowired
    private Supplier<Color> randomColor;

    public List<Node> map(IndividualDto individual) {
        return individual.getTranslatedBoxes().map(this::map).collect(Collectors.toList());
    }

    //TODO associate transators with getters, mapstruct
    public Box map(TranslatedBoxDto boxDto) {
        Box box = map((BoxDto) boxDto);
        xTranslator.accept(box, boxDto.getPosition().getX());
        yTranslator.accept(box, boxDto.getPosition().getY());
        zTranslator.accept(box, boxDto.getPosition().getZ());
        box.setMaterial(new PhongMaterial(randomColor.get()));
        return box;
    }

    public Box map(BoxDto boxDto) {
        return new Box(boxDto.getWidth(), boxDto.getHeight(), boxDto.getDepth());
    }
}
