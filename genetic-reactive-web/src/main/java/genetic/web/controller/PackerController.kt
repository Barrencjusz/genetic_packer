package genetic.web.controller

import genetic.Evolution
import genetic.api.individual.impl.RatedIndividual
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.piteron.genetic.packer.Body
import pl.piteron.genetic.packer.Box
import pl.piteron.genetic.packer.Embryo
import pl.piteron.genetic.packer.PackerConfiguration
import reactor.core.publisher.Flux
import kotlin.math.sqrt
import kotlin.streams.asStream

@RestController
@SpringBootApplication
@Import(PackerConfiguration::class)
class PackerController(
    private val evolution: Evolution<Body, Embryo>
) {

  @GetMapping("fluxBoxes", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
  fun fluxBoxes(/*@RequestBody packerRequest: PackerRequest*/): Flux<RatedIndividual<Body>> {
    val boxes = listOf(
        Box(1, 4, 6),
        Box(2, 4, 6),
        Box(3, 4, 6),
        Box(4, 4, 6),
        Box(5, 4, 6),
        Box(6, 4, 6),
        Box(7, 4, 6),
        Box(8, 4, 6),
        Box(9, 4, 6),
        Box(10, 4, 6),
        Box(11, 1, 30),
        Box(12, 4, 6),
        Box(13, 4, 6),
        Box(14, 4, 6),
        Box(15, 4, 6),
        Box(16, 2, 11),
        Box(17, 2, 11)
    )
    val generationSize = (sqrt(boxes.size.toDouble()) * 100).toInt()
    val generations = evolution(
        Evolution.Context(
            numberOfGenerations = 1000,
            generationSize = generationSize,
            embryo = Embryo(
                containerWidth = 16, //12, //FIXME 11 kills it
                containerDepth = 100,
                boxes = boxes
            ),
            numberOfTopIndividuals = 2,
            numberOfEliteIndividuals = generationSize / 10
        )
    )
    return Flux.fromStream(generations.asStream())
    //    return Flux.generate<PackerResponse, Int>({ 1 }, { state, sink ->
    //      sink.next(
    //          PackerResponse(
    //              boxes = sequenceOf(
    //                  PositionedBox(width = 80, depth = 120, x = 0, y = 0),
    //                  PositionedBox(width = 80, depth = 120, x = 80, y = 0),
    //                  PositionedBox(width = 80, depth = 120, x = 0, y = 120),
    //                  PositionedBox(width = 80, depth = 120, x = 80, y = 120),
    //                  PositionedBox(width = 80, depth = 120, x = 160, y = 0),
    //                  PositionedBox(width = 80, depth = 120, x = 160, y = 120)
    //              )
    //          )
    //      )
    //      1
    //    })
    //        .take(1)
  }
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

//data class Box(
//    override val width: Int,
//    override val depth: Int
//) : Size

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