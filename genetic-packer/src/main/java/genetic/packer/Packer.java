package genetic.packer;

import genetic.packer.generation.GeneticContext;
import genetic.packer.generation.dto.GenerationStatistics;
import genetic.packer.generation.dto.DetailedIndividual;
import genetic.packer.generation.dto.Embryo;
import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.Individual;
import genetic.packer.misc.Cast;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
public class Packer implements Function<Packer.Context, Packer.Result<Double, Individual>> {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private Function<Context, GeneticContext> packerContextMapper;

    @Autowired
    private BiFunction<List<Generation<Double, Individual>>, Integer, List<DetailedIndividual<Double, Individual>>> bestIndividualsSelector;

    @Override
    public Result<Double, Individual> apply(Context context) {
        Supplier<Generation<Double, Individual>> generationCreator = Cast.checked(beanFactory.getBean("statefulGenerationCreator", packerContextMapper.apply(context)));
        final List<Generation<Double, Individual>> generations = Stream
                .generate(generationCreator)
                .limit(context.getNumberOfGenerations())
                .collect(Collectors.toList());
        return new PackerResultBuilder<Double, Individual>()
                .withGenerations(generations)
                .withBestIndividuals(bestIndividualsSelector.apply(generations, context.getNumberOfBestIndividuals()))
                //.withGenerationStats()
                .build();
    }

    @GeneratePojoBuilder(withName = "Packer*Builder")
    public static class Context {

        private Integer numberOfGenerations;

        private Integer generationSize;

        private Embryo embryo;

        private Integer numberOfBestIndividuals;

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

        public Embryo getEmbryo() {
            return embryo;
        }

        public void setEmbryo(Embryo embryo) {
            this.embryo = embryo;
        }

        public Integer getNumberOfBestIndividuals() {
            return numberOfBestIndividuals;
        }

        public void setNumberOfBestIndividuals(Integer numberOfBestIndividuals) {
            this.numberOfBestIndividuals = numberOfBestIndividuals;
        }
    }

    @GeneratePojoBuilder(withName = "Packer*Builder")
    public static class Result<V extends Comparable<V>, T> {

        private List<Generation<V, T>> generations;

        private List<DetailedIndividual<V, T>> bestIndividuals;

        private List<GenerationStatistics> generationStats;

        public List<Generation<V, T>> getGenerations() {
            return generations;
        }

        public void setGenerations(List<Generation<V, T>> generations) {
            this.generations = generations;
        }

        public List<DetailedIndividual<V, T>> getBestIndividuals() {
            return bestIndividuals;
        }

        public void setBestIndividuals(List<DetailedIndividual<V, T>> bestIndividuals) {
            this.bestIndividuals = bestIndividuals;
        }

        public List<GenerationStatistics> getGenerationStats() {
            return generationStats;
        }

        public void setGenerationStats(List<GenerationStatistics> generationStats) {
            this.generationStats = generationStats;
        }
    }
}
