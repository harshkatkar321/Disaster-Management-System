package com.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.model.WeatherEntity;
import com.app.model.WeatherResponse;
import com.app.repository.WeatherRepository;

@Service
public class WeatherService {
	
	@Value("${api.key}")
	private String apiKey;
	
	@Autowired
	private WeatherRepository weatherRepository;
	
	public WeatherEntity fetchAndSaveCity(String city) {
		String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apiKey+"&units=metric";
		RestTemplate restTemplate  = new RestTemplate();
		WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
		
		if(weatherResponse==null||weatherResponse.getWeather() == null || weatherResponse.getWeather().isEmpty()) {
			return null;
		}
		
		WeatherEntity entity = new WeatherEntity();
			entity.setCityName(weatherResponse.getName());
			entity.setCountry(weatherResponse.getSys().getCountry());
			entity.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());
			entity.setTemperature(weatherResponse.getMain().getTemp());
			entity.setHumidity(weatherResponse.getMain().getHumidity());
			entity.setWindSpeed(weatherResponse.getWind().getSpeed());
			entity.setFetchTime(LocalDateTime.now());
			entity.setLatitude(weatherResponse.getCoord().getLat());
			entity.setLongitude(weatherResponse.getCoord().getLon());

			return weatherRepository.save(entity);
		}
	}


