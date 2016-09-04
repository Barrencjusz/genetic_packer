package genetic.packer.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import genetic.packer.Packer;
import genetic.packer.PackerContextBuilder;
import genetic.packer.dto.request.ContainerDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.generation.dto.Embryo;
import genetic.packer.generation.dto.EmbryoBuilder;
import genetic.packer.generation.dto.Individual;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.ContainerBoundsBuilder;
import javafx.scene.shape.Box;
import javafx.scene.shape.BoxBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessLoggingAdapter implements Runnable {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Function<ContainerDto, Bounds> containerToBoundsMapper;

    @Autowired
    private Packer packer;

    @Override
    public void run() {
        try {
            final RequestDto request = this.mapper.readValue(getClass().getResource("/packages.json"), RequestDto.class);

            final List<Box> boxes = request.getEmbryo().getBoxes().stream().map(boxDto -> {
                Box box = new Box();
                box.setWidth(boxDto.getWidth());
                box.setHeight(boxDto.getHeight());
                box.setDepth(boxDto.getDepth());
                return box;
            }).collect(Collectors.toList());

            final Embryo embryo = new EmbryoBuilder()
                    .withBounds(containerToBoundsMapper.apply(request.getEmbryo().getContainer()))
                    .withBoxes(boxes)
                    .build();

            final Packer.Context packerContext = new PackerContextBuilder()
                    .withNumberOfGenerations(request.getNumberOfGenerations())
                    .withGenerationSize(request.getGenerationSize())
                    .withNumberOfBestIndividuals(request.getNumberOfTopIndividuals())
                    .withEmbryo(embryo)
                    .build();
            Packer.Result<Double, Individual> packingResult = packer.apply(packerContext);
            packingResult
                    .getBestIndividuals()
                    .stream()
                    .forEach(individual -> System.out.println(individual.getFitness()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
