package vla9isla8.weatherspringmvc.weather.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import vla9isla8.weatherspringmvc.weather.config.ApiConfig;
import vla9isla8.weatherspringmvc.weather.providers.apixu.ApixuProvider;
import vla9isla8.weatherspringmvc.weather.providers.openweather.OpenMapWeatherProvider;

import java.security.InvalidParameterException;

@Service
public class SimpleWeatherProviderFactory implements WeatherProviderFactory {
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;

    public SimpleWeatherProviderFactory(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public WeatherProvider getProvider(String name) {
        switch (name) {
            case "OpenMap":
                return new OpenMapWeatherProvider(apiConfig,objectMapper);
            case "Apixu":
                return new ApixuProvider(apiConfig,objectMapper);
            default:
                throw new InvalidParameterException("Unexpected providers name " + name);
        }
    }
}
