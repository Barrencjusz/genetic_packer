package genetic.packer.dto.response;

import genetic.packer.dto.BoxDto;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class ResponseDto {

    private BoxDto container;

    private List<IndividualDto> topIndividuals;

    public BoxDto getContainer() {
        return container;
    }

    public void setContainer(BoxDto container) {
        this.container = container;
    }

    public List<IndividualDto> getTopIndividuals() {
        return topIndividuals;
    }

    public void setTopIndividuals(List<IndividualDto> topIndividuals) {
        this.topIndividuals = topIndividuals;
    }
}
