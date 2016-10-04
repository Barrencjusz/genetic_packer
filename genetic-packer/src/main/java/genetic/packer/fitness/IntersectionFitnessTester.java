package genetic.packer.fitness;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class IntersectionFitnessTester implements Consumer<Map.Entry<BoxScoreProperties, BoxScoreProperties>> {

    @Autowired
    private BiPredicate<Box, Box> nonTangentialIntersectionResolver;

    @Override
    public void accept(Map.Entry<BoxScoreProperties, BoxScoreProperties> boxScorePropertiesPair) {
        final BoxScoreProperties left = boxScorePropertiesPair.getKey();
        final BoxScoreProperties right = boxScorePropertiesPair.getValue();

        if(nonTangentialIntersectionResolver.test(left.getBox(), right.getBox())) {
            left.setIntersects(true);
            right.setIntersects(true);
        }
    }
}
