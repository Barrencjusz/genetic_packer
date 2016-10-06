package genetic.packer.mapper;

import genetic.packer.Packer;
import genetic.packer.evolution.generation.GeneticContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class PackerContextMapper implements Function<Packer.Context, GeneticContext> {

    @Override
    @Mapping(target = "chromosomeSize", expression = "java(context.getEmbryo().getBoxes().size())")
    @Mapping(target = "numberOfEliteIndividuals", defaultValue = "1")
    public abstract GeneticContext apply(Packer.Context context);
}
