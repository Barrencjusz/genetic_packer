package genetic.selectors;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class ThreadLocalRandomSupplier implements Supplier<ThreadLocalRandom> {

    @Override
    public ThreadLocalRandom get() {
        return ThreadLocalRandom.current();
    }
}
