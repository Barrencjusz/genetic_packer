package genetic.packer.evolution.fitness;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import javafx.scene.shape.Box;
import javaslang.Tuple2;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class IntersectionFitnessTester implements Consumer<Tuple2<BoxScoreProperties, BoxScoreProperties>> {

    @Autowired
    private BiPredicate<Box, Box> nonTangentialIntersectionResolver;

    @Override
    public void accept(Tuple2<BoxScoreProperties, BoxScoreProperties> tuple) {
        final BoxScoreProperties left = tuple._1();
        final BoxScoreProperties right = tuple._2();

        if(this.nandIntersects(left, right) && nonTangentialIntersectionResolver.test(left.getBox(), right.getBox())) {
            left.setIntersects(true);
            right.setIntersects(true);
        }
    }

    private boolean nandIntersects(BoxScoreProperties left, BoxScoreProperties right) {
        return BooleanUtils.negate(left.isIntersects() && right.isIntersects());
    }
}
