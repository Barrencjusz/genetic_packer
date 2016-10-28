package genetic.packer.evolution.fitness;

import genetic.api.builder.HasBuilder;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public class FitnessComponents extends TranslatedFitness<FitnessComponents, Double> {

    private Traversable<Boolean> intersections;

    private Double volume;

    @HasBuilder
    public FitnessComponents(
        FitnessTranslator<FitnessComponents, Double> totalFitnessTranslator,
        FitnessTranslator<FitnessComponents, String> fitnessExplainedTranslator
    ) {
        super(totalFitnessTranslator, fitnessExplainedTranslator);
    }

    @Override
    protected FitnessComponents self() {
        return this;
    }

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
