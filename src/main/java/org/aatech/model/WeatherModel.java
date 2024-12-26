package org.aatech.model;

public class WeatherModel {
    private String date;
    private float precipitation;
    private float tempMax;
    private float tempMin;
    private float wind;

    // Constructor
    public WeatherModel(String date, float precipitation, float tempMax, float tempMin, float wind) {
        this.date = date;
        this.precipitation = precipitation;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.wind = wind;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherModel [date=" + date + ", precipitation=" + precipitation + ", tempMax=" + tempMax
                + ", tempMin=" + tempMin + ", wind=" + wind + "]";
    }
}
