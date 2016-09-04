package genetic.packer.misc;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype")
public class IdGenerator implements Supplier<Integer> {

    private int id = 1;

    @Override
    public Integer get() {
        return id++;
    }
}
