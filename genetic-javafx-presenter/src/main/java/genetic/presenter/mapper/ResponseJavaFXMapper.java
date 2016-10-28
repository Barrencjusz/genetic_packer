package genetic.presenter.mapper;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import genetic.packer.dto.BoxDto;
import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.TranslatedBoxDto;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public List<? extends Node> map(IndividualDto individual) {
        return individual.getTranslatedBoxes().map(this::map).toJavaList();
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
