package genetic.web.controller;

import genetic.packer.dto.request.ParamsDto;
import genetic.packer.dto.request.RequestDto;
import genetic.packer.dto.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@RestController
@RequestMapping("packer")
public class PackerController {

    @Autowired
    private BiFunction<RequestDto, ParamsDto, ResponseDto> requestResponseAdapter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto pack(@Valid @RequestBody RequestDto request, ParamsDto params) {
        return requestResponseAdapter.apply(request, params);
    }
}
