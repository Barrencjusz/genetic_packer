package genetic.packer.evolution.fitness;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javafx.scene.shape.Box;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class IntersectionFitnessTester implements Consumer<Pair<BoxScoreProperties, BoxScoreProperties>> {

    @Autowired
    private BiPredicate<Box, Box> nonTangentialIntersectionResolver;

    @Override
    public void accept(Pair<BoxScoreProperties, BoxScoreProperties> boxScorePropertiesPair) {
        final BoxScoreProperties left = boxScorePropertiesPair.getLeft();
        final BoxScoreProperties right = boxScorePropertiesPair.getRight();

        if(this.nandIntersects(left, right) && nonTangentialIntersectionResolver.test(left.getBox(), right.getBox())) {
            left.setIntersects(true);
            right.setIntersects(true);
        }
    }

    private boolean nandIntersects(BoxScoreProperties left, BoxScoreProperties right) {
        return BooleanUtils.negate(left.isIntersects() && right.isIntersects());
    }
}
