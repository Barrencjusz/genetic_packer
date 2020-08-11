package genetic.api.fitness

import genetic.api.individual.Individual

interface FitnessTester<T, U> : (Individual<T>, U) -> TranslatedFitness<*>