package com.shometeam.ao.shome.SyncClasses;

import android.util.Log;

import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by ao on 15.05.15.
 */
public class WeatherConnector {
    String mCityName;

    public WeatherConnector(String cityName){
        mCityName = cityName;
    }

    public float[] getWeatherForecastForDay(int dayNumber) throws IOException, JSONException{ //in new Thread only 0-today
        float[] temperature = new float[4];
        OpenWeatherMap owm = new OpenWeatherMap("");
        DailyForecast today = owm.dailyForecastByCityName(mCityName, (byte)dayNumber);
        temperature[0]= today.getForecastInstance(dayNumber).getTemperatureInstance().getMorningTemperature();
        temperature[1]= today.getForecastInstance(dayNumber).getTemperatureInstance().getDayTemperature();
        temperature[2]= today.getForecastInstance(dayNumber).getTemperatureInstance().getEveningTemperature();
        temperature[3]= today.getForecastInstance(dayNumber).getTemperatureInstance().getNightTemperature();
        return temperature;
    }

    public float[] getWeatherForecastForWeek() throws IOException, JSONException{ //in new Thread only
        float[] temperature = new float[28];
        OpenWeatherMap owm = new OpenWeatherMap("");
        DailyForecast today = owm.dailyForecastByCityName(mCityName, (byte)7);
        for(int i=0; i<7; i++){
            temperature[i*4]= today.getForecastInstance(i).getTemperatureInstance().getMorningTemperature();
            temperature[i*4+1]= today.getForecastInstance(i).getTemperatureInstance().getDayTemperature();
            temperature[i*4+2]= today.getForecastInstance(i).getTemperatureInstance().getEveningTemperature();
            temperature[i*4+3]= today.getForecastInstance(i).getTemperatureInstance().getNightTemperature();
            Log.d("Temp","Day"+String.valueOf(i+1));
            Log.d("Temp",String.valueOf(temperature[i*4]));
            Log.d("Temp",String.valueOf(temperature[i*4+1]));
            Log.d("Temp",String.valueOf(temperature[i*4+2]));
            Log.d("Temp",String.valueOf(temperature[i*4+3]));
        }
        return temperature;
    }

}