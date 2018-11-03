package vla9isla8.weatherspringmvc.weather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weather.providers.api")
@Data
public class ApiConfig {
    private String openWeatherApiKey;
    private String openWeatherApiPath;
    private String apixuForecastPath;
    private String apixuApiKey;
}
