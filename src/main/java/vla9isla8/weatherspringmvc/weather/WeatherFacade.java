package vla9isla8.weatherspringmvc.weather;

import static java.util.stream.Collectors.toMap;

import org.springframework.stereotype.Service;
import vla9isla8.weatherspringmvc.weather.providers.WeatherProviderFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeatherFacade implements AutoCloseable {

    private final WeatherProviderFactory providerFactory;

    private final ExecutorService executorService = Executors.newFixedThreadPool(PROVIDERS.length);

    private static final String[] PROVIDERS = {"OpenMap","Apixu"};

    public WeatherFacade(WeatherProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    public List<String> getProviders() {
        return Arrays.asList(PROVIDERS);
    }

    public Map<LocalDate,Map<String,WeatherData>> getWeather(String city, short days) {
        Map<String, Future<Map<LocalDate, WeatherData>>> map = Arrays.stream(PROVIDERS).collect(toMap(
            Function.identity(),
            provider -> executorService
                        .submit(() -> providerFactory.getProvider(provider).getForecast(city, days)))
        );
        List<LocalDate> dates = map.values().stream().map(mapFuture -> {
            try {
                return mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }).filter(Objects::nonNull)
            .map(Map::keySet)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
        return dates.stream().collect(Collectors.toMap(
            Function.identity(),
            date -> {
                HashMap<String, WeatherData> values = new LinkedHashMap<>();
                Arrays.stream(PROVIDERS).forEach(provider ->
                    {
                        Map<LocalDate, WeatherData> weatherDataMap;
                        try {
                            weatherDataMap = map.get(provider).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        } catch (ExecutionException e) {
                            System.err.println(e.getCause().getMessage());
                            return;
                        }
                        values.put(provider, weatherDataMap.get(date));
                    }
                );
                return values;
            },
            (o, o2) -> o,
            TreeMap::new));
    }

    public Map<LocalDate,Map<String,WeatherData>> getWeather(String city) {
        return getWeather(city,(short)7);
    }

    @Override
    public synchronized void close() throws Exception {
        executorService.shutdown();
        wait(200);
        executorService.shutdownNow();
    }

}
