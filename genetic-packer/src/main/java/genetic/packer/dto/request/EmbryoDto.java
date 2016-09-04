package genetic.packer.dto.request;

import genetic.packer.dto.BoxDto;
import genetic.packer.dto.request.ContainerDto;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class EmbryoDto {

    private ContainerDto container;

    private List<BoxDto> boxes;

    public List<BoxDto> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxDto> boxes) {
        this.boxes = boxes;
    }

    public ContainerDto getContainer() {
        return container;
    }

    public void setContainer(ContainerDto container) {
        this.container = container;
    }
}
