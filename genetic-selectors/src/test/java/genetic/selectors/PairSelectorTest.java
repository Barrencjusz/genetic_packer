//package genetic.selectors;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//
//import genetic.api.fitness.Fitness;
//import genetic.api.individual.Rated;
//import genetic.selectors.config.Configuration;
//import javaslang.Tuple2;
//import javaslang.collection.List;
//import javaslang.collection.Traversable;
//import org.apache.commons.lang3.NotImplementedException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Configuration.class)
//public class PairSelectorTest {
//
//	@Autowired
//	private PairSelector.Creator<Double> pairSelectorCreator;
//
//	@MockBean
//	private Supplier<ThreadLocalRandom> random;
//
//	@Mock
//	private ThreadLocalRandom threadLocalRandom;
//
//	private Traversable<TestAsset> testAssets = List.of(
//		new TestAssetBuilder().fitness(new TestAsset.TestFitness(1.0)).text("ala").build(),
//		new TestAssetBuilder().fitness(new TestAsset.TestFitness(2.0)).text("ma").build(),
//		new TestAssetBuilder().fitness(new TestAsset.TestFitness(2.0)).text("super").build(),
//		new TestAssetBuilder().fitness(new TestAsset.TestFitness(3.0)).text("kota").build()
//	);
//
//	@Before
//	public void before() {
//		//when(random.get()).thenReturn(threadLocalRandom);
//		when(threadLocalRandom.nextDouble(any())).thenReturn(0.9, 1.1, 8.0, 3.5);
//	}
//
//	@Test
//	public void test() {
//		Traversable<Tuple2<TestAsset, TestAsset>> selectedPairs = pairSelectorCreator.from(testAssets).select(size -> 2);
//		assertEquals(2, selectedPairs.size());
//		assertTrue(selectedPairs.existsUnique(tupleHolding("ala", "ma")));
//		assertTrue(selectedPairs.existsUnique(tupleHolding("kota", "super")));
//	}
//
//	private static Predicate<Tuple2<TestAsset, TestAsset>> tupleHolding(String first, String second) {
//		return tuple -> tuple._1().getText().equals(first) && tuple._2().getText().equals(second);
//	}
//
//	static class TestAsset implements Rated<Double> {
//
//		private Fitness<Double> fitness;
//
//		private String text;
//
//		public TestAsset(Fitness<Double> fitness, String text) {
//			this.fitness = fitness;
//			this.text = text;
//		}
//
//		public String getText() {
//			return text;
//		}
//
//		@Override
//		public Fitness<Double> getFitness() {
//			return fitness;
//		}
//
//		private static class TestFitness implements Fitness<Double> {
//
//			private Double fitness;
//
//			public TestFitness(Double fitness) {
//				this.fitness = fitness;
//			}
//
//			@Override
//			public String explain() {
//				throw new NotImplementedException("test fitness");
//			}
//
//			@Override
//			public Double get() {
//				return fitness;
//			}
//		}
//	}
//}
