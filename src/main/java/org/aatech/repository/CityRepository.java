package org.aatech.repository;

import org.aatech.model.CityModel;

public interface CityRepository {
	
	public boolean isAddNewCity(CityModel model);
	public int getCityIdByName(String cityName,int stateId,int distId);

}
