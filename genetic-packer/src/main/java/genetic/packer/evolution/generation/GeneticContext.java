package genetic.packer.evolution.generation;

import genetic.packer.evolution.generation.dto.Embryo;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class GeneticContext {

    private Embryo embryo;

    private Integer chromosomeSize;

    private Integer generationSize;

    private Integer numberOfEliteIndividuals;

    public Embryo getEmbryo() {
        return embryo;
    }

    public void setEmbryo(Embryo embryo) {
        this.embryo = embryo;
    }

    public Integer getChromosomeSize() {
        return chromosomeSize;
    }

    public void setChromosomeSize(Integer chromosomeSize) {
        this.chromosomeSize = chromosomeSize;
    }

    public Integer getGenerationSize() {
        return generationSize;
    }

    public void setGenerationSize(Integer generationSize) {
        this.generationSize = generationSize;
    }

    public Integer getNumberOfEliteIndividuals() {
        return numberOfEliteIndividuals;
    }

    public void setNumberOfEliteIndividuals(Integer numberOfEliteIndividuals) {
        this.numberOfEliteIndividuals = numberOfEliteIndividuals;
    }
}
