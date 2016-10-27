package genetic.packer.adapter;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import genetic.packer.Packer;
import genetic.packer.PackerContextBuilder;
import genetic.packer.dto.request.ContainerDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.EmbryoBuilder;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

            final Seq<Box> boxes = request.getEmbryo().getBoxes().map(boxDto -> {
                Box box = new Box();
                box.setWidth(boxDto.getWidth());
                box.setHeight(boxDto.getHeight());
                box.setDepth(boxDto.getDepth());
                return box;
            });

            final Embryo embryo = new EmbryoBuilder()
                    .bounds(containerToBoundsMapper.apply(request.getEmbryo().getContainer()))
                    .boxes(boxes)
                    .build();

            final Packer.Context packerContext = new PackerContextBuilder()
                    .numberOfGenerations(request.getNumberOfGenerations())
                    .generationSize(request.getGenerationSize())
                    .numberOfTopIndividuals(request.getNumberOfTopIndividuals())
                    .embryo(embryo)
                    .build();
            Packer.Result<Double, Box> packingResult = packer.apply(packerContext);
            packingResult
                    .getTopIndividuals().forEach(individual -> System.out.println(individual.getFitness()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
