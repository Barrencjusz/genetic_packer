package genetic.packer.evolution.fitness.impl;

import java.util.function.Consumer;
import java.util.function.Function;

import genetic.api.individual.Cell;
import genetic.api.individual.Individual;
import genetic.packer.evolution.fitness.BoxScoreProperties;
import genetic.packer.evolution.fitness.EveryCombinationResolver;
import genetic.packer.evolution.fitness.FitnessComponents;
import genetic.packer.evolution.fitness.FitnessComponentsBuilder;
import genetic.packer.evolution.fitness.FitnessTranslator;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import javaslang.Tuple2;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessTesterImpl implements Function<Individual<Double, Box>, FitnessComponents> {

    @Autowired
    private EveryCombinationResolver everyCombinationResolver;

    @Autowired
    private Consumer<Tuple2<BoxScoreProperties, BoxScoreProperties>> intersectionFitnessTester;

    @Autowired
    private Function<Traversable<Bounds>, Double> edgeVolumeCalculator;

    @Autowired
    private FitnessTranslator<FitnessComponents, Double> totalFitnessTranslator;

    @Autowired
    private FitnessTranslator<FitnessComponents, String> fitnessExplainedTranslator;

    @Override
    public FitnessComponents apply(final Individual<Double, Box> individual) {
        final Seq<BoxScoreProperties> boxScorePropertiesList = individual.getCells()
            .map(Cell::getNucleus)
            .map(BoxScoreProperties::new);

        everyCombinationResolver.apply(boxScorePropertiesList).forEach(this.intersectionFitnessTester);

        final Seq<Bounds> allBoxesBounds = individual.getCells()
            .map(Cell::getNucleus)
            .map(Node::getBoundsInParent);

        final FitnessComponents fitnessComponents = new FitnessComponentsBuilder()
                .intersections(boxScorePropertiesList.map(BoxScoreProperties::isIntersects))
                .volume(edgeVolumeCalculator.apply(allBoxesBounds))
                .totalFitnessTranslator(totalFitnessTranslator)
                .fitnessExplainedTranslator(fitnessExplainedTranslator)
                .build();
        return fitnessComponents;
    }
}
