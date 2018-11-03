package vla9isla8.weatherspringmvc.weather.providers.openweather;

import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import vla9isla8.weatherspringmvc.weather.WeatherData;
import vla9isla8.weatherspringmvc.weather.config.ApiConfig;
import vla9isla8.weatherspringmvc.weather.providers.WeatherProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class OpenMapWeatherProvider extends WeatherProvider {
    public OpenMapWeatherProvider(ApiConfig apiConfig, ObjectMapper objectMapper) {
        super(apiConfig,objectMapper);
    }

    @Override
    protected HttpGet getRequest(String city, short days) {
        String uri = apiConfig.getOpenWeatherApiPath()
            + String.format("?q=%s&cnt=%d&appid=%s", city, days, apiConfig.getOpenWeatherApiKey());
        return new HttpGet(uri);
    }

    @Override
    protected Map<LocalDate, WeatherData> parser(String body) throws IOException {
        OpenWeatherData openWeatherData = objectMapper.readValue(body, OpenWeatherData.class);
        return openWeatherData.getList().stream().collect(toMap(OpenWeatherData.Data::getDt, data -> {
            WeatherData weatherData = new WeatherData();
            weatherData.setDayTemperature(data.getTemp().getDay());
            weatherData.setNightTemperature(data.getTemp().getNight());
            return weatherData;
        }));
    }

}
