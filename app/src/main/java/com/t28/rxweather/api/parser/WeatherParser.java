package com.t28.rxweather.api.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.t28.rxweather.data.model.City;
import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.model.Weather;

import java.io.IOException;
import java.util.List;

public class WeatherParser extends JacksonParser<Weather> {

    @Override
    public Weather parse(byte[] data) throws ParseException {
        final WeatherHolder holder;
        try {
            holder = getMapper().readValue(data, WeatherHolder.class);
        } catch (IOException e) {
            throw new ParseException(e);
        }

        final City city = new City.Builder()
                .setId(holder.id)
                .setName(holder.name)
                .setCountryCode(holder.sys.country)
                .setCoordinate(holder.coord)
                .build();

        return new Weather.Builder()
                .setCity(city)
                .setTemperature(holder.main.temp)
                .setMinTemperature(holder.main.minTemp)
                .setMaxTemperature(holder.main.maxTemp)
                .setHumidity(holder.main.humidity)
                .setPressure(holder.main.pressure)
                .setUpdateTime(holder.dt)
                .setSunriseTime(holder.sys.sunrise)
                .setSunsetTime(holder.sys.sunset)
                .build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WeatherHolder {
        public int id;
        public long dt;
        public String name;
        public Coordinate coord;
        public MainHolder main;
        public SysHolder sys;
        @JsonProperty("weather")
        public List<ConditionHolder> conditions;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SysHolder {
        public String country;
        public long sunrise;
        public long sunset;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ConditionHolder {
        public int id;
        public String main;
        public String description;
    }
}
