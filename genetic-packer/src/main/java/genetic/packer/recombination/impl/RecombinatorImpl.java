package genetic.packer.recombination.impl;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genetic.packer.generation.GeneticContext;
import genetic.packer.generation.dto.Individual;
import genetic.packer.generation.dto.IndividualBuilder;
import genetic.packer.recombination.Recombinator;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecombinatorImpl implements Recombinator<Individual, GeneticContext> {

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    private Function<Box, Box> boxCloningMapper;

    private final Supplier<Boolean> recombination = () -> random.get().nextDouble() <= 0.7;

    @Override
    public Map.Entry<Individual, Individual> apply(Map.Entry<Individual, Individual> pair, GeneticContext context) {
        if(recombination.get()) {
            final Individual male = pair.getKey();
            final Individual female = pair.getValue();

            final int exchangePoint = random.get().nextInt(context.getChromosomeSize());

            final List<Box> malesFirstPart = male.getCells().subList(0, exchangePoint);
            final List<Box> malesSecondPart = male.getCells().subList(exchangePoint, context.getChromosomeSize());

            final List<Box> femalesFirstPart = female.getCells().subList(0, exchangePoint);
            final List<Box> femalesSecondPart = female.getCells().subList(exchangePoint, context.getChromosomeSize());


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
