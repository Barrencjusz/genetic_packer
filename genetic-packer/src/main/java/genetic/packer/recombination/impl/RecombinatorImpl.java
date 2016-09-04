package genetic.packer.recombination.impl;

import genetic.packer.fx.copy.BoxSizeCloner;
import genetic.packer.generation.GeneticContext;
import genetic.packer.generation.dto.Individual;
import genetic.packer.generation.dto.IndividualBuilder;
import genetic.packer.recombination.Recombinator;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
public class RecombinatorImpl implements Recombinator<Individual, GeneticContext> {

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    private Function<Box, Box> boxCloningMapper;

    @Autowired
    private BoxSizeCloner boxSizeCloner; //FIXME interface

    @Autowired
    private Consumer<Box> containerCentralizer;

    private final Supplier<Boolean> recombination = () -> random.get().nextDouble() <= 0.7;

    @Override
    public Map.Entry<Individual, Individual> apply(Map.Entry<Individual, Individual> pair, GeneticContext context) {
        if(recombination.get()) {
            final Individual male = pair.getKey();
            final Individual female = pair.getValue();

            final int exchangePoint = random.get().nextInt(context.getChromosomeSize());

            final List<Box> malesFirstPart = male.getBoxes().subList(0, exchangePoint);
            final List<Box> malesSecondPart = male.getBoxes().subList(exchangePoint, context.getChromosomeSize());

            final List<Box> femalesFirstPart = female.getBoxes().subList(0, exchangePoint);
            final List<Box> femalesSecondPart = female.getBoxes().subList(exchangePoint, context.getChromosomeSize());

            final List<Box> firstChildChromosome = Stream.concat(malesFirstPart.stream(), femalesSecondPart.stream())
                    .map(boxCloningMapper)
                    .collect(Collectors.toList());
            final List<Box> secondChildChromosome = Stream.concat(femalesFirstPart.stream(), malesSecondPart.stream())
                    .map(boxCloningMapper)
                    .collect(Collectors.toList());

            return new AbstractMap.SimpleEntry<>(new IndividualBuilder()
                    .withBoxes(firstChildChromosome)
                    .build(),
                    new IndividualBuilder()
                    .withBoxes(secondChildChromosome)
                    .build()
            );
        }
        return pair;
    }
}
