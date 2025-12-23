package com.tempconverter.model;

import com.tempconverter.exception.InvalidTemperatureException;

public abstract class Temperature {

    protected double value;

    // Constructor
    public Temperature(double value) throws InvalidTemperatureException {
        if (value < -273.15) {
            throw new InvalidTemperatureException("Temperature below absolute zero!");
        }
        this.value = value;
    }

    // Abstract method
    public abstract String getUnit();

    // Concrete method
    public double getValue() {
        return value;
    }
}
