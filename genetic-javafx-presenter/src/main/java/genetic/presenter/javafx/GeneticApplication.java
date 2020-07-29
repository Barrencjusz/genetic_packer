package genetic.presenter.javafx;

import java.util.function.Consumer;

import com.sun.javafx.application.LauncherImpl;
import genetic.packer.GeneticPackerApplication;
import genetic.packer.Runner;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "genetic",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Runner.class, GeneticPackerApplication.class}))
public class GeneticApplication extends Application {

    @Autowired
    private Consumer<Stage> presenter;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(GeneticApplication.class, GeneticPreloader.class, args);
    }

    @Override
    public void init() throws Exception {
        SpringApplication.run(getClass(), new String[] {}).getBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        presenter.accept(primaryStage);
    }
}
