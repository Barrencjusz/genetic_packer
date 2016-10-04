package genetic.packer.adapter;

import genetic.packer.Packer;
import genetic.packer.PackerContextBuilder;
import genetic.packer.dto.request.ContainerDto;
import genetic.packer.dto.request.ParamsDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.response.IndividualDtoBuilder;
import genetic.packer.dto.response.ResponseDto;
import genetic.packer.dto.response.ResponseDtoBuilder;
import genetic.packer.generation.dto.EmbryoBuilder;
import genetic.packer.generation.dto.Individual;
import genetic.packer.mapper.BoxMapper;
import javafx.geometry.Bounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                .withNumberOfBestIndividuals(requestDto.getNumberOfTopIndividuals())
                .withEmbryo(new EmbryoBuilder()
                    .withBounds(containerToBoundsMapper.apply(requestDto.getEmbryo().getContainer()))
                    .withBoxes(requestDto.getEmbryo().getBoxes().stream().map(boxMapper::map).collect(Collectors.toList()))
                    .build())
                .build();

        final Packer.Result<Double, Individual> result = packer.apply(context);

        return new ResponseDtoBuilder()
                .withTopIndividuals(result
                        .getBestIndividuals()
                        .stream()
                        .map(detailedIndividual -> {
                            return new IndividualDtoBuilder()
                                    .withFitness(detailedIndividual.getFitness())
                                    .withTranslatedBoxes(detailedIndividual
                                            .get()
                                            .getCells()
                                            .stream()
                                            .map(boxMapper::map)
                                            .collect(Collectors.toList())
                                    )
                                    .withNumberOfGeneration(detailedIndividual.getNumberOfGeneration())
                                    .build();
                        })
                        .collect(Collectors.toList())
                )
                .withContainer(requestDto.getEmbryo().getContainer())
                .build();
    }
}
