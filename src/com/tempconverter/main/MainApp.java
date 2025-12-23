package com.tempconverter.main;

import java.util.Scanner;

import com.tempconverter.model.*;
import com.tempconverter.thread.ConversionThread;
import com.tempconverter.exception.InvalidTemperatureException;

public class MainApp {

    static String appName = "Temperature Converter";

    public static void main(String[] args) {

        System.out.println("=== " + appName + " ===");

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter temperature value: ");
            double value = sc.nextDouble();

            System.out.print("Enter temperature type (C / F / K): ");
            String choice = sc.next().toUpperCase();

            Temperature temp = null; // Dynamic Method Dispatch

            if (choice.equals("C")) {
                temp = new Celsius(value);
            }
            else if (choice.equals("F")) {
                temp = new Fahrenheit(value);
            }
            else if (choice.equals("K")) {
                temp = new Kelvin(value);
            }
            else {
                System.out.println("Invalid choice!");
                System.exit(0);
            }

            // Run conversion in a separate thread
            ConversionThread thread = new ConversionThread(temp);
            thread.start();

        } catch (InvalidTemperatureException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }

        sc.close();
    }
}
