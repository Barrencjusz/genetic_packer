package genetic.packer.evolution.fitness;

import java.util.Collection;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class FitnessComponents {

    private Collection<Boolean> intersections;

    private Double volume;

    public Collection<Boolean> getIntersections() {
        return intersections;
    }

    public void setIntersections(Collection<Boolean> intersections) {
        this.intersections = intersections;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
