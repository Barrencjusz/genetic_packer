package genetic.recombinators;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.Individual;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividualBuilder;
import javafx.scene.shape.Box;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class SingleChildRecombinator extends BaseRecombinator<Box, SimpleIndividual<Double, Box>> {

    @Override
    protected SimpleIndividual<Double, Box> recombine(List<Cell<Box>> male, List<Cell<Box>> female) {

        final int exchangePoint = random.get().nextInt(male.size());

        final List<Cell<Box>> malesFirstPart = male.subList(0, exchangePoint);
        final List<Cell<Box>> femalesSecondPart = female.subList(exchangePoint, male.size());

        final List<Cell<Box>> firstChildChromosome = Stream.concat(malesFirstPart.stream(), femalesSecondPart.stream())
                .map(cellCloningMapper)
                .collect(Collectors.toList());
        return new SimpleIndividualBuilder<Double, Box>() // fixme, maybe just return a new chromosome
                .cells(firstChildChromosome)
                .build();
    }

    @Override
    protected SimpleIndividual<Double, Box> resolveDefault(Pair<Individual<Box>, Individual<Box>> pair) {
        return random.get().nextBoolean() ? pair.getLeft() : pair.getRight();
    }
}
