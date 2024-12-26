package org.aatech.repository;

import java.util.List;
import java.util.Optional;

import org.aatech.model.StateModel;
import org.aatech.model.DistModel;

public interface StateRepository {
	public boolean isAddNewState(StateModel model);
	public boolean isAddNewState(String stateName, String distName);
	public Optional<List<StateModel>> getAllStates();
	public List<DistModel> getDistListByName(String stateName);
	public int getStateIdByName(String stateName);
	public int getDistIdByName(String distName);
	public boolean isAddWeatherData(int cityId);
	

}
