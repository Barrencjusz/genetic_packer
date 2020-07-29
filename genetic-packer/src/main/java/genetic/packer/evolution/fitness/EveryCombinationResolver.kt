package genetic.packer.evolution.fitness

import java.util.AbstractMap
import java.util.ArrayList

class EveryCombinationResolver {

  operator fun <T> invoke(objects: List<T>): Collection<Pair<T, T>> {
    val combinations = ArrayList<Pair<T, T>>()
    (0 until objects.size - 1).forEach { outer ->
      (outer + 1 until objects.size).forEach { inner ->
        combinations.add(Pair(objects[outer], objects[inner]))
      }
    }
    return combinations
  }

  //  fun <T> apply(objects: Sequence<T>): Sequence<Pair<T, T>> {
  //    return objects.toList().combinations(2)
  //        .map({ combination -> Pair(combination.head(), combination.last()) })
  //  }
}