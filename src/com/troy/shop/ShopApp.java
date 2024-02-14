package com.troy.shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShopApp {

    private JFrame frame;

    private JButton refreshButton;
    private JLabel streakIcon;
    private JLabel level;
    private JLabel goldCount;
    private JLabel levelOdds;
    private JLabel lockShop;
    private JLabel shopBackground;
    private JLabel levelUp;

    private JLabel unitPlaceholder;

    // Constructor for the ShopApp class
    public ShopApp() {
        // Initialize the JFrame
        frame = new JFrame("Game Shop");
        frame.setSize(1500, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);  // Using null layout for manual component placement

        // Create a JButton with the refresh icon
        ImageIcon refreshIcon = new ImageIcon("Assets/RefreshButton.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setBounds(50, 750, refreshIcon.getIconWidth(), refreshIcon.getIconHeight());
        frame.add(refreshButton);

        // Create JLabels with images and add them to the frame
        // Adjust positions and sizes based on image dimensions

        // Level Up icon
        ImageIcon levelUpImage = new ImageIcon("Assets/LevelUp.png");
        levelUp = new JLabel(levelUpImage);
        levelUp.setBounds(50, 670, levelUpImage.getIconWidth(), levelUpImage.getIconHeight());
        frame.add(levelUp);

        // Unit Placeholder icons (multiple)
        int numberOfPlaceholders = 5;
        int spacing = 8;
        for (int i = 0; i < numberOfPlaceholders; i++) {
            unitPlaceholder = new JLabel(new ImageIcon("Assets/UnitPlaceholder.png"));
            int xOffset = i * (unitPlaceholder.getIcon().getIconWidth() + spacing);
            unitPlaceholder.setBounds(255 + xOffset, 670, unitPlaceholder.getIcon().getIconWidth(), unitPlaceholder.getIcon().getIconHeight());
            frame.add(unitPlaceholder);
        }

        // Gold Count icon
        ImageIcon goldCountImage = new ImageIcon("Assets/GoldCount.png");
        goldCount = new JLabel(goldCountImage);
        goldCount.setBounds(570, 608, goldCountImage.getIconWidth(), goldCountImage.getIconHeight());
        frame.add(goldCount);

        // Level icon
        ImageIcon levelImage = new ImageIcon("Assets/Level.png");
        level = new JLabel(levelImage);
        level.setBounds(39, 616, levelImage.getIconWidth(), levelImage.getIconHeight());
        frame.add(level);

        // Streak Icon
        ImageIcon streakIconImage = new ImageIcon("Assets/StreakIcon.png");
        streakIcon = new JLabel(streakIconImage);
        streakIcon.setBounds(740, 615, streakIconImage.getIconWidth(), streakIconImage.getIconHeight());
        frame.add(streakIcon);

        // Level Odds icon
        ImageIcon levelOddsImage = new ImageIcon("Assets/LevelOdds.png");
        levelOdds = new JLabel(levelOddsImage);
        levelOdds.setBounds(200, 630, levelOddsImage.getIconWidth(), levelOddsImage.getIconHeight());
        frame.add(levelOdds);

        // Lock Shop icon
        ImageIcon lockShopImage = new ImageIcon("Assets/LockShop.png");
        lockShop = new JLabel(lockShopImage);
        lockShop.setBounds(1213, 620, lockShopImage.getIconWidth(), lockShopImage.getIconHeight());
        frame.add(lockShop);

        // Shop Background image
        ImageIcon shopBackgroundImage = new ImageIcon("Assets/ShopBackground.png");
        shopBackground = new JLabel(shopBackgroundImage);
        shopBackground.setBounds(38, 660, shopBackgroundImage.getIconWidth(), shopBackgroundImage.getIconHeight());
        frame.add(shopBackground);

        // Set the frame properties
        frame.setFocusable(true);
        frame.setVisible(true);

        // Add action listener for the refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the refresh logic here
                refreshShop();
            }
        });

        // Add key listener for the "d" key
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the "d" key is pressed
                if (e.getKeyChar() == 'd') {
                    // Handle the refresh logic here
                    refreshShop();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Empty implementation, not used in this example
            }
        });
    }

    // Method to handle the logic for refreshing the shop
    private void refreshShop() {
        // Display a message for simplicity (replace with actual logic)
        JOptionPane.showMessageDialog(frame, "Shop Refreshed!");
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShopApp());
    }
}
