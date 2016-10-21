package genetic.packer.evolution.generation.dto.individual;

import java.util.List;

import genetic.packer.evolution.generation.dto.Cell;

/**
 * @author piotr.larysz
 */
public interface Individual<T> {

    List<Cell<T>> getCells();
}
