package org.aatech.clientapp;

import java.util.*;

import org.aatech.model.StateModel;
import org.aatech.service.StateServiceImpl;
import org.aatech.service.WeatherPredService;
import org.aatech.service.WeatherPredServiceImpl;
import org.aatech.model.CityModel;
import org.aatech.model.DistModel;
import org.aatech.service.CityService;
import org.aatech.service.CityServiceImpl;

public class WeahterPredClientApp {
	static int count = 0;

	public static void main(String[] args) {

		StateServiceImpl stateService = new StateServiceImpl();
		CityService cityService = new CityServiceImpl();
		WeatherPredService weatherPredService = new WeatherPredServiceImpl();
	

		do {
			Scanner xyz = new Scanner(System.in);
			System.out.println("1: Add New State");
			System.out.println("2: Add new Dist");
			System.out.println("3: Add New City");
			System.out.println("4: Add Past weather Data in city");
			System.out.println("5: Predict the tomorrow weather using liner regression");

			int choice = xyz.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter the state name");
				xyz.nextLine();
				String stateName = xyz.nextLine();

				System.out.println(stateService.isAddNewState(new StateModel(0, stateName)) ? "State Added Sucessfully"
						: "State Not Added");
				break;
			case 2:
				System.out.println("Enter the state name");
				xyz.nextLine();
				stateName = xyz.nextLine();
				System.out.println("Enter the dist name");
				String distName = xyz.nextLine();

				boolean b = stateService.isAddNewDist(stateName, distName);
				if (b) {
					System.out.println("Dist added successfully");
				} else {
					System.out.println("Dist not added");
				}
				break;
			case 3:
				Optional<List<StateModel>> o = stateService.getAllStates();
				if (o.isPresent()) {
					List<StateModel> states = o.get();
					states.forEach((state) -> System.out.println((++count) + "\t" + state.getStateName()));
					System.out.println("Enter state name in which City want to add");
					xyz.nextLine();
					stateName = xyz.nextLine();
					System.out.println("Distric Name");
					System.out.println("========================================");
					List<DistModel> list = stateService.getDistListByName(stateName);
					if (list != null) {
						list.forEach((dist) -> System.out.println(dist.getId() + "\t" + dist.getName()));
						System.out.println("========================================");
						states.clear();
						list.clear();
						System.out.println("Enter District name in which city want to add");
						distName = xyz.nextLine();
						int stateId = stateService.getStateIdByName(stateName);
						int distId = stateService.getDistIdByName(distName);
						// System.out.println(stateId + " " + distId);
						System.out.println("Enter the city name");
						String cityName = xyz.nextLine();
						CityModel cityModel = new CityModel();
						cityModel.setId(distId);
						cityModel.setStateId(stateId);
						cityModel.setCityName(cityName);
						b = cityService.isAddNewCity(cityModel);
						if (b) {
							System.out.println("City added succesfully...");
						} else {
							System.out.println("City Not Added...");
						}
					} else {
						System.out.println("Do you want to add " + stateName + " in database");
						String msg = xyz.nextLine();
						if (msg.equals("yes")) {
							b = stateService.isAddNewState(new StateModel(0, stateName));
							if (b) {
								System.out.println("New State Added successfully...");
							}
						} else {
							System.out.println("state Not Added");
						}
					}

				} else {
					System.out.println("There is no data table is present");

				}
				break;
			case 4:
				o = stateService.getAllStates();
				if (o.isPresent()) {
					List<StateModel> states = o.get();
					states.forEach((state) -> System.out.println((++count) + "\t" + state.getStateName()));
					System.out.println("Enter state name in which City want to add");
					xyz.nextLine();
					stateName = xyz.nextLine();
					System.out.println("Distric Name");
					System.out.println("========================================");
					List<DistModel> list = stateService.getDistListByName(stateName);
					if (list != null) {
						list.forEach((dist) -> System.out.println(dist.getId() + "\t" + dist.getName()));
						System.out.println("========================================");
						states.clear();
						list.clear();
						System.out.println("Enter District name in which city want to add");
						distName = xyz.nextLine();
						int stateId = stateService.getStateIdByName(stateName);
						int distId = stateService.getDistIdByName(distName);
						//System.out.println(stateId + " " + distId);
						System.out.println("Enter the city name");
						String cityName = xyz.nextLine();
						int cityId = cityService.getCityIdByName(cityName, stateId, distId);
						
						b = stateService.isAddWeatherData(cityId);
						if (b) {
							System.out.println("weather data added successfully");
						} else {
							System.out.println("weather data not added");
						}
						
						
					} else {
						System.out.println("Do you want to add " + stateName + " in database");
						String msg = xyz.nextLine();
						if (msg.equals("yes")) {
							b = stateService.isAddNewState(new StateModel(0, stateName));
							if (b) {
								System.out.println("New State Added successfully...");
							}
						} else {
							System.out.println("state Not Added");
						}
					}

				} else {
					System.out.println("There is no data table is present");
				}
				break;
			case 5:
				o = stateService.getAllStates();
				if (o.isPresent()) {
					List<StateModel> states = o.get();
					states.forEach((state) -> System.out.println((++count) + "\t" + state.getStateName()));
					System.out.println("Enter state name in which City want to add");
					xyz.nextLine();
					stateName = xyz.nextLine();
					System.out.println("Distric Name");
					System.out.println("========================================");
					List<DistModel> list = stateService.getDistListByName(stateName);
					if (list != null) {
						list.forEach((dist) -> System.out.println(dist.getId() + "\t" + dist.getName()));
						System.out.println("========================================");
						states.clear();
						list.clear();
						System.out.println("Enter District name in which city want to add");
						distName = xyz.nextLine();
						int stateId = stateService.getStateIdByName(stateName);
						int distId = stateService.getDistIdByName(distName);
						//System.out.println(stateId + " " + distId);
						System.out.println("Enter the city name");
						String cityName = xyz.nextLine();
						int cityId = cityService.getCityIdByName(cityName, stateId, distId);
						 
						String Prediction = weatherPredService.WetherPrediction(cityId);
						
						System.out.println(Prediction);
					
						
					} else {
						System.out.println("Do you want to add " + stateName + " in database");
						String msg = xyz.nextLine();
						if (msg.equals("yes")) {
							b = stateService.isAddNewState(new StateModel(0, stateName));
							if (b) {
								System.out.println("New State Added successfully...");
							}
						} else {
							System.out.println("state Not Added");
						}
					}

				} else {
					System.out.println("There is no data table is present");
				}
				break;

			default:
				System.out.println("Invalid choice. Please select again.");
			}

		} while (true);
	}
}
