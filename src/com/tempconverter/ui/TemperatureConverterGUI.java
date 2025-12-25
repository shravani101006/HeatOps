package com.tempconverter.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.tempconverter.model.*;
import com.tempconverter.service.*;
import com.tempconverter.exception.InvalidTemperatureException;

public class TemperatureConverterGUI extends JFrame {
    
    private JTextField inputField;
    private JComboBox<String> unitComboBox;
    private JLabel celsiusResult;
    private JLabel fahrenheitResult;
    private JLabel kelvinResult;
    private JButton convertButton;
    private JLabel errorLabel;
    
    private static final Color BG_PRIMARY = new Color(10, 15, 30);
    private static final Color BG_SECONDARY = new Color(15, 23, 42);
    private static final Color BG_CARD = new Color(30, 41, 59);
    private static final Color ACCENT = new Color(138, 92, 246);
    private static final Color ACCENT_HOVER = new Color(167, 139, 250);
    private static final Color TEXT_PRIMARY = new Color(230, 237, 243);
    private static final Color TEXT_SECONDARY = new Color(139, 148, 158);
    private static final Color BORDER_COLOR = new Color(51, 65, 85);
    private static final Color ERROR_COLOR = new Color(248, 81, 73);
    
    public TemperatureConverterGUI() {
        initializeUI();
    }
    
    private void initializeUI() {
        // Frame settings
        setTitle("HeatOps - Temperature Converter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        // Main panel with dark background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center Panel (Input and Results)
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Error Panel
        JPanel errorPanel = createErrorPanel();
        mainPanel.add(errorPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG_PRIMARY);
        
        // Title
        JLabel titleLabel = new JLabel("HeatOps");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(ACCENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Professional Temperature Converter");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        return headerPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(20, 30));
        centerPanel.setBackground(BG_SECONDARY);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        
        // Input Section
        JPanel inputSection = createInputSection();
        centerPanel.add(inputSection, BorderLayout.NORTH);
        
        // Results Section
        JPanel resultsSection = createResultsSection();
        centerPanel.add(resultsSection, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createInputSection() {
        JPanel inputSection = new JPanel();
        inputSection.setLayout(new BorderLayout(0, 15));
        inputSection.setBackground(BG_SECONDARY);
        
        // Section Title
        JLabel inputTitle = new JLabel("📥 Input Temperature");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        inputTitle.setForeground(TEXT_PRIMARY);
        inputSection.add(inputTitle, BorderLayout.NORTH);
        
        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        inputPanel.setBackground(BG_SECONDARY);
        
        // Input Field
        inputField = new JTextField(20);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        inputField.setBackground(BG_CARD);
        inputField.setForeground(TEXT_PRIMARY);
        inputField.setCaretColor(ACCENT);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Unit ComboBox
        String[] units = {"Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)"};
        unitComboBox = new JComboBox<>(units);
        unitComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        unitComboBox.setBackground(BG_CARD);
        unitComboBox.setForeground(TEXT_PRIMARY);
        unitComboBox.setPreferredSize(new Dimension(200, 55));
        
        // Convert Button
        convertButton = new JButton("Convert →");
        convertButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        convertButton.setBackground(ACCENT);
        convertButton.setForeground(TEXT_PRIMARY);
        convertButton.setFocusPainted(false);
        convertButton.setBorderPainted(false);
        convertButton.setPreferredSize(new Dimension(180, 55));
        convertButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        convertButton.addActionListener(e -> performConversion());
        
        // Add hover effect
        convertButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                convertButton.setBackground(ACCENT_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                convertButton.setBackground(ACCENT);
            }
        });
        
        // Enter key support
        inputField.addActionListener(e -> performConversion());
        
        inputPanel.add(inputField);
        inputPanel.add(unitComboBox);
        inputPanel.add(convertButton);
        
        inputSection.add(inputPanel, BorderLayout.CENTER);
        
        return inputSection;
    }
    
    private JPanel createResultsSection() {
        JPanel resultsSection = new JPanel();
        resultsSection.setLayout(new BorderLayout(0, 20));
        resultsSection.setBackground(BG_SECONDARY);
        
        // Section Title
        JLabel resultsTitle = new JLabel("📊 Converted Results");
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        resultsTitle.setForeground(TEXT_PRIMARY);
        resultsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        resultsSection.add(resultsTitle, BorderLayout.NORTH);
        
        // Results Cards Panel
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(1, 3, 30, 0));
        cardsPanel.setBackground(BG_SECONDARY);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Create result cards
        JPanel celsiusCard = createResultCard("❄️", "Celsius", "°C");
        JPanel fahrenheitCard = createResultCard("🔥", "Fahrenheit", "°F");
        JPanel kelvinCard = createResultCard("⚗️", "Kelvin", "K");
        
        cardsPanel.add(celsiusCard);
        cardsPanel.add(fahrenheitCard);
        cardsPanel.add(kelvinCard);
        
        resultsSection.add(cardsPanel, BorderLayout.CENTER);
        
        return resultsSection;
    }
    
    private JPanel createResultCard(String icon, String name, String unit) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 15));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(30, 25, 30, 25)
        ));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        headerPanel.setBackground(BG_CARD);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameLabel.setForeground(TEXT_SECONDARY);
        
        headerPanel.add(iconLabel);
        headerPanel.add(nameLabel);
        
        // Center Panel for Result
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.setBackground(BG_CARD);
        
        // Result Value
        JLabel resultLabel = new JLabel("--", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        resultLabel.setForeground(TEXT_PRIMARY);
        
        // Unit Label
        JLabel unitLabel = new JLabel(unit, SwingConstants.CENTER);
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        unitLabel.setForeground(TEXT_SECONDARY);
        
        // Store references
        if (name.equals("Celsius")) {
            celsiusResult = resultLabel;
        } else if (name.equals("Fahrenheit")) {
            fahrenheitResult = resultLabel;
        } else {
            kelvinResult = resultLabel;
        }
        
        centerPanel.add(resultLabel, BorderLayout.CENTER);
        centerPanel.add(unitLabel, BorderLayout.SOUTH);
        
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createErrorPanel() {
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        errorPanel.setBackground(BG_PRIMARY);
        
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        errorLabel.setForeground(ERROR_COLOR);
        errorLabel.setVisible(false);
        
        errorPanel.add(errorLabel);
        
        return errorPanel;
    }
    
    private void performConversion() {
        try {
            hideError();
            
            String inputText = inputField.getText().trim();
            if (inputText.isEmpty()) {
                showError("Please enter a temperature value!");
                return;
            }
            
            double value = Double.parseDouble(inputText);
            int selectedIndex = unitComboBox.getSelectedIndex();
            
            Temperature temp = null;
            
            switch (selectedIndex) {
                case 0: // Celsius
                    temp = new Celsius(value);
                    break;
                case 1: // Fahrenheit
                    temp = new Fahrenheit(value);
                    break;
                case 2: // Kelvin
                    temp = new Kelvin(value);
                    break;
            }
            
            if (temp != null) {
                Converter converter = new ConverterImpl();
                
                // Convert to all units
                double celsius = (temp instanceof Celsius) ? value : 
                                (temp instanceof Fahrenheit) ? (value - 32) * 5/9 : value - 273.15;
                double fahrenheit = (temp instanceof Fahrenheit) ? value : 
                                   (temp instanceof Celsius) ? (value * 9/5) + 32 : (value - 273.15) * 9/5 + 32;
                double kelvin = (temp instanceof Kelvin) ? value : 
                               (temp instanceof Celsius) ? value + 273.15 : (value - 32) * 5/9 + 273.15;
                
                // Update results
                celsiusResult.setText(String.format("%.2f", celsius));
                fahrenheitResult.setText(String.format("%.2f", fahrenheit));
                kelvinResult.setText(String.format("%.2f", kelvin));
            }
            
        } catch (InvalidTemperatureException e) {
            showError(e.getMessage());
            resetResults();
        } catch (NumberFormatException e) {
            showError("Invalid temperature value!");
            resetResults();
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
            resetResults();
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    private void hideError() {
        errorLabel.setVisible(false);
    }
    
    private void resetResults() {
        celsiusResult.setText("--");
        fahrenheitResult.setText("--");
        kelvinResult.setText("--");
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new TemperatureConverterGUI());
    }
}
