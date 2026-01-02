package model;

import exception.InvalidTemperatureException;

public class Fahrenheit extends Temperature {

    public Fahrenheit(double value) throws InvalidTemperatureException {
        super(value);
    }
}
