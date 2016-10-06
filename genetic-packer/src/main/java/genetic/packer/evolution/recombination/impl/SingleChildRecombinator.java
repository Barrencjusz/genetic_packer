package genetic.packer.evolution.recombination.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genetic.packer.evolution.generation.dto.Individual;
import genetic.packer.evolution.generation.dto.IndividualBuilder;
import genetic.packer.evolution.recombination.Recombinator;
import genetic.packer.fx.Cell;
import javafx.scene.shape.Box;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleChildRecombinator implements Recombinator<Individual<Box>, Individual<Box>> {

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    private Function<Cell<Box>, Cell<Box>> cellCloningMapper;

    private final Supplier<Boolean> recombination = () -> random.get().nextDouble() <= 0.7;

    @Override
    public Individual<Box> apply(Pair<Individual<Box>, Individual<Box>> pair) {
        if(recombination.get()) {

            final Individual<Box> male = pair.getLeft();
            final Individual<Box> female = pair.getRight();

            final int size = male.getCells().size();

            final int exchangePoint = random.get().nextInt(size);

            final List<Cell<Box>> malesFirstPart = male.getCells().subList(0, exchangePoint);
            final List<Cell<Box>> femalesSecondPart = female.getCells().subList(exchangePoint, size);

            final List<Cell<Box>> firstChildChromosome = Stream.concat(malesFirstPart.stream(), femalesSecondPart.stream())
                    .map(cellCloningMapper)
                    .collect(Collectors.toList());
            return new IndividualBuilder<Box>()
                    .withCells(firstChildChromosome)
                    .build();
        }
        return random.get().nextBoolean() ? pair.getLeft() : pair.getRight();
    }
}
