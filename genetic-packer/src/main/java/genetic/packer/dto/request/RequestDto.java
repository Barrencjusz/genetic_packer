package genetic.packer.dto.request;

import genetic.packer.dto.request.validation.Even;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class RequestDto {

    private Integer numberOfGenerations;

    @Even
    @Min(2)
    private Integer generationSize;

    private Integer numberOfTopIndividuals; //fixme it's statistics property, move it to new node 'statistics' ? or make it parameter

    private Integer numberOfEliteIndividuals;

    private EmbryoDto embryo;

    public Integer getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(Integer numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public Integer getGenerationSize() {
        return generationSize;
    }

    public void setGenerationSize(Integer generationSize) {
        this.generationSize = generationSize;
    }

    public Integer getNumberOfTopIndividuals() {
        return numberOfTopIndividuals;
    }

    public void setNumberOfTopIndividuals(Integer numberOfTopIndividuals) {
        this.numberOfTopIndividuals = numberOfTopIndividuals;
    }

    public Integer getNumberOfEliteIndividuals() {
        return numberOfEliteIndividuals;
    }

    public void setNumberOfEliteIndividuals(Integer numberOfEliteIndividuals) {
        this.numberOfEliteIndividuals = numberOfEliteIndividuals;
    }

    public EmbryoDto getEmbryo() {
        return embryo;
    }

    public void setEmbryo(EmbryoDto embryo) {
        this.embryo = embryo;
    }
}
