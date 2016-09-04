package genetic.presenter.javafx.view;

import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class ColorRandomizer implements Supplier<Color>, Function<Set<Color>, Color> {

    @Autowired
    private Integer colorsSize;

    @Resource
    private List<Color> colors;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Override
    public Color apply(Set<Color> rejectedColors) {
        final List<Color> possibleColors = new ArrayList<>(colors);
        possibleColors.removeAll(rejectedColors);
        return getRandomColor(possibleColors);
    }

    @Override
    public Color get() {
        return getRandomColor(colors);
    }

    private Color getRandomColor(List<Color> colors) {
        return colors.get(random.get().nextInt(colorsSize));
    }
}
