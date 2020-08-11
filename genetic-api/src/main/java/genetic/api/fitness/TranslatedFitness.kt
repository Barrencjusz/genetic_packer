package genetic.api.fitness

abstract class TranslatedFitness<T : TranslatedFitness<T>>(
    totalFitnessTranslator: FitnessTranslator<T, Double>,
    fitnessExplainedTranslator: FitnessTranslator<T, String>
) : Fitness {

  override val score: Double by lazy {
    totalFitnessTranslator.apply(self())
  }

  private val explanation: String by lazy {
    fitnessExplainedTranslator.apply(self())
  }

  override fun explain() = explanation

  protected abstract fun self(): T
}