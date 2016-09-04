//package genetic.packer.evolution;
//
//import genetic.packer.generation.dto.Individual;
//import genetic.selectors.dto.RatedIndividual;
//import genetic.packer.recombination.Recombinator;
//import genetic.selectors.PairSelector;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @author piotr.larysz
// */
//@Component
//public class Evolution implements Function<Collection<RatedIndividual>, Collection<Individual>> {
//
//    @Autowired
//    private PairSelector<Double> pairSelector;
//
//    @Autowired
//    private Recombinator<Individual, RecombinatorContext> recombinator;
//
//    @Override
//    public Collection<Individual> apply(Collection<RatedIndividual> ratedIndividuals) {
//        final Collection<Map.Entry<Individual, Individual>> selectedPairs = pairSelector
//                .select(new PairSelector.Context(ratedIndividuals, PairSelector.Context.HALF));
//        return selectedPairs
//                .stream()
//                .map(recombinator::apply)
//                .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//    }
//}
