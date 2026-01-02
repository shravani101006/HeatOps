package ui;

import javax.swing.*;
import java.awt.*;
import model.*;
import exception.InvalidTemperatureException;

public class TemperatureConverterGUI extends JFrame {
    
    private JTextField inputField;
    private JComboBox<String> unitComboBox;
    private JTextArea resultArea;
    
    public TemperatureConverterGUI() {
        setTitle("Temperature Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputField = new JTextField(10);
        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        unitComboBox = new JComboBox<>(units);
        JButton convertBtn = new JButton("Convert");
        convertBtn.addActionListener(e -> convert());
        
        inputPanel.add(new JLabel("Value:"));
        inputPanel.add(inputField);
        inputPanel.add(unitComboBox);
        inputPanel.add(convertBtn);
        
        // Results
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panel.add(new JLabel("Temperature Converter", SwingConstants.CENTER));
        panel.add(inputPanel);
        panel.add(new JLabel("Results:"));
        panel.add(new JScrollPane(resultArea));
        
        add(panel);
        setVisible(true);
    }
    
    private void convert() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            String unit = (String) unitComboBox.getSelectedItem();
            
            Temperature temp = null;
            if (unit.equals("Celsius")) temp = new Celsius(value);
            else if (unit.equals("Fahrenheit")) temp = new Fahrenheit(value);
            else if (unit.equals("Kelvin")) temp = new Kelvin(value);
            
            double celsius = (temp instanceof Celsius) ? value : 
                            (temp instanceof Fahrenheit) ? (value - 32) * 5/9 : value - 273.15;
            double fahrenheit = (temp instanceof Fahrenheit) ? value : 
                               (temp instanceof Celsius) ? (value * 9/5) + 32 : (value - 273.15) * 9/5 + 32;
            double kelvin = (temp instanceof Kelvin) ? value : 
                           (temp instanceof Celsius) ? value + 273.15 : (value - 32) * 5/9 + 273.15;
            
            resultArea.setText(String.format("Celsius: %.2f°C\nFahrenheit: %.2f°F\nKelvin: %.2fK", 
                                            celsius, fahrenheit, kelvin));
        } catch (InvalidTemperatureException e) {
            resultArea.setText("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            resultArea.setText("Error: Invalid number");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TemperatureConverterGUI());
    }
}

