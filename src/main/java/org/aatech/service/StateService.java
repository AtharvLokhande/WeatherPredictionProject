package org.aatech.service;

import java.util.List;
import java.util.Optional;

import org.aatech.model.StateModel;
import org.aatech.model.DistModel;

public interface StateService {
	public boolean isAddNewState(StateModel model);
	public boolean isAddNewDist(String stateName, String distName);
	public Optional<List<StateModel>> getAllStates();
	public List<DistModel> getDistListByName(String stateName);
	public int getStateIdByName(String stateName);
	public int getDistIdByName(String distName);
	public boolean isAddWeatherData(int cityId);

}
