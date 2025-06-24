package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.model.WeatherEntity;
import com.app.model.WeatherResponse;
import com.app.repository.WeatherRepository;
import com.app.service.WeatherService;


@Controller
public class WeatherController {
	
	@Autowired
	private WeatherRepository weatherRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	
	
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	@GetMapping("/weather")
	public String getWeather(@ModelAttribute("city") String city, Model model) {
	    WeatherEntity entity = weatherService.fetchAndSaveCity(city);

	    if (entity == null) {
	        model.addAttribute("error", "City not found or weather data unavailable.");
	        return "index"; // or a dedicated error page
	    }

	    model.addAttribute("entity", entity); // for rendering in map.html
	    return "map";
	}

	
	@GetMapping("/api/weather/all")
	@ResponseBody
	public List<WeatherEntity> getAllWeatherData() {
	    return weatherRepository.findAll();
	}


}
