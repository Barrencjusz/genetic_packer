package genetic.presenter.rest;

import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import genetic.packer.Packer;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.response.IndividualDto;
import genetic.packer.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * @author piotr.larysz
 */
@Component
public class GeneticClient implements AsyncFunction<Object, ResponseDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Packer.class);

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private RequestDto geneticRequest;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Future<ResponseDto> apply(Object o) {
        final ResponseDto responseDto = restOperations.postForObject("http://localhost:8280/packer", geneticRequest, ResponseDto.class);
        LOGGER.info("Boxes size match: " + responseDto.getTopIndividuals()
            .map(IndividualDto::getTranslatedBoxes)
            .forAll(boxes -> boxes.size() == geneticRequest.getEmbryo().getBoxes().size()) + ":" + geneticRequest.getEmbryo().getBoxes().size());
        LOGGER.info(gson.toJson(responseDto.getTopIndividuals().get(0)));
        return new AsyncResult<>(responseDto);
    }
}
