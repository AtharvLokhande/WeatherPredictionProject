package org.aatech.service;

public interface WeatherPredService {
	
	public String WetherPrediction(int cityId);
	public String WetherPredictionBydate(int cityId, String dateInput);
	

}
