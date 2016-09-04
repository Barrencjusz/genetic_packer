package genetic.packer.fitness;

import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.Individual;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessTester implements Function<Individual, Double> {

    @Autowired
    private EveryCombinationResolver everyCombinationResolver;

    @Autowired
    private Consumer<Map.Entry<BoxScoreProperties, BoxScoreProperties>> intersectionFitnessTester;

    @Override
    public Double apply(final Individual individual) {
        final List<BoxScoreProperties> boxScorePropertiesList = individual.getBoxes().stream().map(BoxScoreProperties::new).collect(Collectors.toList());
        final Collection<Map.Entry<BoxScoreProperties, BoxScoreProperties>> combinations = everyCombinationResolver.apply(boxScorePropertiesList);

        combinations.stream().forEach(intersectionFitnessTester::accept);
        double score = boxScorePropertiesList
                .stream()
                .map(this::rateScoreProperties)
                .mapToDouble(Double::valueOf)
                .reduce(1, (left, right) -> left * right);
        return score;
    }

    private double rateScoreProperties(BoxScoreProperties boxScoreProperties) {
        return boxScoreProperties.isIntersects() ? 1 : 1.5;
    }
}
