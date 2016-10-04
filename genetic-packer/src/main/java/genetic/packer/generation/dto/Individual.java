package genetic.packer.generation.dto;

import genetic.packer.fx.Cell;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class Individual {

    private List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
