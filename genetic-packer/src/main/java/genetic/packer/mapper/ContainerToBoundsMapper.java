package genetic.packer.mapper;

import java.util.function.Function;

import genetic.packer.dto.request.ContainerDto;
import javafx.geometry.Bounds;
import javafx.geometry.ContainerBoundsBuilder;
import org.mapstruct.Mapper;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class ContainerToBoundsMapper implements Function<ContainerDto, Bounds> {

    @Override
    public Bounds apply(ContainerDto containerDto) {
        return new ContainerBoundsBuilder()
            .width(containerDto.getWidth())
            .height(containerDto.getHeight())
            .depth(containerDto.getDepth())
            .build();
    }
}
