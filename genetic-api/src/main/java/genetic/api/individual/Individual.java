package genetic.api.individual;

/**
 * @author piotr.larysz
 */
public interface Individual<T1 extends Comparable<T1>, T2> extends Evaluated<T1, T2>, Organism<T2> {

}
