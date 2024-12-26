package org.aatech.service;

import java.util.List;
import java.util.Optional;

import org.aatech.model.StateModel;
import org.aatech.repository.StateRepository;
import org.aatech.repository.StateRepositoryImpl;

public class StateServiceImpl implements StateService{
	StateRepository stmtRepo = new StateRepositoryImpl();
	@Override
		public boolean isAddNewState(StateModel model) {

			return stmtRepo.isAddNewState(model);
		}
	@Override
	public boolean isAddNewDist(String stateName, String distName) {
		// TODO Auto-generated method stub
		return stmtRepo.isAddNewState(stateName, distName);
	}
	@Override
	public Optional<List<StateModel>> getAllStates() {
		return stmtRepo.getAllStates();
	}
	@Override
	public List getDistListByName(String stateName) {
		
		return stmtRepo.getDistListByName(stateName);
	}
	@Override
	public int getStateIdByName(String stateName) {
		
		return stmtRepo.getStateIdByName(stateName);
	}

	@Override
	public int getDistIdByName(String distName) {
		
		return stmtRepo.getDistIdByName(distName);
	}
	@Override
	public boolean isAddWeatherData(int cityId) {
		// TODO Auto-generated method stub
		return stmtRepo.isAddWeatherData(cityId);
	}
	
}
