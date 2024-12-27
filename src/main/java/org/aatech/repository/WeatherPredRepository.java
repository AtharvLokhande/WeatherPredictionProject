package org.aatech.repository;

public interface WeatherPredRepository {

	public String WetherPrediction(int cityId);
	public String WetherPredictionBydate(int cityId, String dateInput);
}
