package genetic.presenter.javafx.view.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import javafx.scene.paint.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorConfig {

    @Bean
    public Map<String, Color> namedColors() throws ClassNotFoundException {
        final Field[] fields = Class.forName("javafx.scene.paint.Color").getFields();
        return Arrays.stream(fields)
                .filter(field -> field.getDeclaringClass().equals(Color.class))
                .collect(Collectors.toMap(Field::getName, ColorConfig::getColor));
    }

    @Bean
    public List<Color> colors() throws ClassNotFoundException {
        return namedColors().entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Bean
    @Resource
    public Integer colorsSize() throws ClassNotFoundException {
        return colors().size();
    }

    private static Color getColor(Field field) {
        try {
            return (Color) field.get(null);
        } catch (IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }
}
