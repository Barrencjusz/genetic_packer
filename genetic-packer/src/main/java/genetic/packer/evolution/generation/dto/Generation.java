package genetic.packer.evolution.generation.dto;

import genetic.api.builder.HasBuilder;
import genetic.api.individual.impl.RatedIndividual;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class Generation<V extends Comparable<V>, T> {

    private Integer id;

    private Traversable<RatedIndividual<V, T>> ratedIndividuals;

//    private Collection<T> rawIndividuals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Traversable<RatedIndividual<V, T>> getRatedIndividuals() {
        return ratedIndividuals;
    }

    public void setRatedIndividuals(Traversable<RatedIndividual<V, T>> ratedIndividuals) {
        this.ratedIndividuals = ratedIndividuals;
    }

//    public Collection<T> getRawIndividuals() {
//        return rawIndividuals;
//    }
//
//    public void setRawIndividuals(Collection<T> rawIndividuals) {
//        this.rawIndividuals = rawIndividuals;
//    }
}
