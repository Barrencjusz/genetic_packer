package genetic.packer.mapper

import genetic.Evolution
import genetic.api.generation.GenerationContext
import genetic.packer.evolution.generation.dto.Embryo

class PackerContextMapper : (Evolution.Context<Embryo>) -> GenerationContext<Embryo> {

  override fun invoke(context: Evolution.Context<Embryo>) = GenerationContext(
      embryo = context.embryo,
      chromosomeSize = context.embryo.boxes.count(),
      numberOfEliteIndividuals = context.numberOfEliteIndividuals,
      generationSize = context.generationSize
  )
}