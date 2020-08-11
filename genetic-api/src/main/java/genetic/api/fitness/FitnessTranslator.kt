package genetic.api.fitness

import java.util.function.Function

interface FitnessTranslator<T1, T2> : Function<T1, T2> 