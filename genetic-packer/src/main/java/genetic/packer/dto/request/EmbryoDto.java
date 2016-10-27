package genetic.packer.dto.request;

import genetic.api.builder.HasBuilder;
import genetic.packer.dto.BoxDto;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class EmbryoDto {

    private ContainerDto container;

    private Seq<BoxDto> boxes;

    public Seq<BoxDto> getBoxes() {
        return boxes;
    }

    public void setBoxes(Seq<BoxDto> boxes) {
        this.boxes = boxes;
    }

    public ContainerDto getContainer() {
        return container;
    }

    public void setContainer(ContainerDto container) {
        this.container = container;
    }
}
