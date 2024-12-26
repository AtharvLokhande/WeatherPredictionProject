package org.aatech.service;

import org.aatech.model.CityModel;
import org.aatech.repository.CityRepository;
import org.aatech.repository.CityRepositoryImpl;

public class CityServiceImpl implements CityService{
	CityRepository cityRepo = new CityRepositoryImpl();
	@Override
	public boolean isAddNewCity(CityModel model) {
		
		return cityRepo.isAddNewCity(model);
	}
	@Override
	public int getCityIdByName(String cityName, int stateId, int distId) {
		// TODO Auto-generated method stub
		return cityRepo.getCityIdByName(cityName, stateId, distId);
	}

}
