package genetic.packer.adapter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import genetic.packer.Packer;
import genetic.packer.PackerContextBuilder;
import genetic.packer.dto.request.ContainerDto;
import genetic.packer.dto.request.ParamsDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.IndividualDtoBuilder;
import genetic.packer.dto.response.ResponseDto;
import genetic.packer.dto.response.ResponseDtoBuilder;
import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.EmbryoBuilder;
import genetic.packer.evolution.generation.dto.individual.impl.DetailedIndividual;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import genetic.packer.mapper.BoxMapper;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class RequestResponseAdapter implements BiFunction<RequestDto, ParamsDto, ResponseDto> {

    @Autowired
    private Packer packer;

    @Autowired
    private Function<ContainerDto, Bounds> containerToBoundsMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Override
    public ResponseDto apply(RequestDto requestDto, ParamsDto params) {

        final Packer.Context context = new PackerContextBuilder()
                .withNumberOfGenerations(requestDto.getNumberOfGenerations())
                .withGenerationSize(requestDto.getGenerationSize())
                .withNumberOfTopIndividuals(requestDto.getNumberOfTopIndividuals())
                .withEmbryo(new EmbryoBuilder()
                    .withBounds(containerToBoundsMapper.apply(requestDto.getEmbryo().getContainer()))
                    .withBoxes(requestDto.getEmbryo().getBoxes().stream().map(boxMapper::map).collect(Collectors.toList()))
                    .build())
                .build();

        final Packer.Result<Double, SimpleIndividual<Box>> result = packer.apply(context);

        return new ResponseDtoBuilder()
                .withTopIndividuals(this.createTopIndividualsDtos(result.getTopIndividuals()))
                .withContainer(requestDto.getEmbryo().getContainer())
                .withGenerationStats(result.getGenerationStats())
                .build();
    }

    public List<IndividualDto> createTopIndividualsDtos(List<DetailedIndividual<Double, SimpleIndividual<Box>>> topIndividuals) {
        return topIndividuals.stream()
                .map(this::createTopIndividualDto)
                .collect(Collectors.toList());

    }

    public IndividualDto createTopIndividualDto(DetailedIndividual<Double, SimpleIndividual<Box>> detailedIndividual) {
        return new IndividualDtoBuilder()
            .withFitness(detailedIndividual.getFitness())
            .withTranslatedBoxes(detailedIndividual
                .getCells()
                .stream()
                .map(Cell::getNucleus)
                .map(boxMapper::map)
                .collect(Collectors.toList())
            )
            .withNumberOfGeneration(detailedIndividual.getNumberOfGeneration())
            .build();
    }
}
