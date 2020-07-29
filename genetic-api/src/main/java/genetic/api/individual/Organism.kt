package genetic.api.individual

interface Organism<T> {

  val cells: Sequence<Cell<T>>
}