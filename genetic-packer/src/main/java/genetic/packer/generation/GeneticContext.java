package genetic.packer.generation;

import genetic.packer.generation.dto.Embryo;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class GeneticContext {

    private Embryo embryo;

    private Integer chromosomeSize;

    private Integer generationSize;

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
}
