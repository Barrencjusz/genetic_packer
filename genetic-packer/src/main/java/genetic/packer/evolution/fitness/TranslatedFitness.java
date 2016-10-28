package genetic.packer.evolution.fitness;

import genetic.api.fitness.Fitness;
import javaslang.Lazy;

/**
 * @author piotr.larysz
 */
public abstract class TranslatedFitness<T1 extends TranslatedFitness<T1, T2>, T2 extends Comparable<T2>> implements Fitness<T2> {

    private Lazy<T2> fitness;

    private Lazy<String> explanation;

    public TranslatedFitness(
        FitnessTranslator<T1, T2> totalFitnessTranslator,
        FitnessTranslator<T1, String> fitnessExplainedTranslator
    ) {
        this.fitness = Lazy.of(() -> totalFitnessTranslator.apply(self()));
        this.explanation = Lazy.of(() -> fitnessExplainedTranslator.apply(self()));
    }

    @Override
    public String explain() {
        return explanation.get();
    }

    @Override
    public T2 get() {
        return fitness.get();
    }

    protected abstract T1 self();
}
