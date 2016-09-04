package genetic.presenter.config;

import com.google.gson.Gson;
import genetic.packer.dto.request.RequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author piotr.larysz
 */
@Configuration
public class GeneticRequestConfig {

    @Bean
    public RequestDto geneticRequest(@Value("classpath:geneticInput.json") Resource geneticInput) throws IOException {
        try (final Reader reader = new InputStreamReader(geneticInput.getInputStream())) {
            return new Gson().fromJson(reader, RequestDto.class);
        }
    }
}
