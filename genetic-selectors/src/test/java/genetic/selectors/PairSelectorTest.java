//shame comment
//
// package genetic.selectors;
//
//import static genetic.selectors.PairSelector.Context.HALF;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.util.Collection;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.function.Supplier;
//
//import com.google.common.collect.ImmutableList;
//import genetic.selectors.dto.RatedIndividual;
//import genetic.selectors.dto.RatedIndividualBuilder;
//import org.apache.commons.lang3.tuple.ImmutablePair;
//import org.apache.commons.lang3.tuple.Pair;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = PairSelectorTest.Config.class)
//public class PairSelectorTest {
//
//	@Autowired
//	private PairSelector<Double> pairSelector;
//
//	@Autowired
//	private Supplier<ThreadLocalRandom> random;
//
//	@Mock
//	private ThreadLocalRandom threadLocalRandom;
//
//	private Collection<RatedIndividual<Double, String>> ratedIndividuals = ImmutableList.<RatedIndividual<Double, String>> builder()
//			.add(new RatedIndividualBuilder<Double, String>().withFitness(1.0).withIndividual("ala").build())
//			.add(new RatedIndividualBuilder<Double, String>().withFitness(2.0).withIndividual("ma").build())
//			.add(new RatedIndividualBuilder<Double, String>().withFitness(2.0).withIndividual("super").build())
//			.add(new RatedIndividualBuilder<Double, String>().withFitness(3.0).withIndividual("kota").build())
//			.build();
//
//	private PairSelector.Context<Double, String> context = new PairSelector.Context<>(ratedIndividuals, HALF);
//
//	@Before
//	public void before() {
//		MockitoAnnotations.initMocks(this);
//		when(random.get()).thenReturn(threadLocalRandom);
//		when(threadLocalRandom.nextDouble(any())).thenReturn(0.9, 1.1, 3.5, 8.0);
//	}
//
//	@Test
//	public void test() {
//		Collection<Pair<RatedIndividual<Double, String>, RatedIndividual<Double, String>>> selectedPairs = pairSelector.select(context);
//		assertEquals(2, selectedPairs.size());
//		assertTrue(selectedPairs.contains(new ImmutablePair<>("ala", "ma")));
//		assertTrue(selectedPairs.contains(new ImmutablePair<>("super", "kota")));
//	}
//
//	@Configuration
//	@ComponentScan("genetic.selectors.roulette.impl")
//	public static class Config {
//
//		@Bean
//		public Supplier<ThreadLocalRandom> random() {
//			return mock(Supplier.class);
//		}
//
//	}
//}
