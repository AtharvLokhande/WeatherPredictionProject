package org.aatech.service;

import org.aatech.repository.*;

public class WeatherPredServiceImpl implements WeatherPredService {
	WeatherPredRepository stmtRepo = new WeatherPredRepositoryImpl(); 

	@Override
	public String WetherPrediction(int cityId) {
		
		return stmtRepo.WetherPrediction(cityId);
		
		
	}

}
