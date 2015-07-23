package com.shometeam.ao.ArduinoInfo;

import java.io.Serializable;

/**
 * Room class
 * Using as storage for room's measurements
 *
 * @author Artem Chernatsky
 * @version 1.0.1
 */

public class Room  implements Serializable {
    /** Measurement of room's humidity */
    private Float humidity;

    /** Measurement of room's temperature */
    private Float temperature;

    /** Measurement of room's illumination */
    private Float illumination;

    /**
     * Constructor with default parameters
     */
    public Room () {
        humidity = null;
        temperature = null;
        illumination = null;
    }

    /**
     * Constructor with custom parameters
     *
     * @param temperature is measurement of room's temperature
     * @param humidity is measurement of room's humidity
     * @param illumination is measurement of room's illumination
     */
    public Room (Float temperature, Float humidity, Float illumination) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.illumination = illumination;
    }

    /**
     * Method for setting room's humidity
     *
     * @param humidity is measurement of room's humidity
     */
    public synchronized void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    /**
     * Method for setting room's temperature
     *
     * @param temperature is measurement of room's temperature
     */
    public synchronized void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    /**
     * Method for setting room's illumination
     *
     * @param illumination is measurement of room's illumination
     */
    public synchronized void setIllumination(Float illumination) {
        this.illumination = illumination;
    }

    /**
     * Method which returns room's humidity measurement
     *
     * @return room's humidity measurement
     */
    public synchronized Float getHumidity () {
        return humidity;
    }

    /**
     * Method which returns room's temperature measurement
     *
     * @return room's temperature measurement
     */
    public synchronized Float getTemperature () {
        return temperature;
    }

    /**
     * Method which returns room's illumination measurement
     *
     * @return room's illumination measurement
     */
    public synchronized Float getIllumination () {
        return illumination;
    }
}
