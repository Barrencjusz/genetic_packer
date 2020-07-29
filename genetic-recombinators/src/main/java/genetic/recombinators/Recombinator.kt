package genetic.recombinators

interface Recombinator<T1, T2> : (Pair<T1, T1>) -> Sequence<T2>