package genetic.packer.statistics;

import java.util.List;

import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import genetic.packer.evolution.generation.dto.Individual;
import javafx.scene.shape.Box;

/**
 * @author piotr.larysz
 */
public interface GenerationStatisticsCreator {

    List<GenerationStatistics> create(List<Generation<Double, Individual<Box>>> generations);
}
