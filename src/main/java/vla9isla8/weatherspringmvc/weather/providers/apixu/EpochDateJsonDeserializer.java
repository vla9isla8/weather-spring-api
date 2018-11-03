package vla9isla8.weatherspringmvc.weather.providers.apixu;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class EpochDateJsonDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return deserialize(jsonParser.getIntValue());
    }

    public LocalDate deserialize(int ts) throws IOException, JsonProcessingException {
        return Instant.ofEpochSecond(ts).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
