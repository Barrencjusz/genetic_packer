package genetic.api.fitness

import genetic.api.individual.Individual

interface FitnessTester<T> : (Individual<T>) -> TranslatedFitness<*>