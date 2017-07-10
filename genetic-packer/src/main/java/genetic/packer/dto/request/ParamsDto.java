package genetic.packer.dto.request;

/**
 * @author piotr.larysz
 */
public class ParamsDto {

    private Boolean showFitness;

    private Boolean showStatistics;

    public Boolean getShowFitness() {
        return showFitness;
    }

    public void setShowFitness(Boolean showFitness) {
        this.showFitness = showFitness;
    }

    public Boolean getShowStatistics() {
        return showStatistics;
    }

    public void setShowStatistics(Boolean showStatistics) {
        this.showStatistics = showStatistics;
    }
}
