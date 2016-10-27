package genetic.packer;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.api.builder.HasBuilder;
import genetic.api.individual.Individual;
import genetic.api.individual.impl.DetailedIndividual;
import genetic.packer.evolution.generation.GeneticContext;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import genetic.packer.misc.Cast;
import genetic.packer.statistics.GenerationStatisticsCreator;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;
import javaslang.collection.Stream;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class Packer implements Function<Packer.Context, Packer.Result<Double, Box>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Packer.class);

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private Function<Context, GeneticContext> packerContextMapper;

    @Autowired
    private BiFunction<Seq<Generation<Double, Box>>, Integer, Seq<DetailedIndividual<Double, Box>>> bestIndividualsSelector;

    @Autowired
    private GenerationStatisticsCreator generationStatisticsCreator;

    @Override
    public Result<Double, Box> apply(Context context) {
        Supplier<Generation<Double, Box>> generationCreator = Cast.checked(beanFactory.getBean("statefulGenerationCreator", packerContextMapper.apply(context)));
        final Seq<Generation<Double, Box>> generations = Stream
            .continually(generationCreator)
            .take(context.getNumberOfGenerations());
        return new PackerResultBuilder<Double, Box>()
            .generations(generations)
            .topIndividuals(bestIndividualsSelector.apply(generations, context.getNumberOfTopIndividuals()))
            .generationStats(generationStatisticsCreator.create(generations))
            .build();
    }

    @GeneratePojoBuilder(withName = "Packer*Builder", withSetterNamePattern = "*")
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

    @GeneratePojoBuilder(withName = "Packer*Builder", withSetterNamePattern = "*")
    public static class Result<V extends Comparable<V>, T> {

        private Seq<Generation<V, T>> generations;

        private Seq<DetailedIndividual<V, T>> topIndividuals;

        private Seq<GenerationStatistics> generationStats;

        public Seq<Generation<V, T>> getGenerations() {
            return generations;
        }

        public void setGenerations(Seq<Generation<V, T>> generations) {
            this.generations = generations;
        }

        public Seq<DetailedIndividual<V, T>> getTopIndividuals() {
            return topIndividuals;
        }

        public void setTopIndividuals(Seq<DetailedIndividual<V, T>> topIndividuals) {
            this.topIndividuals = topIndividuals;
        }

        public Seq<GenerationStatistics> getGenerationStats() {
            return generationStats;
        }

        public void setGenerationStats(Seq<GenerationStatistics> generationStats) {
            this.generationStats = generationStats;
        }
    }
}
