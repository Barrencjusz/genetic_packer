package genetic.packer.mapper;

import genetic.packer.dto.BoxDto;
import genetic.packer.dto.response.TranslatedBoxDto;
import genetic.packer.dto.response.TranslatedBoxDtoBuilder;
import genetic.packer.dto.response.TranslationDto;
import genetic.packer.dto.response.TranslationDtoBuilder;
import javafx.scene.shape.Box;
import org.mapstruct.Mapper;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class BoxMapper {

    public abstract Box map(BoxDto boxDto);

    public TranslatedBoxDto map(Box box) {
        return new TranslatedBoxDtoBuilder()
                .withWidth((int) box.getWidth())
                .withHeight((int) box.getHeight())
                .withDepth((int) box.getDepth())
                .withPosition(new TranslationDtoBuilder<Integer>()
                        .withX((int) box.getBoundsInParent().getMinX())
                        .withY((int) box.getBoundsInParent().getMinY())
                        .withZ((int) box.getBoundsInParent().getMinZ())
                        .build())
                .build();
    }
}
