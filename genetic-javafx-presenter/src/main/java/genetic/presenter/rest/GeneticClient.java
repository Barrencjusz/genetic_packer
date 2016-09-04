package genetic.presenter.rest;

import genetic.packer.dto.BoxDtoBuilder;
import genetic.packer.dto.request.ContainerDtoBuilder;
import genetic.packer.dto.request.EmbryoDtoBuilder;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.request.RequestDtoBuilder;
import genetic.packer.dto.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
public class GeneticClient implements AsyncFunction<Object, ResponseDto> {

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private RequestDto geneticRequest;

    @Override
    public Future<ResponseDto> apply(Object o) {
        final ResponseDto responseDto = restOperations.postForObject("http://localhost:8280/packer", geneticRequest, ResponseDto.class);
        System.out.println(responseDto);
        return new AsyncResult<>(responseDto);
    }
}
