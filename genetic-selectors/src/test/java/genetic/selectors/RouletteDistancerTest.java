package genetic.selectors;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author piotr.larysz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
public class RouletteDistancerTest {
//fixme :(
//    @Autowired
//    private RouletteDistancer rouletteDistancer;
//
//    private Collection<RatedIndividual<Double, String>> ratedIndividuals;
//
//    private Set<Double> testValues = Sets.newHashSet(1.0, 3.0, 6.0, 10.0);
//
//    @Before
//    public void before() {
//        ratedIndividuals = ImmutableList.<RatedIndividual<Double, String>> builder()
//                .add(new RatedIndividualBuilder<Double, String>().withFitness(1.0).withIndividual("a").build())
//                .add(new RatedIndividualBuilder<Double, String>().withFitness(2.0).withIndividual("b").build())
//                .add(new RatedIndividualBuilder<Double, String>().withFitness(3.0).withIndividual("c").build())
//                .add(new RatedIndividualBuilder<Double, String>().withFitness(4.0).withIndividual("d").build())
//                .build();
//    }
//
//    @Test
//    public void test() {
//        TreeMap<Double, RatedIndividual<Double, String>> treeMap = this.rouletteDistancer.distance(ratedIndividuals);
//        boolean allMatch = treeMap.entrySet().stream().allMatch(entry -> {
//            return testValues.stream().anyMatch(testValue -> DoubleMath.fuzzyEquals(entry.getKey(), testValue, 0.1));
//        });
//        assertTrue(allMatch);
//    }
}