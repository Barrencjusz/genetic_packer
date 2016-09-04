package genetic.packer.dto.request;

/**
 * @author piotr.larysz
 */
public class ParamsDto {

    private Boolean showFitness;

    private Boolean perGenerationStats;

    public Boolean getShowFitness() {
        return showFitness;
    }

    public void setShowFitness(Boolean showFitness) {
        this.showFitness = showFitness;
    }

    public Boolean getPerGenerationStats() {
        return perGenerationStats;
    }

    public void setPerGenerationStats(Boolean perGenerationStats) {
        this.perGenerationStats = perGenerationStats;
    }
}
