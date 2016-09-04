package genetic.packer.fitness;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author piotr.larysz
 */
@Component
public class IntersectionFitnessTester implements Consumer<Map.Entry<BoxScoreProperties, BoxScoreProperties>> {

    @Override
    public void accept(Map.Entry<BoxScoreProperties, BoxScoreProperties> boxScorePropertiesPair) {
        final BoxScoreProperties left = boxScorePropertiesPair.getKey();
        final BoxScoreProperties right = boxScorePropertiesPair.getValue();

        final boolean intersects = left.getBox().getBoundsInParent().intersects(right.getBox().getBoundsInParent());

        if(intersects) {
            left.setIntersects(intersects);
            right.setIntersects(intersects);
        }
    }
}
