package genetic.web.controller

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@SpringBootApplication
class PackerController {

  @GetMapping("fluxBoxes", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
  fun fluxBoxes(/*@RequestBody packerRequest: PackerRequest*/) =
      Flux.generate<PackerResponse, Int>({ 1 }, { state, sink ->
        sink.next(
            PackerResponse(
                boxes = sequenceOf(
                    PositionedBox(width = 80, depth = 120, x = 0, y = 0),
                    PositionedBox(width = 80, depth = 120, x = 80, y = 0),
                    PositionedBox(width = 80, depth = 120, x = 0, y = 120),
                    PositionedBox(width = 80, depth = 120, x = 80, y = 120),
                    PositionedBox(width = 80, depth = 120, x = 160, y = 0),
                    PositionedBox(width = 80, depth = 120, x = 160, y = 120)
                )
            )
        )
        1
      })
          .take(1)
}

data class PackerRequest(
    val container: Container,
    val boxes: Sequence<Box>
)

data class PackerResponse(
    val boxes: Sequence<PositionedBox>
)

interface Size {
  val width: Int
  val depth: Int
}

interface Position {
  val x: Int
  val y: Int
}

interface Rotated {
  val rotated: Boolean
}

data class Container(
    override val width: Int,
    override val depth: Int
) : Size

data class Box(
    override val width: Int,
    override val depth: Int
) : Size

data class PositionedBox(
    override val width: Int,
    override val depth: Int,
    override val x: Int,
    override val y: Int,
    override val rotated: Boolean = false
) : Size, Position, Rotated

fun main(args: Array<String>) {
  runApplication<PackerController>(*args)
}