package genetic.packer.evolution.fitness;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class EveryCombinationResolver {

    public <T> Collection<Map.Entry<T, T>> apply(List<T> objects) {
        Collection<Map.Entry<T, T>> combinations = new ArrayList<>();
        for(int outer = 0; outer < objects.size() - 1; outer++) {
            for (int inner = outer + 1; inner < objects.size(); inner++) {
                combinations.add(new AbstractMap.SimpleEntry<>(objects.get(outer), objects.get(inner)));
            }
        }
        return combinations;
    }

    public <T> Traversable<Tuple2<T, T>> apply(Seq<T> objects) {
        return objects.combinations(2).map(combination -> Tuple.of(combination.head(), combination.last()));
    }
}
