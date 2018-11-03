package vla9isla8.weatherspringmvc.weather.providers.apixu;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EpochDateJsonDeserializerTest {

    @Test
    public void deserialize() throws IOException {
        EpochDateJsonDeserializer deserializer = new EpochDateJsonDeserializer();
        LocalDate date = deserializer.deserialize(1541289600);
        Assert.assertEquals("2018-11-04",date.format(DateTimeFormatter.ISO_DATE));
    }
}