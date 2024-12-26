package org.aatech.repository;

import java.sql.*;
import java.util.*;

public class WeatherPredRepositoryImpl extends DBSTATE implements WeatherPredRepository {

    @Override
    public String WetherPrediction(int cityId) {
        String prediction = "";
        
        try {
            // Query to fetch the last 30 rows from the weather table
            String query = "SELECT * FROM weather ORDER BY date DESC LIMIT 30";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            // Initialize variables to calculate averages or detect patterns
            int totalTempMax = 0;
            int totalTempMin = 0;
            int totalPrecipitation = 0;
            int count = 0;
            Map<String, Integer> weatherCount = new HashMap<>();
            
            // Process the last 30 rows of weather data
            while (rs.next()) {
                totalTempMax += rs.getDouble("temp_max");
                totalTempMin += rs.getDouble("temp_min");
                totalPrecipitation += rs.getDouble("precipitation");
                String weather = rs.getString("weather");
                
                // Count occurrences of different weather types
                weatherCount.put(weather, weatherCount.getOrDefault(weather, 0) + 1);
                count++;
            }
            
            // Calculate average temperatures and precipitation
            double avgTempMax = totalTempMax / count;
            double avgTempMin = totalTempMin / count;
            double avgPrecipitation = totalPrecipitation / count;
            
            // Determine the most frequent weather type in the last 30 days
            String predictedWeather = getMostFrequentWeather(weatherCount);
            
            // Simple prediction logic based on average temperatures and weather type
            if (predictedWeather.equals("sun") && avgTempMax > 20) {
                prediction = "Tomorrow will be sunny with a high of " + avgTempMax + "°C and a low of " + avgTempMin + "°C.";
            } else if (predictedWeather.equals("rain") && avgPrecipitation > 2) {
                prediction = "Expect rain tomorrow with a high of " + avgTempMax + "°C and a low of " + avgTempMin + "°C.";
            } else if (predictedWeather.equals("fog")) {
                prediction = "Tomorrow will be foggy with a high of " + avgTempMax + "°C and a low of " + avgTempMin + "°C.";
            } else {
                prediction = "The weather tomorrow is uncertain, but it will likely be " + predictedWeather + " with a high of " + avgTempMax + "°C and a low of " + avgTempMin + "°C.";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            prediction = "Error retrieving weather data.";
        }
        
        return prediction;
    }

    // Helper method to determine the most frequent weather type
    private String getMostFrequentWeather(Map<String, Integer> weatherCount) {
        return weatherCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("sun"); // Default to "sun" if no data is found
    }
}
