package main;

import java.util.Scanner;
import model.*;
import exception.InvalidTemperatureException;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("=== Temperature Converter ===");

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter temperature value: ");
            double value = sc.nextDouble();

            System.out.print("Enter temperature type (C / F / K): ");
            String choice = sc.next().toUpperCase();

            if (choice.equals("C")) {
                double fahrenheit = (value * 9 / 5) + 32;
                System.out.println("Celsius to Fahrenheit: " + fahrenheit);
            }
            else if (choice.equals("F")) {
                double celsius = (value - 32) * 5 / 9;
                System.out.println("Fahrenheit to Celsius: " + celsius);
            }
            else if (choice.equals("K")) {
                double celsius = value - 273.15;
                System.out.println("Kelvin to Celsius: " + celsius);
            }
            else {
                System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("Invalid input!");
        }

        sc.close();
    }
}
