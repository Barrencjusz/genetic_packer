//package genetic.packer.proof;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.List;
//
//import com.google.common.collect.ImmutableList;
//import org.junit.Test;
//import sun.jvm.hotspot.utilities.AssertionFailure;
//
//public class StreamLimitSortOrderProof {
//
//    private List<Integer> list = ImmutableList.<Integer>builder().add(1).add(2).add(3).add(1000).build();
//
//    @Test
//    public void limitSorted() {
//        final int three = list.stream().limit(3).sorted().max(Integer::compare).orElseThrow(AssertionFailure::new);
//        assertEquals(3, three);
//    }
//
//    @Test
//    public void sortedLimit() {
//        final int three = list.stream().sorted().limit(3).max(Integer::compare).orElseThrow(AssertionFailure::new);
//        assertEquals(3, three);
//    }
//
//    @Test
//    public void oppositeSortLimit() {
//        final int thousand = list.stream().sorted((o1, o2) -> Integer.compare(o2, o1)).limit(3).max(Integer::compare).orElseThrow(AssertionFailure::new);
//        assertEquals(1000, thousand);
//    }
//
//    @Test
//    public void limitOppositeSort() {
//        final int three = list.stream().limit(3).sorted((o1, o2) -> Integer.compare(o2, o1)).max(Integer::compare).orElseThrow(AssertionFailure::new);
//        assertEquals(3, three);
//    }
//}
