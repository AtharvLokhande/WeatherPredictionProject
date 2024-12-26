package org.aatech.model;

public class StateModel {
	private int stateId;

	public StateModel() {

	}

	public StateModel(int id, String name) {
		this.stateId = id;
		this.stateName = name;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	private String stateName;
	
	public String toString() {
		return "["+stateId+","+stateName+"]"; 
	}
	
	public boolean equals(Object obj) {
		StateModel model = (StateModel)obj;
		if (model.getStateId()==this.stateId&&model.getStateName().equals(this.stateName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return stateId*1000;
	}
}
