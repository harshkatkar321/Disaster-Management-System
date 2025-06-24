package com.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private String country;
    private String weatherDescription;
    private double temperature;
    public LocalDateTime getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(LocalDateTime fetchTime) {
		this.fetchTime = fetchTime;
	}

	private int humidity;
    private double windSpeed;
    private LocalDateTime fetchTime;
    private double latitude;
    private double longitude;

    // Getters & Setters
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }


    // Constructors
    public WeatherEntity() {}

    public WeatherEntity(String cityName, String country, String weatherDescription,
                         double temperature, int humidity, double windSpeed) {
        this.cityName = cityName;
        this.country = country;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    // Getters and Setters
   

    public Long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
