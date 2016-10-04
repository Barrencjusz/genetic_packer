package genetic.packer.normalisation;

import java.util.function.BiFunction;

import genetic.packer.generation.dto.Individual;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class Normalizer implements BiFunction<Individual, Bounds, Individual> {

    @Override
    public Individual apply(Individual individual, Bounds bounds) {
        //fixme generate numbers at the beginning
        final int[] order = {0}; //fixme
        individual.getCells().stream()
            .map(box -> new OrderedBox(order[0]++, box));
            //.so
        return null;
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
