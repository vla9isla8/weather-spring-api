package vla9isla8.weatherspringmvc.weather.providers.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import vla9isla8.weatherspringmvc.weather.providers.apixu.EpochDateJsonDeserializer;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherData {

    private City city;
    private List<Data> list;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class City {
        private String name;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Data {

        @JsonDeserialize(using = EpochDateJsonDeserializer.class)
        private LocalDate dt;
        private Temp temp;

        @lombok.Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Temp {
            private short day;
            private short night;
        }
    }


}
