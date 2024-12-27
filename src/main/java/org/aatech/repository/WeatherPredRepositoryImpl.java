package org.aatech.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WeatherPredRepositoryImpl extends DBSTATE implements WeatherPredRepository {

    @Override
    public String WetherPrediction(int cityId) {
        List<Double> precipitation = new ArrayList<>();
        List<Double> tempMax = new ArrayList<>();
        List<Double> tempMin = new ArrayList<>();
        List<Double> windFlow = new ArrayList<>();
        List<String> weatherType = new ArrayList<>(); 

        try  {
        	stmt = conn.prepareStatement("SELECT w.precipitation, w.temp_max, w.temp_min, w.wind, w.weather FROM cityweatherjoin cw JOIN weather w ON cw.weatherid = w.id WHERE cw.cityid = ? ORDER\n"
        			+ " BY w.date desc LIMIT 30 ");
            stmt.setInt(1, cityId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                precipitation.add(resultSet.getDouble("precipitation"));
                tempMax.add(resultSet.getDouble("temp_max"));
                tempMin.add(resultSet.getDouble("temp_min"));
                windFlow.add(resultSet.getDouble("wind"));
                weatherType.add(resultSet.getString("weather"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching weather data.";
        }

        // Ensure we have enough data
        if (weatherType.size() < 2) {
            return "Not enough data for prediction.";
        }

        // Predict weather type for tomorrow // calling predictWeatherType
        String predictedWeather = predictWeatherType(precipitation, tempMax, tempMin, windFlow, weatherType);

        return "Predicted weather type for tomorrow is " + predictedWeather;
    }

    private String predictWeatherType(List<Double> precipitation, List<Double> tempMax, 
                                      List<Double> tempMin, List<Double> windFlow, 
                                      List<String> weatherType) {
        int n = weatherType.size();

        // Example: Use the last day's features to predict tomorrow
        double nextPrecipitation = precipitation.get(n - 1);
        double nextTempMax = tempMax.get(n - 1);
        double nextTempMin = tempMin.get(n - 1);
        double nextWindFlow = windFlow.get(n - 1);

        // Build a simple rule-based classifier (example)
        Map<String, Double> probabilities = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String type = weatherType.get(i);
            double similarityScore = 0;

            // Calculate similarity score based on feature closeness
         // Calculate similarity for precipitation
            double precipitationDifference = Math.abs(precipitation.get(i) - nextPrecipitation);
            similarityScore += 1.0 / (1 + precipitationDifference);

            // Calculate similarity for max temperature
            double tempMaxDifference = Math.abs(tempMax.get(i) - nextTempMax);
            similarityScore += 1.0 / (1 + tempMaxDifference);

            // Calculate similarity for min temperature
            double tempMinDifference = Math.abs(tempMin.get(i) - nextTempMin);
            similarityScore += 1.0 / (1 + tempMinDifference);

            // Calculate similarity for wind flow
            double windFlowDifference = Math.abs(windFlow.get(i) - nextWindFlow);
            similarityScore += 1.0 / (1 + windFlowDifference);


            probabilities.put(type, probabilities.getOrDefault(type, 0.0) + similarityScore);
        }

        // Find the weather type with the highest probability
        return probabilities.entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Finds the entry with the highest value (i.e., highest probability)
                .map(Map.Entry::getKey)             // Maps the result to the key (the weather type)
                .orElse("Unknown");                 // If no entries are found, return "Unknown"

    }

	@Override
	public String WetherPredictionBydate(int cityId, String dateInput) {
		 List<Double> precipitation = new ArrayList<>();
	        List<Double> tempMax = new ArrayList<>();
	        List<Double> tempMin = new ArrayList<>();
	        List<Double> windFlow = new ArrayList<>();
	        List<String> weatherType = new ArrayList<>();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        int month=0;
	        int day=0;
	        try {
	            // Parse the input string to LocalDate
	            LocalDate date = LocalDate.parse(dateInput, formatter);

	            // Get the year, month, and day from the LocalDate object
	            int year = date.getYear();
	            month = date.getMonthValue();  // Gets the month as an integer (1-12)
	            day = date.getDayOfMonth();    // Gets the day of the month (1-31)
	            
//	            // Output the separated values
//	            System.out.println("Year: " + year);
//	            System.out.println("Month: " + month);
//	            System.out.println("Day: " + day);
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
	        }

	        try  {
	        	stmt = conn.prepareStatement("SELECT w.precipitation, w.temp_max, w.temp_min, w.wind, w.weather FROM cityweatherjoin cw JOIN weather w ON cw.weatherid = w.id WHERE cw.cityid = ? AND MONTH(w.date) =? AND DAY(w.date) = ?");
	            stmt.setInt(1, cityId);
	            stmt.setInt(2, month);
	            stmt.setInt(3, day);
	            ResultSet resultSet = stmt.executeQuery();

	            while (resultSet.next()) {
	                precipitation.add(resultSet.getDouble("precipitation"));
	                tempMax.add(resultSet.getDouble("temp_max"));
	                tempMin.add(resultSet.getDouble("temp_min"));
	                windFlow.add(resultSet.getDouble("wind"));
	                weatherType.add(resultSet.getString("weather"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error fetching weather data.";
	        }

	        // Ensure we have enough data
	        if (weatherType.size() < 2) {
	            return "Not enough data for prediction.";
	        }

	        // Predict weather type for tomorrow
	        String predictedWeather = predictWeatherTypeBydate(precipitation, tempMax, tempMin, windFlow, weatherType);

	        return "Predicted weather type for specifice date is " + predictedWeather;
	    }

	    private String predictWeatherTypeBydate(List<Double> precipitation, List<Double> tempMax, 
	                                      List<Double> tempMin, List<Double> windFlow, 
	                                      List<String> weatherType) {
	        int n = weatherType.size();

	        // Example: Use the last day's features to predict tomorrow
	        double nextPrecipitation = precipitation.get(n - 1);
	        double nextTempMax = tempMax.get(n - 1);
	        double nextTempMin = tempMin.get(n - 1);
	        double nextWindFlow = windFlow.get(n - 1);

	        // Build a simple rule-based classifier (example)
	        Map<String, Double> probabilities = new HashMap<>();
	        for (int i = 0; i < n; i++) {
	            String type = weatherType.get(i);
	            double similarityScore = 0;

	            // Calculate similarity score based on feature closeness
	            
	         // Calculate similarity for precipitation
	            double precipitationDifference = Math.abs(precipitation.get(i) - nextPrecipitation);
	            similarityScore += 1.0 / (1 + precipitationDifference);
	            System.out.println(similarityScore);


	            // Calculate similarity for max temperature
	            double tempMaxDifference = Math.abs(tempMax.get(i) - nextTempMax);
	            similarityScore += 1.0 / (1 + tempMaxDifference);
	            System.out.println(similarityScore);


	            // Calculate similarity for min temperature
	            double tempMinDifference = Math.abs(tempMin.get(i) - nextTempMin);
	            similarityScore += 1.0 / (1 + tempMinDifference);
	            System.out.println(similarityScore);


	            // Calculate similarity for wind flow
	            double windFlowDifference = Math.abs(windFlow.get(i) - nextWindFlow);
	            similarityScore += 1.0 / (1 + windFlowDifference);
	            System.out.println(similarityScore);


	            probabilities.put(type, probabilities.getOrDefault(type, 0.0) + similarityScore);
	        }

	        // Find the weather type with the highest probability
	        return probabilities.entrySet().stream()
	                .max(Map.Entry.comparingByValue())  // Finds the entry with the highest value (i.e., highest probability)
	                .map(Map.Entry::getKey)             // Maps the result to the key (the weather type)
	                .orElse("Unknown");                 // If no entries are found, return "Unknown"

	}
}
