package genetic.api.individual.impl;

import genetic.api.elitism.EliteAcknowledged;
import genetic.api.elitism.Promoter;
import genetic.api.fitness.Evaluator;
import genetic.api.individual.Individual;
import genetic.api.individual.Promoted;

/**
 * @author piotr.larysz
 */
public class Elite<T1 extends Comparable<T1>, T2> extends RatedIndividual<T1, T2> implements Individual<T1, T2>, Promoted<T1, T2> {

    public Elite(RatedIndividual<T1, T2> ratedIndividual) {
        super(ratedIndividual.getFitness(), ratedIndividual);
    }

    @Override
    public <M extends Evaluator<T1, T2> & EliteAcknowledged> RatedIndividual<T1, T2> evaluate(M evaluator) {
        return evaluator.acknowledge(this);
    }

    @Override
    public <M extends Promoter & EliteAcknowledged> Elite<T1, T2> promote(M promoter) {
        return promoter.acknowledge(this);
    }
}
