package genetic.packer.evolution.fitness;

import genetic.api.builder.HasBuilder;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class FitnessComponents {

    private Traversable<Boolean> intersections;

    private Double volume;

    public Traversable<Boolean> getIntersections() {
        return intersections;
    }

    public void setIntersections(Traversable<Boolean> intersections) {
        this.intersections = intersections;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
