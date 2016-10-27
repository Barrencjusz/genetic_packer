package genetic.recombinators.impl.child;

import genetic.recombinators.chromosome.ChromosomeRecombinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class TwoChildrenRecombinator<T1> extends ChildRecombinator<T1> {

    @Autowired
    public TwoChildrenRecombinator(@Qualifier("twoChromosomesRecombinator") ChromosomeRecombinator<T1> chromosomeRecombinator) {
        super(chromosomeRecombinator);
    }
}
