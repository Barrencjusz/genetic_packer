package genetic.packer.misc;

/**
 * @author piotr.larysz
 */
public class Cast {

    @SuppressWarnings("unchecked")
    public static <T, V> T checked(V object) {
        return (T)object;
    }
}
