package genetic.packer.evolution.elitism.impl;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.packer.fx.Cell;
import genetic.selectors.dto.RatedIndividual;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
//todo maybe we don't need to clone the individual as it will not ever change?
@Component
public class ElitistCloningPicker implements ElitistPicker<Individual<Box>> {

    @Autowired
    ElitistPicker<Individual<Box>> elitistPicker;

    @Autowired
    private Function<Cell<Box>, Cell<Box>> cellCloningMapper;

    @Override
    public List<Individual<Box>> pick(Collection<RatedIndividual<Double, Individual<Box>>> ratedIndividuals, Integer count) {
        return this.elitistPicker.pick(ratedIndividuals, count); // fixme not cloning for now
    }
}
