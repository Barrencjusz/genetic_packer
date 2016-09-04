package genetic.packer.mapper;

import javafx.scene.shape.Box;
import org.mapstruct.Mapper;

import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class BoxCloningMapper implements Function<Box, Box> {

    @Override
    public Box apply(Box box) {
        return new Box();
    }
}
