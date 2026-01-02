package model;

import exception.InvalidTemperatureException;

public class Celsius extends Temperature {

    public Celsius(double value) throws InvalidTemperatureException {
        super(value);
    }

    @Override
    public String getUnit() {
        return "Celsius";
    }
}
