package genetic.api.elitism

import genetic.api.individual.impl.Elite

interface EliteAcknowledged {

  operator fun <T> invoke(elite: Elite<T>) = elite
}