package genetic.api.individual;


import genetic.api.elitism.EliteAcknowledged;
import genetic.api.elitism.Promoter;
import genetic.api.individual.impl.Elite;

/**
 * @author piotr.larysz
 */
public interface Promoted<T1 extends Comparable<T1>, T2>  {

    <M extends Promoter & EliteAcknowledged> Elite<T1, T2> promote(M promotor);
}
