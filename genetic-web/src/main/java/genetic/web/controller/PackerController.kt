package genetic.web.controller

import genetic.packer.dto.request.EmbryoDto
import genetic.packer.dto.request.ParamsDto
import genetic.packer.dto.request.RequestDto
import genetic.packer.dto.response.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("packer")
class PackerController(
    private val requestResponseAdapter: (RequestDto<EmbryoDto>, ParamsDto) -> (ResponseDto)
) {

  @PostMapping
  fun pack(@RequestBody request: @Valid RequestDto<EmbryoDto>, params: ParamsDto): ResponseDto {
    return requestResponseAdapter(request, params)
  }
}