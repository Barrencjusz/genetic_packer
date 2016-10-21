package genetic.packer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "genetic")
public class GeneticPackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneticPackerApplication.class, args);
	}
}
