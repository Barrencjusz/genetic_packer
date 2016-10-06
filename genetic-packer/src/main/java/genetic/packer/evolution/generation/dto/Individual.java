package genetic.packer.evolution.generation.dto;

import genetic.packer.fx.Cell;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class Individual<T> {

    private List<Cell<T>> cells;

    public List<Cell<T>> getCells() {
        return cells;
    }

    public void setCells(List<Cell<T>> cells) {
        this.cells = cells;
    }
}
