package genetic.presenter.rest;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
public interface AsyncFunction<I, O> {

    @Async
    Future<O> apply(I in);
}
