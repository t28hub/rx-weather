package com.t28.rxweather.api.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.t28.rxweather.data.model.City;
import com.t28.rxweather.data.model.Forecast;
import com.t28.rxweather.data.model.Weather;

import java.io.IOException;
import java.util.List;

public class ForecastParser extends JacksonParser<Forecast> {

    @Override
    public Forecast parse(byte[] data) throws ParseException {
        final ResponseHolder holder;
        try {
            holder = getMapper().readValue(data, ResponseHolder.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }

        final City city = new City.Builder()
                .setId(holder.city.id)
                .setName(holder.city.name)
                .setCountryCode(holder.city.country)
                .build();

        final Forecast.Builder builder = new Forecast.Builder();
        builder.setCity(city);

        for (WeatherHolder weatherHolder : holder.list) {
            builder.addWeathers(new Weather.Builder()
                    .setTemperature(weatherHolder.main.temp)
                    .setMinTemperature(weatherHolder.main.minTemp)
                    .setMaxTemperature(weatherHolder.main.maxTemp)
                    .setHumidity(weatherHolder.main.humidity)
                    .setPressure(weatherHolder.main.pressure)
                    .build());
        }

        return builder.build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ResponseHolder {
        public CityHolder city;
        public List<WeatherHolder> list;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CityHolder {
        public int id;
        public String name;
        public String country;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WeatherHolder {
        public MainHolder main;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MainHolder {
        public int humidity;
        public float pressure;
        public float temp;
        @JsonProperty("temp_min")
        public float minTemp;
        @JsonProperty("temp_max")
        public float maxTemp;
    }
}
