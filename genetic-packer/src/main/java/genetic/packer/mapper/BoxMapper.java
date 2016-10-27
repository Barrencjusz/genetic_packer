package genetic.packer.mapper;

import genetic.packer.dto.BoxDto;
import genetic.packer.dto.response.TranslatedBoxDto;
import genetic.packer.dto.response.TranslatedBoxDtoBuilder;
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
            .width((int) box.getWidth())
            .height((int) box.getHeight())
            .depth((int) box.getDepth())
            .position(new TranslationDtoBuilder<Integer>()
                .x((int) box.getBoundsInParent().getMinX())
                .y((int) box.getBoundsInParent().getMinY())
                .z((int) box.getBoundsInParent().getMinZ())
                .build())
            .build();
    }
}
