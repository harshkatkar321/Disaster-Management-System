package com.app.model;

import java.util.List;

public class WeatherResponse {
	
	private String name;
	private Sys sys;
	private List<Weather> weather;
	private Main main;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	private Wind wind;
	public static class Sys{
		private String country;

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
	}
	
	public static class Weather{
		private int id;
		private String description;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public static class Main{
		private double temp;
		private int humidity;
		public double getTemp() {
			return temp;
		}
		public void setTemp(double temp) {
			this.temp = temp;
		}
		public int getHumidity() {
			return humidity;
		}
		public void setHumidity(int humidity) {
			this.humidity = humidity;
		}
	}
	
	public static class Wind{
		private double speed;

		public double getSpeed() {
			return speed;
		}

		public void setSpeed(double speed) {
			this.speed = speed;
		}
	}
	
	public static class Coord {
	    private double lat;
	    private double lon;

	    public double getLat() { return lat; }
	    public void setLat(double lat) { this.lat = lat; }

	    public double getLon() { return lon; }
	    public void setLon(double lon) { this.lon = lon; }
	}
	private Coord coord;

	public Coord getCoord() { return coord; }
	public void setCoord(Coord coord) { this.coord = coord; }


}