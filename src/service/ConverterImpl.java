package service;

import model.*;

public class ConverterImpl implements Converter {

    // Method overriding
    @Override
    public double convert(double value) {
        return value;
    }

    // Method overloading
    public double convert(Celsius c) {
        return (c.getValue() * 9 / 5) + 32;
    }

    public double convert(Fahrenheit f) {
        return (f.getValue() - 32) * 5 / 9;
    }

    public double convert(Kelvin k) {
        return k.getValue() - 273.15;
    }
}
