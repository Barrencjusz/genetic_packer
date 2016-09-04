package genetic.packer;

import genetic.packer.config.TranslationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "genetic")
public class GeneticPackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneticPackerApplication.class, args);
	}
}
