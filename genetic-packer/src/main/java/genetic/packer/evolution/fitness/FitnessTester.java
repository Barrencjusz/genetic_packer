package genetic.packer.evolution.fitness;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import genetic.packer.fx.Cell;
import genetic.packer.evolution.generation.dto.Individual;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class FitnessTester implements Function<Individual<Box>, Double> {

    @Autowired
    private EveryCombinationResolver everyCombinationResolver;

    @Autowired
    private Consumer<Map.Entry<BoxScoreProperties, BoxScoreProperties>> intersectionFitnessTester;

    @Override
    public Double apply(final Individual<Box> individual) {
        final List<BoxScoreProperties> boxScorePropertiesList = individual.getCells().stream().map(Cell::getNucleus).map(BoxScoreProperties::new).collect(Collectors.toList());

        final List<Bounds> allBoxesBounds = individual.getCells().stream().map(Cell::getNucleus).map(Node::getBoundsInParent).collect(Collectors.toList());
        final double maxX = allBoxesBounds.stream().map(Bounds::getMaxX).max(Double::compare).orElse(0.0);
        final double maxY = allBoxesBounds.stream().map(Bounds::getMaxY).max(Double::compare).orElse(0.0);
        final double maxZ = allBoxesBounds.stream().map(Bounds::getMaxZ).max(Double::compare).orElse(0.0);

        final double minX = allBoxesBounds.stream().map(Bounds::getMinX).min(Double::compare).orElse(0.0);
        final double minY = allBoxesBounds.stream().map(Bounds::getMinY).min(Double::compare).orElse(0.0);
        final double minZ = allBoxesBounds.stream().map(Bounds::getMinZ).min(Double::compare).orElse(0.0);

        final double width = maxX - minX;
        final double height = maxY - minY;
        final double depth = maxZ - minZ;

        final double volume = width * height * depth;

        everyCombinationResolver.apply(boxScorePropertiesList).forEach(this.intersectionFitnessTester);
        final double fitness = boxScorePropertiesList
                .stream()
                .map(this::rateScoreProperties)
                .mapToDouble(Double::valueOf)
                .reduce(1, (left, right) -> left * right) + 1 / (volume * 100);
        return fitness;
    }

    private double rateScoreProperties(BoxScoreProperties boxScoreProperties) {
        return boxScoreProperties.isIntersects() ? 1 : 1.5;
    }
}
