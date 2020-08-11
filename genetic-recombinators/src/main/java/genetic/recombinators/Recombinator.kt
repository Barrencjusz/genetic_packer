package genetic.recombinators

interface Recombinator<T, U, P> : (Pair<T, T>, P) -> Sequence<U>