package genetic

import genetic.api.fitness.Fitness
import genetic.api.fitness.TranslatedFitness
import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.Organism
import genetic.api.individual.impl.DetailedIndividual
import genetic.api.individual.impl.RatedIndividual
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
import genetic.selectors.roulette.RoulettePairSelectorFactory
import genetic.selectors.roulette.RouletteTreeMapCreator
import genetic.statistics.BestIndividualsSelector
import genetic.statistics.GenerationStatisticsCreator
import genetic.statistics.average.AverageFitnessResolver
import genetic.statistics.best.BestFitnessResolver
import genetic.statistics.impl.GenerationStatisticsCreatorImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ThreadLocalRandom

@Configuration
abstract class EvolutionAutoConfiguration<T, P> {

  @Bean
  fun evolution(
      generationsFactory: (GenerationContext<T>) -> () -> Generation<P>,
      contextMapper: Evolution.ContextMapper<T>,
      bestIndividualsSelector: (Sequence<Generation<P>>) -> Sequence<DetailedIndividual<P>>,
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
      idGenerator: () -> Int,
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
      pairSelectorFactory: PairSelector.Factory<RatedIndividual<P>>,
      singleChildRecombinator: Recombinator<Organism<P>, Individual<P>>,
      twoChildrenRecombinator: Recombinator<Organism<P>, Individual<P>>,
      mutator: Mutator<T, P>
  ) = OngoingGenerationCreator(
      elitistPicker = elitistPicker,
      pairSelectorFactory = pairSelectorFactory,
      singleChildRecombinator = singleChildRecombinator,
      twoChildrenRecombinator = twoChildrenRecombinator,
      mutator = mutator
  )

  @Bean
  fun elitistPicker(promoter: PromoterImpl) = ElitistPickerImpl(promoter)

  @Bean
  fun promoter() = PromoterImpl()

  @Bean
  fun pairSelectorFactory(
      rouletteTreeMapCreator: RouletteTreeMapCreator,
      random: () -> ThreadLocalRandom
  ) = RoulettePairSelectorFactory<RatedIndividual<P>>(
      rouletteTreeMapCreator = rouletteTreeMapCreator,
      random = random
  )

  @Bean
  fun rouletteTreeMapCreator(keyCreatorFactory: () -> RouletteTreeMapCreator.KeyCreator) =
      RouletteTreeMapCreator(keyCreatorFactory)

  @Bean
  fun keyCreatorFactory() = RouletteTreeMapCreator.KeyCreator.Factory()

  @Bean
  fun random(): () -> ThreadLocalRandom = { ThreadLocalRandom.current() }

  @Bean
  fun idGenerator() = object : () -> Int {

    private var value = 1

    override fun invoke(): Int {
      return value++
    }

  }

  @Bean
  fun evaluator(fitnessTester: (Individual<*>) -> TranslatedFitness<*>) = EvaluatorImpl(fitnessTester)

  @Bean
  fun generationStatisticsCreator(
      bestFitnessResolver: (Generation<*>) -> Fitness,
      averageFitnessResolver: (Generation<*>) -> Double
  ) = GenerationStatisticsCreatorImpl(
      bestFitnessResolver = bestFitnessResolver,
      averageFitnessResolver = averageFitnessResolver
  )

  @Bean
  fun bestFitnessResolver() = BestFitnessResolver()

  @Bean
  fun averageFitnessResolver() = AverageFitnessResolver()

  @Bean
  fun bestIndividualsSelector() = BestIndividualsSelector<P>()
}