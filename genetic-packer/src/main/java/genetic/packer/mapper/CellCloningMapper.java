package genetic.packer.mapper;

import java.util.function.Function;

import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.CellBuilder;
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
        return new CellBuilder<Box>().withNucleus(clonedBox)
            .withProcessingOrder(cell.getProcessingOrder())
            .withOrder(cell.getOrder())
            .build();
    }
}
