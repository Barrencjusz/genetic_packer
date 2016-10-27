package genetic.packer.dto.response;

import genetic.api.builder.HasBuilder;
import genetic.packer.dto.BoxDto;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class TranslatedBoxDto extends BoxDto {

    private TranslationDto<Integer> position;

    private TranslationDto<Double> rotation;

    public TranslationDto<Integer> getPosition() {
        return position;
    }

    public void setPosition(TranslationDto<Integer> position) {
        this.position = position;
    }

    public TranslationDto<Double> getRotation() {
        return rotation;
    }

    public void setRotation(TranslationDto<Double> rotation) {
        this.rotation = rotation;
    }
}
