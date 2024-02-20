package com.troy.shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// StartMenu class extending JFrame for the initial configuration menu
public class StartMenu extends JFrame {

    private JRadioButton unlimitedModeButton;
    private JRadioButton timedModeButton;
    private ButtonGroup modeGroup;
    private JRadioButton[] levelButtons;
    private int selectedShopLevel;
    private JButton startButton;

    // Constructor to initialize the StartMenu frame
    public StartMenu() {
        initComponents();
    }

    // Method to initialize and set up StartMenu components
    private void initComponents() {
        // Radio buttons for game modes
        unlimitedModeButton = new JRadioButton("Unlimited Mode");
        timedModeButton = new JRadioButton("Timed Mode");

        // Button group to manage radio button selection exclusivity
        modeGroup = new ButtonGroup();
        modeGroup.add(unlimitedModeButton);
        modeGroup.add(timedModeButton);


        // Labels for mode and level selection
        JLabel modeLabel = new JLabel("Select Mode:");
        createLevelButtons(); // Initialize level selection radio buttons
        JLabel levelLabel = new JLabel("Select Level:");

        // Start button to initiate the game
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the start button click
                handleStartButtonClick();
            }
        });

        // Add components to the frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(modeLabel);
        add(unlimitedModeButton);
        add(timedModeButton);

        add(levelLabel);

        for (JRadioButton levelButton : levelButtons) {
            add(levelButton);
        }

        add(startButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);  // Increased window size
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    // Method to create level selection radio buttons
    private void createLevelButtons() {
        levelButtons = new JRadioButton[10];  // Assuming levels from 2 to 11

        ButtonGroup levelGroup = new ButtonGroup();

        for (int i = 0; i < 10; i++) {
            int level = i + 2;
            levelButtons[i] = new JRadioButton("Level " + level);
            levelButtons[i].setActionCommand(Integer.toString(level));
            levelGroup.add(levelButtons[i]);
        }
    }

    // Method to handle the start button click
    private void handleStartButtonClick() {
        // Retrieve selected options from UI components
        boolean isUnlimitedMode = unlimitedModeButton.isSelected();
        selectedShopLevel = getSelectedLevel();

        // Check if both game mode and level are selected
        if (!isGameModeSelected() || selectedShopLevel == -1) {
            JOptionPane.showMessageDialog(this, "Please select both game mode and level.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Do not proceed if selections are incomplete
        }

        // Hide the start menu
        setVisible(false);

        // If timed mode is selected, initiate countdown before starting the game
        if (timedModeButton.isSelected()) {
            TimerClass.performCountdown(3, () -> {
                // Countdown finished, start the game
                SwingUtilities.invokeLater(() -> new ShopApp(selectedShopLevel, isUnlimitedMode));
            });
        } else {
            // Start the game immediately for other modes
            SwingUtilities.invokeLater(() -> new ShopApp(selectedShopLevel, isUnlimitedMode));
        }
    }

    // Method to check if a game mode is selected
    private boolean isGameModeSelected() {
        return unlimitedModeButton.isSelected() || timedModeButton.isSelected();
    }

    // Method to get the selected level from radio buttons
    private int getSelectedLevel() {
        for (JRadioButton levelButton : levelButtons) {
            if (levelButton.isSelected()) {
                return Integer.parseInt(levelButton.getActionCommand());
            }
        }
        return -1;  // Return an invalid value if no level is selected
    }

    // Main method to launch the application
    public static void main(String[] args) {
        // Create an instance of the StartMenu
        SwingUtilities.invokeLater(() -> new StartMenu());
    }
}
