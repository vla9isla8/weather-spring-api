package vla9isla8.weatherspringmvc.weather.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import vla9isla8.weatherspringmvc.weather.WeatherData;
import vla9isla8.weatherspringmvc.weather.config.ApiConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class WeatherProvider {

    protected final ApiConfig apiConfig;
    protected final ObjectMapper objectMapper;

    public WeatherProvider(ApiConfig apiConfig, ObjectMapper objectMapper) {
        this.apiConfig = apiConfig;
        this.objectMapper = objectMapper;
    }

    public Map<LocalDate, WeatherData> getForecast(String city, short days) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpRequestBase request = this.getRequest(city, days);
        System.out.println(request);
        HttpResponse response = client.execute(request);
        try (InputStream content = response.getEntity().getContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(content))) {
            String data = reader.lines().collect(Collectors.joining());
            return parser(data);
        }
    }

    protected abstract HttpRequestBase getRequest(String city, short days) throws MalformedURLException;

    protected abstract Map<LocalDate,WeatherData> parser(String body) throws IOException;

}
