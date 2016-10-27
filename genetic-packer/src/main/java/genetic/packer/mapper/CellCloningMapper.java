package genetic.packer.mapper;

import java.util.function.Function;

import genetic.api.individual.Cell;
import genetic.api.individual.CellBuilder;
import javafx.scene.shape.Box;
import org.mapstruct.Mapper;

/**
 * @author piotr.larysz
 */
@Mapper
public abstract class CellCloningMapper implements Function<Cell<Box>, Cell<Box>> {

    @Override
    public Cell<Box> apply(Cell<Box> cell) {
        final Box box = cell.getNucleus();
        final Box clonedBox = new Box(box.getWidth(), box.getHeight(), box.getDepth());
        clonedBox.setTranslateX(box.getTranslateX());
        clonedBox.setTranslateY(box.getTranslateY());
        clonedBox.setTranslateZ(box.getTranslateZ());
        return new CellBuilder<Box>().nucleus(clonedBox)
            .processingOrder(cell.getProcessingOrder())
            .order(cell.getOrder())
            .build();
    }
}
