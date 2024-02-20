package com.troy.shop;


import javax.swing.*;
import java.util.List;

// ResultsWindow class extending JFrame for displaying results
public class ResultsWindow extends JFrame {

    // Constructor for ResultsWindow
    public ResultsWindow(List<String> boughtUnits, int totalCostOfUnits, int rerollCount) {
        initComponents(boughtUnits, totalCostOfUnits, rerollCount);
    }

    // Method to initialize and set up ResultsWindow components
    private void initComponents(List<String> boughtUnits, int totalCostOfUnits, int rerollCount) {
        // Set up the frame and layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Add components to display results
        JTextArea resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        resultsTextArea.append("Results:\n\n");

        // Display bought units
        resultsTextArea.append("Bought Units:\n");
        for (String boughtUnit : boughtUnits) {
            resultsTextArea.append(boughtUnit + "\n");
        }

        // Display total cost of units
        resultsTextArea.append("\nTotal Cost of Units: " + totalCostOfUnits +" gold \n");

        // Display reroll count
        resultsTextArea.append("\nRerolls: " + (rerollCount-1) + "\n");

        // Calculate total gold spent
        int totalGoldSpent = totalCostOfUnits + ((rerollCount-1) * 2);

        // Display total gold spent
        resultsTextArea.append("\nTotal Gold Spent: " + totalGoldSpent +" gold \n");

        // Add resultsTextArea to the frame
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        add(scrollPane);

        // Set frame properties
        setVisible(true);
    }
}
