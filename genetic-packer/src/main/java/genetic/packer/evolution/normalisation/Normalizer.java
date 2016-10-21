package genetic.packer.evolution.normalisation;

import java.util.function.BiFunction;

import genetic.packer.evolution.generation.dto.Cell;
import genetic.packer.evolution.generation.dto.individual.Individual;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class Normalizer implements BiFunction<Individual<Box>, Bounds, Individual<Box>> {

    @Override
    public Individual<Box> apply(Individual<Box> individual, Bounds bounds) {
        //fixme generate numbers at the beginning
        final int[] order = {0}; //fixme
        individual.getCells().stream()
            .map(Cell::getNucleus)
            .map(box -> new OrderedBox(order[0]++, box));
            //.so
        return individual; //todo
    }

    private static class OrderedBox {

        private int order;

        private Box box;

        public OrderedBox(int order, Box box) {
            this.order = order;
            this.box = box;
        }

        public int getOrder() {
            return order;
        }

        public Box getBox() {
            return box;
        }
    }
}
