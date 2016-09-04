package genetic.selectors;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

import static genetic.selectors.PairSelector.Context.HALF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PairSelectorTest.Config.class)
public class PairSelectorTest {

	@Autowired
	private PairSelector<Double> pairSelector;

	@Autowired
	private Supplier<ThreadLocalRandom> random;

	@Mock
	private ThreadLocalRandom threadLocalRandom;

	private Collection<Map.Entry<Double, String>> ratedIndividuals = ImmutableList.<Map.Entry<Double, String>> builder()
			.add(new AbstractMap.SimpleEntry<>(1.0, "ala"))
			.add(new AbstractMap.SimpleEntry<>(2.0, "ma"))
			.add(new AbstractMap.SimpleEntry<>(2.0, "super"))
			.add(new AbstractMap.SimpleEntry<>(3.0, "kota"))
			.build();

	private PairSelector.Context<Double, String> context = new PairSelector.Context(ratedIndividuals, HALF);

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		when(random.get()).thenReturn(threadLocalRandom);
		when(threadLocalRandom.nextDouble(any())).thenReturn(0.9, 1.1, 3.5, 8.0);
	}

	@Test
	public void test() {
		Collection<Map.Entry<String, String>> selectedPairs = pairSelector.select(context);
		assertEquals(2, selectedPairs.size());
		assertTrue(selectedPairs.contains(new AbstractMap.SimpleEntry<>("ala", "ma")));
		assertTrue(selectedPairs.contains(new AbstractMap.SimpleEntry<>("super", "kota")));
	}

	@Configuration
	@ComponentScan("genetic.selectors.roulette.impl")
	public static class Config {

		@Bean
		public Supplier<ThreadLocalRandom> random() {
			return mock(Supplier.class);
		}

	}
}
