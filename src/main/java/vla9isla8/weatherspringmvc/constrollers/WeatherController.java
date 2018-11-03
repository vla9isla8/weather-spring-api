package vla9isla8.weatherspringmvc.constrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vla9isla8.weatherspringmvc.weather.WeatherData;
import vla9isla8.weatherspringmvc.weather.WeatherFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherFacade  weatherFacade;

    @Autowired
    public WeatherController(WeatherFacade weatherFacade) {
        this.weatherFacade = weatherFacade;
    }

    @RequestMapping("forecast")
    public Map<LocalDate, Map<String, WeatherData>> forecast(@RequestParam String city, @RequestParam(required = false) Integer days) {
        if (days == null) {
            days = 7;
        }
        return weatherFacade.getWeather(city,(short)days.intValue());
    }

    @RequestMapping("providers")
    public List<String> providers() {
        return weatherFacade.getProviders();
    }

}
