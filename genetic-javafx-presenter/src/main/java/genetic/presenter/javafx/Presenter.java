package genetic.presenter.javafx;

import genetic.packer.dto.response.ResponseDto;
import genetic.presenter.rest.AsyncFunction;
import genetic.presenter.rest.UnsafeAsyncExecutor;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author piotr.larysz
 */
@Component
public class Presenter implements Consumer<Stage> {

    @Autowired
    private AsyncFunction<Object, ResponseDto> geneticClient;

    @Autowired
    private UnsafeAsyncExecutor asyncExecutor;

    @Autowired
    private Consumer<Stage> baseViewCreator;

    @Autowired
    private Consumer<ResponseDto> geneticViewCreator;

    @Override
    public void accept(Stage stage) {
        final Future<ResponseDto> futureResponse = geneticClient.apply(new Object());
        baseViewCreator.accept(stage);
        final ResponseDto responseDto = asyncExecutor.apply(futureResponse);
        geneticViewCreator.accept(responseDto);
    }
}
