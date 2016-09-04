package genetic.packer.fx.copy;

import javafx.scene.shape.Box;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author piotr.larysz
 */
@Mapper
public interface BoxCopier {

    Box clone(Box box);
}
