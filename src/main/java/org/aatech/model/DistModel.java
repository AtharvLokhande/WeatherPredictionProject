package org.aatech.model;

public class DistModel extends StateModel {

	private int id;
	
	public DistModel() {
		
	}
	public DistModel(String name, int id) {
		this.name=name;
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	
}
