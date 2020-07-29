package genetic.packer.misc;

import java.util.function.Supplier;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class IdGenerator implements Supplier<Integer> {

    private int id = 1;

    @Override
    public Integer get() {
        return id++;
    }
}
