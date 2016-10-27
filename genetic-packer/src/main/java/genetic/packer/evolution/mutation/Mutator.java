package genetic.packer.evolution.mutation;

import java.util.function.BiConsumer;

import genetic.api.individual.Organism;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;

/**
 * @author piotr.larysz
 */
public interface Mutator extends BiConsumer<Organism<Box>, Bounds> {
}
