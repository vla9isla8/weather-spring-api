package vla9isla8.weatherspringmvc.weather.providers;

public interface WeatherProviderFactory {
    WeatherProvider getProvider(String name);
}
