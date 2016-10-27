package genetic.packer.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

import genetic.api.individual.Cell;
import genetic.api.individual.impl.DetailedIndividual;
import genetic.packer.Packer;
import genetic.packer.PackerContextBuilder;
import genetic.packer.dto.request.ContainerDto;
import genetic.packer.dto.request.ParamsDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.IndividualDtoBuilder;
import genetic.packer.dto.response.ResponseDto;
import genetic.packer.dto.response.ResponseDtoBuilder;
import genetic.packer.evolution.generation.dto.EmbryoBuilder;
import genetic.packer.mapper.BoxMapper;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;
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
                .numberOfGenerations(requestDto.getNumberOfGenerations())
                .generationSize(requestDto.getGenerationSize())
                .numberOfTopIndividuals(requestDto.getNumberOfTopIndividuals())
                .embryo(new EmbryoBuilder()
                    .bounds(containerToBoundsMapper.apply(requestDto.getEmbryo().getContainer()))
                    .boxes(requestDto.getEmbryo().getBoxes().map(boxMapper::map))
                    .build())
                .build();

        final Packer.Result<Double, Box> result = packer.apply(context);

        return new ResponseDtoBuilder()
                .topIndividuals(this.createTopIndividualsDtos(result.getTopIndividuals()))
                .container(requestDto.getEmbryo().getContainer())
                .generationStats(result.getGenerationStats())
                .build();
    }

    public Seq<IndividualDto> createTopIndividualsDtos(Seq<DetailedIndividual<Double, Box>> topIndividuals) {
        return topIndividuals.map(this::createTopIndividualDto);

    }

    public IndividualDto createTopIndividualDto(DetailedIndividual<Double, Box> detailedIndividual) {
        return new IndividualDtoBuilder()
            .fitness(detailedIndividual.getFitness())
            .translatedBoxes(detailedIndividual
                .getCells()
                .map(Cell::getNucleus)
                .map(boxMapper::map)
            )
            .numberOfGeneration(detailedIndividual.getNumberOfGeneration())
            .build();
    }
}
