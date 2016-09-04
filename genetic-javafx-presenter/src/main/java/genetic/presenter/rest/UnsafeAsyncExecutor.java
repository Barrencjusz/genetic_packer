package genetic.presenter.rest;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author piotr.larysz
 */
@Component
public class UnsafeAsyncExecutor {

    public <T> T apply(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException exception) {
            throw new RuntimeException(exception);
        }
    }
}
