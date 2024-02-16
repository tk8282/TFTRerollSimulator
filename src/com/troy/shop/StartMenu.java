package com.troy.shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        unlimitedModeButton = new JRadioButton("Unlimited Mode");
        timedModeButton = new JRadioButton("Timed Mode");

        modeGroup = new ButtonGroup();
        modeGroup.add(unlimitedModeButton);
        modeGroup.add(timedModeButton);

        JLabel modeLabel = new JLabel("Select Mode:");
        createLevelButtons();

        JLabel levelLabel = new JLabel("Select Level:");

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

        // Hide the start menu
        setVisible(false);

        // If timed mode is selected, initiate countdown before starting the game
        if (timedModeButton.isSelected()) {
            performCountdown(() -> {
                // Countdown finished, start the game
                SwingUtilities.invokeLater(() -> new ShopApp(selectedShopLevel, isUnlimitedMode));
            });
        } else {
            // Start the game immediately for other modes
            SwingUtilities.invokeLater(() -> new ShopApp(selectedShopLevel, isUnlimitedMode));
        }
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

    // Method to perform a countdown before starting the game
    private void performCountdown(Runnable onFinish) {
        int countdownSeconds = 3;

        // Create a timer to decrement the countdown
        Timer timer = new Timer(1000, new ActionListener() {
            int remainingSeconds = countdownSeconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    System.out.println("Countdown: " + remainingSeconds);
                    remainingSeconds--;
                } else {
                    // Stop the timer
                    ((Timer) e.getSource()).stop();
                    onFinish.run();  // Execute the onFinish action
                }
            }
        });

        // Start the timer
        timer.start();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        // Create an instance of the StartMenu
        SwingUtilities.invokeLater(() -> new StartMenu());
    }
}
