package genetic.packer.statistics;

import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
public interface GenerationStatisticsCreator {

    Seq<GenerationStatistics> create(Seq<Generation<Double, Box>> generations);
}
