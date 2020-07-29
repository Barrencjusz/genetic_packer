package genetic.generations.creator

import genetic.api.individual.Individual

class FirstGenerationCreator<P, T>(
    private val individualCreator: (T) -> () -> Individual<P>
) : (T, Int) -> Sequence<Individual<P>> {

  override fun invoke(
      embryo: T,
      size: Int
  ) = individualCreator(embryo).let { generateSequence { it() }.take(size) }
}