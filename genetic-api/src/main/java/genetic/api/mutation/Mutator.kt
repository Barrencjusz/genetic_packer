package genetic.api.mutation

import genetic.api.individual.Organism

interface Mutator<T, P> : (Organism<P>, T) -> Unit