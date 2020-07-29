package genetic.api.individual

interface IndividualCreator<T, P> : (T) -> () -> Individual<P>