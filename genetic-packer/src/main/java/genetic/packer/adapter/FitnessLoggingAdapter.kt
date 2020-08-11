package genetic.packer.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import genetic.Evolution
import genetic.packer.dto.request.ContainerDto
import genetic.packer.dto.request.EmbryoDto
import genetic.packer.dto.request.RequestDto
import genetic.packer.evolution.generation.dto.Embryo
import javafx.geometry.Bounds
import javafx.scene.shape.Box
import java.io.IOException
import java.util.function.Function

class FitnessLoggingAdapter(
    private val mapper: ObjectMapper,
    private val containerToBoundsMapper: Function<ContainerDto, Bounds>,
    private val evolution: Evolution<Box, Embryo>
) : Runnable {

  override fun run() {
    try {
      val request = mapper.readValue<RequestDto<EmbryoDto>>(javaClass.getResource("/packages.json"))
      val boxes = request.embryo.boxes.map {
        Box(it.width.toDouble(), it.height.toDouble(), it.depth.toDouble())
      }
      val embryo = Embryo(
          bounds = containerToBoundsMapper.apply(request.embryo.container),
          boxes = boxes
      )
      val evolutionContext: Evolution.Context<Embryo> = Evolution.Context(
          numberOfGenerations = request.numberOfGenerations,
          generationSize = request.generationSize,
          numberOfEliteIndividuals = request.numberOfEliteIndividuals,
          embryo = embryo
      )
//      evolution(evolutionContext).topIndividuals.forEach { println(it.fitness.score) }
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
}