package genetic.packer.fx.calculation.impl;

import java.util.function.BiPredicate;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

@Component
public class NonTangentialIntersectionResolver implements BiPredicate<Box, Box> {

    @Override
    public boolean test(Box firstBox, Box secondBox) {
        return this.intersects(firstBox.getBoundsInParent(), secondBox.getBoundsInParent());
    }

    private boolean intersects(Bounds firstBounds, Bounds secondBounds) {
        if (firstBounds == null || firstBounds.isEmpty() || secondBounds == null || secondBounds.isEmpty()) {
            return false;
        }
        return firstBounds.getMaxX() > secondBounds.getMinX()
                && firstBounds.getMaxY() > secondBounds.getMinY()
                && firstBounds.getMaxZ() > secondBounds.getMinZ();
    }
}
