package com.tempconverter.model;

import com.tempconverter.exception.InvalidTemperatureException;

public class Fahrenheit extends Temperature {

    public Fahrenheit(double value) throws InvalidTemperatureException {
        super(value);
    }

    @Override
    public String getUnit() {
        return "Fahrenheit";
    }
}
