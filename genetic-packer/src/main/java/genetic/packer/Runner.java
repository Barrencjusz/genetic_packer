package genetic.packer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private Runnable fitnessLoggingAdapter;

    @Override
    public void run(String... strings) throws Exception {

        this.fitnessLoggingAdapter.run();
    }
}
