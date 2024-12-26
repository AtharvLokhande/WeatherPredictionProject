package org.aatech.service;

import org.aatech.model.CityModel;

public interface CityService {
	public boolean isAddNewCity(CityModel model);
	public int getCityIdByName(String cityName,int stateId,int distId);

}
