package model;

import exception.InvalidTemperatureException;

public class Kelvin extends Temperature {

    public Kelvin(double value) throws InvalidTemperatureException {
        super(value);
    }

    @Override
    public String getUnit() {
        return "Kelvin";
    }
}
