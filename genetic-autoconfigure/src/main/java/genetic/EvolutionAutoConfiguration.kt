package genetic

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.Organism
import genetic.api.individual.impl.DetailedIndividual
import genetic.api.mutation.Mutator
import genetic.evaluator.EvaluatorImpl
import genetic.evaluator.elitism.ElitistPicker
import genetic.evaluator.elitism.impl.ElitistPickerImpl
import genetic.evaluator.elitism.impl.PromoterImpl
import genetic.generations.creator.FirstGenerationCreator
import genetic.generations.creator.GenerationCreator
import genetic.generations.creator.OngoingGenerationCreator
import genetic.recombinators.Recombinator
import genetic.selectors.PairSelector
import genetic.selectors.roulette.impl.RouletteDistancerImpl
import genetic.selectors.roulette.impl.RoulettePairSelector
import genetic.statistics.GenerationStatisticsCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Supplier

@Configuration
abstract class EvolutionAutoConfiguration<T, P> {

  @Bean
  fun evolution(
      generationsFactory: (GenerationContext<T>) -> () -> Generation<P>,
      contextMapper: (Evolution.Context<T>) -> GenerationContext<T>,
      bestIndividualsSelector: (Sequence<Generation<P>>, Int) -> Sequence<DetailedIndividual<P>>,
      generationStatisticsCreator: GenerationStatisticsCreator
  ) = Evolution(
      generationsFactory = generationsFactory,
      contextMapper = contextMapper,
      bestIndividualsSelector = bestIndividualsSelector,
      generationStatisticsCreator = generationStatisticsCreator
  )

  @Bean
  fun generationsFactory(
      firstGenerationCreator: (T, Int) -> Sequence<Individual<P>>,
      ongoingGenerationCreator: (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>>,
      idGenerator: Supplier<Int>,
      evaluator: EvaluatorImpl
  ): (GenerationContext<T>) -> () -> Generation<P> = {
    object : () -> Generation<P> {

      val generationCreator = GenerationCreator(
          context = it,
          firstGenerationCreator = firstGenerationCreator,
          ongoingGenerationCreator = ongoingGenerationCreator,
          idGenerator = idGenerator,
          evaluator = evaluator
      )

      override fun invoke() = generationCreator()
    }
  }

  @Bean
  fun firstGenerationCreator(
      individualCreator: IndividualCreator<T, P>
  ) = FirstGenerationCreator(individualCreator = individualCreator)

  @Bean
  fun ongoingGenerationCreator(
      elitistPicker: ElitistPicker,
      pairSelector: PairSelector,
      singleChildRecombinator: Recombinator<Organism<P>, Individual<P>>,
      twoChildrenRecombinator: Recombinator<Organism<P>, Individual<P>>,
      mutator: Mutator<T, P>
  ) = OngoingGenerationCreator(
      elitistPicker = elitistPicker,
      pairSelector = pairSelector,
      singleChildRecombinator = singleChildRecombinator,
      twoChildrenRecombinator = twoChildrenRecombinator,
      mutator = mutator
  )

  @Bean
  fun elitistPicker(promoter: PromoterImpl) = ElitistPickerImpl(promoter)

  @Bean
  fun promoter() = PromoterImpl()

  @Bean
  fun pairSelector() = RoulettePairSelector()

  @Bean
  fun rouletteDistancer() = RouletteDistancerImpl()
}