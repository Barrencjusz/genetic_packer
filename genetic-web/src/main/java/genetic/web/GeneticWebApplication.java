package genetic.web;

import genetic.packer.GeneticPackerApplication;
import genetic.packer.Runner;
import genetic.packer.adapter.FitnessLoggingAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "genetic",
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Runner.class, GeneticPackerApplication.class}))
public class GeneticWebApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(GeneticWebApplication.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
