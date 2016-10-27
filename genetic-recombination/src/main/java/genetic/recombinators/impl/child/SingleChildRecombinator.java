package genetic.recombinators.impl.child;

import genetic.recombinators.chromosome.ChromosomeRecombinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class SingleChildRecombinator<T1> extends ChildRecombinator<T1> {

    @Autowired
    public SingleChildRecombinator(@Qualifier("singleChromosomeRecombinator") ChromosomeRecombinator<T1> chromosomeRecombinator) {
        super(chromosomeRecombinator);
    }
}
