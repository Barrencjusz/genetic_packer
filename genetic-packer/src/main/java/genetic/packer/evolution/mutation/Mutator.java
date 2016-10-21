package genetic.packer.evolution.mutation;

import genetic.packer.evolution.generation.dto.individual.Individual;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;

import java.util.function.BiConsumer;

/**
 * @author piotr.larysz
 */
public interface Mutator extends BiConsumer<Individual<Box>, Bounds> {
}
