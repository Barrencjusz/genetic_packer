package genetic.web.adapter

import genetic.Evolution
import genetic.api.individual.impl.DetailedIndividual
import genetic.packer.dto.request.ContainerDto
import genetic.packer.dto.request.EmbryoDto
import genetic.packer.dto.request.ParamsDto
import genetic.packer.dto.request.RequestDto
import genetic.packer.dto.response.IndividualDto
import genetic.packer.dto.response.ResponseDto
import genetic.packer.evolution.generation.dto.Embryo
import genetic.packer.mapper.BoxMapper
import javafx.geometry.Bounds
import javafx.scene.shape.Box
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class RequestResponseAdapter(
    private val evolution: Evolution<Box, Embryo>,
    @Qualifier("containerToBoundsMapperImpl") private val containerToBoundsMapper: (ContainerDto) -> Bounds,
    private val boxMapper: BoxMapper
) : (RequestDto<EmbryoDto>, ParamsDto) -> ResponseDto {

  override fun invoke(requestDto: RequestDto<EmbryoDto>, params: ParamsDto) = Evolution.Context(
      numberOfGenerations = requestDto.numberOfGenerations,
      generationSize = requestDto.generationSize,
      numberOfTopIndividuals = requestDto.numberOfTopIndividuals,
      numberOfEliteIndividuals = requestDto.numberOfEliteIndividuals,
      embryo = Embryo(
          bounds = containerToBoundsMapper(requestDto.embryo.container),
          boxes = requestDto.embryo.boxes.map(boxMapper::map)
      )
  )
      .let(evolution)
      .let {
        ResponseDto(
            topIndividuals = it.topIndividuals.map {
              createTopIndividualDto(it)
            },
            container = requestDto.embryo.container,
            generationStats = it.generationStats
        )
      }

  private fun createTopIndividualDto(detailedIndividual: DetailedIndividual<Box>) = IndividualDto(
      fitness = detailedIndividual.fitness.score,
      translatedBoxes = detailedIndividual.cells.map { it.nucleus }
          .map(boxMapper::map),
      numberOfGeneration = detailedIndividual.numberOfGeneration
  )
}