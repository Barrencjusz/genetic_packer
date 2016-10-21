package genetic.packer.evolution.fitness.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import genetic.packer.evolution.fitness.BoxScoreProperties;
import genetic.packer.evolution.fitness.EveryCombinationResolver;
import genetic.packer.evolution.fitness.FitnessComponents;
import genetic.packer.evolution.fitness.FitnessComponentsBuilder;
import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessTesterImpl implements Function<SimpleIndividual<Box>, FitnessComponents> {

    @Autowired
    private EveryCombinationResolver everyCombinationResolver;

    @Autowired
    private Consumer<Map.Entry<BoxScoreProperties, BoxScoreProperties>> intersectionFitnessTester;

    @Autowired
    private Function<Collection<Bounds>, Double> edgeVolumeCalculator;

    @Override
    public FitnessComponents apply(final SimpleIndividual<Box> individual) {
        final List<BoxScoreProperties> boxScorePropertiesList = individual.getCells().stream()
            .map(Cell::getNucleus)
            .map(BoxScoreProperties::new)
            .collect(toList());

        everyCombinationResolver.apply(boxScorePropertiesList).forEach(this.intersectionFitnessTester);

        final List<Bounds> allBoxesBounds = individual.getCells().stream()
            .map(Cell::getNucleus)
            .map(Node::getBoundsInParent)
            .collect(toList());

        return new FitnessComponentsBuilder()
            .withIntersections(boxScorePropertiesList.stream().map(BoxScoreProperties::isIntersects).collect(toList()))
            .withVolume(edgeVolumeCalculator.apply(allBoxesBounds))
            .build();
    }
}
