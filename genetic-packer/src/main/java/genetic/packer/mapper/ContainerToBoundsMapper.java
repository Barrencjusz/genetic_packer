package genetic.packer.mapper;

import genetic.packer.dto.request.ContainerDto;
import javafx.geometry.Bounds;
import javafx.geometry.ContainerBoundsBuilder;
import org.mapstruct.Mapper;

import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class ContainerToBoundsMapper implements Function<ContainerDto, Bounds> {

    @Override
    public Bounds apply(ContainerDto containerDto) {
        return new ContainerBoundsBuilder()
                .withWidth(containerDto.getWidth())
                .withHeight(containerDto.getHeight())
                .withDepth(containerDto.getDepth())
                .build();
    }
}
