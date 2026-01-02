package thread;

import model.*;
import service.ConverterImpl;

public class ConversionThread extends Thread {

    private Temperature temp;

    public ConversionThread(Temperature temp) {
        this.temp = temp;
    }

    @Override
    public void run() {
        ConverterImpl converter = new ConverterImpl();

        if (temp instanceof Celsius) {
            System.out.println("Celsius to Fahrenheit: " +
                    converter.convert((Celsius) temp));
        }
        else if (temp instanceof Fahrenheit) {
            System.out.println("Fahrenheit to Celsius: " +
                    converter.convert((Fahrenheit) temp));
        }
        else if (temp instanceof Kelvin) {
            System.out.println("Kelvin to Celsius: " +
                    converter.convert((Kelvin) temp));
        }
    }
}
