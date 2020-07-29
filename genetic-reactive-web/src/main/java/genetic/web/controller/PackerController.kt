package genetic.web.controller

import genetic.packer.dto.request.ParamsDto
import genetic.packer.dto.request.RequestDto
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit
import javax.validation.Valid

@RestController
@SpringBootApplication
class PackerController {

  @GetMapping("string")
  fun string() = "abc huehue"

  @GetMapping("monoString")
  fun monoString() = Mono.just("abc huehue")

  @GetMapping("fluxStrings")
  fun fluxStrings() = Flux.just("abc", "huehue")

  @GetMapping("fluxStringsSSE", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
  fun fluxStringsSSE() = Flux.just("abc", "huehue")

  @GetMapping("fluxBoxes", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
  fun fluxBoxes() = Flux.generate<Box, Int>({ 1 }, { state, sink ->
    sink.next(Box(state, state * 2))
    state + 1
  })
      .take(10)
}

data class Box(val width: Int, val depth: Int)

fun main(args: Array<String>) {
  runApplication<PackerController>(*args)
}