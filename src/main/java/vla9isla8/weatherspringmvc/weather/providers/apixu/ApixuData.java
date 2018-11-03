package vla9isla8.weatherspringmvc.weather.providers.apixu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApixuData {

    private Error error;
    private Location location;
    private Forecast forecast;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Location {
        private String name;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Forecast {

        private List<Data> forecastday;

        @lombok.Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Data {
            @JsonDeserialize(using = EpochDateJsonDeserializer.class)
            @JsonProperty("date_epoch")
            private LocalDate date;
            private Temp day;
            @lombok.Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            static class Temp {
                @JsonProperty("maxtemp_c")
                private short maxtempC;
                @JsonProperty("mintemp_c")
                private short mintempC;
            }
        }
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Error {
        private int code;
        private String message;
    }


}
