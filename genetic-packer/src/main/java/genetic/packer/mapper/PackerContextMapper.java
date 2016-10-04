package genetic.packer.mapper;

import genetic.packer.Packer;
import genetic.packer.generation.GeneticContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class PackerContextMapper implements Function<Packer.Context, GeneticContext> {

    @Override
    @Mapping(target = "chromosomeSize", expression = "java(context.getEmbryo().getCells().size())")
    public abstract GeneticContext apply(Packer.Context context);
}
