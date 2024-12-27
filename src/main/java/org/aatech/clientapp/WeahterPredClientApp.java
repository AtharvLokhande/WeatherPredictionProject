package org.aatech.clientapp;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.aatech.model.StateModel;
import org.aatech.service.StateServiceImpl;
import org.aatech.service.WeatherPredService;
import org.aatech.service.WeatherPredServiceImpl;
import org.aatech.model.CityModel;
import org.aatech.model.DistModel;
import org.aatech.service.CityService;
import org.aatech.service.CityServiceImpl;

public class WeahterPredClientApp {
	 private static final Logger logger = LogManager.getLogger(WeahterPredClientApp.class);
	    static int count = 0;

	public static void main(String[] args) {
		logger.info("Weather Prediction Client Application Started");

		StateServiceImpl stateService = new StateServiceImpl();
		CityService cityService = new CityServiceImpl();
		WeatherPredService weatherPredService = new WeatherPredServiceImpl();
	

		do {
			Scanner xyz = new Scanner(System.in);
			logger.info("Displaying menu options to the user");

			System.out.println("1: Add New State");
			System.out.println("2: Add new Dist");
			System.out.println("3: Add New City");
			System.out.println("4: Add Past weather Data in city");
			System.out.println("5: Predict the tomorrow weather using liner regression");

			int choice = xyz.nextInt();
			logger.debug("User selected menu option: {}", choice);


			switch (choice) {
			case 1:
				logger.debug("Case 1 selected: Adding new state");
                System.out.println("Enter the state name");
                xyz.nextLine();
                String stateName = xyz.nextLine();
                logger.debug("State name entered: {}", stateName);

                boolean b = stateService.isAddNewState(new StateModel(0, stateName));
                if (b) {
                    logger.info("State '{}' added successfully", stateName);
                    System.out.println("State Added Successfully");
                } else {
                    logger.warn("Failed to add state: {}", stateName);
                    System.out.println("State Not Added");
                }
                break;
			case 2:
				logger.debug("Case 2 selected: Adding new district");
                System.out.println("Enter the state name");
                xyz.nextLine();
                stateName = xyz.nextLine();
                System.out.println("Enter the district name");
                String distName = xyz.nextLine();
                logger.debug("State: {}, District: {}", stateName, distName);

                b= stateService.isAddNewDist(stateName, distName);
                if (b) {
                    logger.info("District '{}' added successfully to state '{}'", distName, stateName);
                    System.out.println("District added successfully");
                } else {
                    logger.warn("Failed to add district '{}' to state '{}'", distName, stateName);
                    System.out.println("District not added");
                }
                break;
			case 3:
				logger.debug("Case 3 selected: Adding new city");
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
							logger.info("City '{}' added successfully in district '{}', state '{}'", cityName, distName, stateName);
                            System.out.println("City added succesfully...");
						} else {
							logger.warn("Failed to add city '{}' in district '{}', state '{}'", cityName, distName, stateName);
                            System.out.println("City Not Added...");
						}
					} else {
						logger.warn("District list is null for state: {}", stateName);
                        System.out.println("Do you want to add " + stateName + " in database");
						String msg = xyz.nextLine();
						if (msg.equals("yes")) {
							b = stateService.isAddNewState(new StateModel(0, stateName));
							if (b) {
								logger.info("State '{}' added successfully", stateName);
                                System.out.println("New State Added successfully...");
							}
						} else {
							logger.warn("State '{}' not added by user decision", stateName);
                            System.out.println("state Not Added");
						}
					}

				} else {
					logger.error("No states available in the database");
                    System.out.println("There is no data table is present");

				}
				break;
			case 4:
				logger.debug("Case 4 selected: Adding past weather data in city");
                o = stateService.getAllStates();
				if (o.isPresent()) {
					List<StateModel> states = o.get();
					states.forEach((state) -> System.out.println((++count) + "\t" + state.getStateName()));
					System.out.println("Enter state name");
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
						System.out.println("Enter District name");
						distName = xyz.nextLine();
						int stateId = stateService.getStateIdByName(stateName);
						int distId = stateService.getDistIdByName(distName);
						//System.out.println(stateId + " " + distId);
						System.out.println("Enter the city name");
						String cityName = xyz.nextLine();
						int cityId = cityService.getCityIdByName(cityName, stateId, distId);
						
						b = stateService.isAddWeatherData(cityId);
						if (b) {
							logger.info("Weather Data added successfully");
							System.out.println("weather data added successfully");
						} else {
							logger.warn("Weather Data added successfully");
							System.out.println("weather data not added");
						}
						
						
					} else {
						System.out.println("Do you want to add " + stateName + " in database");
						String msg = xyz.nextLine();
						if (msg.equals("yes")) {
							b = stateService.isAddNewState(new StateModel(0, stateName));
							if (b) {
								logger.warn("Weather Data Not added");
                                System.out.println("Weather data Not Added");
							}
						} else {
							logger.warn("Weather Data Not added");
							System.out.println("Weather data Not Added");
						}
					}

				} else {
					logger.info("There is No data in table");
					System.out.println("There is no data table is present");
				}
				break;
			case 5:
				logger.debug("Case 5 selected: Predicting tomorrow's weather using linear regression");
                o = stateService.getAllStates();
				if (o.isPresent()) {
					List<StateModel> states = o.get();
					states.forEach((state) -> System.out.println((++count) + "\t" + state.getStateName()));
					System.out.println("Enter state name ");
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
						System.out.println("Enter District Name");
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
								logger.info("New State Added successfully...");
								System.out.println("New State Added successfully...");
							}
						} else {
							logger.warn("State Not added");
							System.out.println("state Not Added");
						}
					}

				} else {
					logger.info("There is No data ");
					System.out.println("There is no data table is present");
				}
				break;
				
			default:
				logger.info("Invalid choice");
				System.out.println("Invalid choice. Please select again.");
			}

		} while (true);
	}
}
