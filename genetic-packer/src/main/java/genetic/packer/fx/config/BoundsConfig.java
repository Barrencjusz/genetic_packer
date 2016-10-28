package genetic.packer.fx.config;

import genetic.packer.fx.specification.BoundsGetter;
import javafx.geometry.Bounds;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author piotr.larysz
 */
@Configuration
public class BoundsConfig {

    @Bean
    public Tuple2<BoundsGetter, BoundsGetter> xBoundsGetters() {
        return Tuple.of(Bounds::getMinX, Bounds::getMaxX);
    }

    @Bean
    public Tuple2<BoundsGetter, BoundsGetter> yBoundsGetters() {
        return Tuple.of(Bounds::getMinY, Bounds::getMaxY);
    }

    @Bean
    public Tuple2<BoundsGetter, BoundsGetter> zBoundsGetters() {
        return Tuple.of(Bounds::getMinZ, Bounds::getMaxZ);
    }

    @Bean
    public Set<Tuple2<BoundsGetter, BoundsGetter>> boundGetters() {
        return HashSet.of(xBoundsGetters(), yBoundsGetters(), zBoundsGetters());
    }

    @Bean
    public BoundsGetter xSizeGetter() {
        return Bounds::getWidth;
    }

    @Bean
    public BoundsGetter ySizeGetter() {
        return Bounds::getHeight;
    }

    @Bean
    public BoundsGetter zSizeGetter() {
        return Bounds::getDepth;
    }
}
