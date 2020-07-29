package genetic.presenter.rest;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;

public interface AsyncFunction<I, O> {

    @Async
    Future<O> apply(I in);
}
