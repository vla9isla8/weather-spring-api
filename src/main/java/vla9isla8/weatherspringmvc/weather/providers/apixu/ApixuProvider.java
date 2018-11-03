package vla9isla8.weatherspringmvc.weather.providers.apixu;

import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import vla9isla8.weatherspringmvc.weather.WeatherData;
import vla9isla8.weatherspringmvc.weather.config.ApiConfig;
import vla9isla8.weatherspringmvc.weather.providers.WeatherProvider;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class ApixuProvider extends WeatherProvider {
    public ApixuProvider(ApiConfig apiConfig, ObjectMapper objectMapper) {
        super(apiConfig,objectMapper);
    }

    @Override
    protected HttpGet getRequest(String city, short days) {
        String uri = apiConfig.getApixuForecastPath()
            + String.format("?q=%s&days=%d&key=%s", city, days, apiConfig.getApixuApiKey());
        return new HttpGet(uri);
    }

    @Override
    protected Map<LocalDate, WeatherData> parser(String body) throws IOException {
        ApixuData apixuData = objectMapper.readValue(body, ApixuData.class);
        ApixuData.Error error = apixuData.getError();
        if ( error != null ) {
            System.err.println(error.getMessage());
            return Collections.emptyMap();
        }
        return apixuData.getForecast().getForecastday().stream()
            .collect(toMap(ApixuData.Forecast.Data::getDate, forecast -> {
                WeatherData weatherData = new WeatherData();
                ApixuData.Forecast.Data.Temp temp = forecast.getDay();
                weatherData.setDayTemperature(temp.getMaxtempC());
                weatherData.setNightTemperature(temp.getMintempC());
                return weatherData;
            }));
    }

}
