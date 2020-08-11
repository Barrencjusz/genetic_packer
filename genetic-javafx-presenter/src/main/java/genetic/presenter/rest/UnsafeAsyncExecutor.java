package genetic.presenter.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

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
