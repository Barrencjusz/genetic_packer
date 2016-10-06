package genetic.packer;

import genetic.packer.evolution.generation.GeneticContext;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import genetic.packer.evolution.generation.dto.DetailedIndividual;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.packer.misc.Cast;
import genetic.packer.statistics.GenerationStatisticsCreator;
import javafx.scene.shape.Box;
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
public class Packer implements Function<Packer.Context, Packer.Result<Double, Individual<Box>>> {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private Function<Context, GeneticContext> packerContextMapper;

    @Autowired
    private BiFunction<List<Generation<Double, Individual<Box>>>, Integer, List<DetailedIndividual<Double, Individual<Box>>>> bestIndividualsSelector;

    @Autowired
    private GenerationStatisticsCreator generationStatisticsCreator;

    @Override
    public Result<Double, Individual<Box>> apply(Context context) {
        Supplier<Generation<Double, Individual<Box>>> generationCreator = Cast.checked(beanFactory.getBean("statefulGenerationCreator", packerContextMapper.apply(context)));
        final List<Generation<Double, Individual<Box>>> generations = Stream
                .generate(generationCreator)
                .limit(context.getNumberOfGenerations())
                .collect(Collectors.toList());
        return new PackerResultBuilder<Double, Individual<Box>>()
                .withGenerations(generations)
                .withTopIndividuals(bestIndividualsSelector.apply(generations, context.getNumberOfTopIndividuals()))
                .withGenerationStats(generationStatisticsCreator.create(generations))
                .build();
    }

    @GeneratePojoBuilder(withName = "Packer*Builder")
    public static class Context {

        private Integer numberOfGenerations;

        private Integer generationSize;

        private Embryo embryo;

        private Integer numberOfTopIndividuals;

        private Integer numberOfEliteIndividuals;

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
    }

    @GeneratePojoBuilder(withName = "Packer*Builder")
    public static class Result<V extends Comparable<V>, T> {

        private List<Generation<V, T>> generations;

        private List<DetailedIndividual<V, T>> topIndividuals;

        private List<GenerationStatistics> generationStats;

        public List<Generation<V, T>> getGenerations() {
            return generations;
        }

        public void setGenerations(List<Generation<V, T>> generations) {
            this.generations = generations;
        }

        public List<DetailedIndividual<V, T>> getTopIndividuals() {
            return topIndividuals;
        }

        public void setTopIndividuals(List<DetailedIndividual<V, T>> topIndividuals) {
            this.topIndividuals = topIndividuals;
        }

        public List<GenerationStatistics> getGenerationStats() {
            return generationStats;
        }

        public void setGenerationStats(List<GenerationStatistics> generationStats) {
            this.generationStats = generationStats;
        }
    }
}
