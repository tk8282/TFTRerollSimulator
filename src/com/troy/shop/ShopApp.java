package com.troy.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShopApp extends JFrame {

    private ShopMechanic shopMechanic;
    private JButton refreshButton;
    private JLabel streakIcon;
    private JLabel level;
    private JLabel goldCount;
    private JLabel levelOdds;
    private JLabel lockShop;
    private JLabel shopBackground;
    private JLabel levelUp;
    private JLabel unitPlaceholder;
    private JLabel[] tierBorders;
    private JLabel[] unitImages;
    private JLabel[] unitNameLabels;
    private int selectedShopLevel;
    private boolean isUnlimitedMode;

    // Constructor for the ShopApp class
    public ShopApp(int selectedShopLevel, boolean isUnlimitedMode) {
        this.selectedShopLevel = selectedShopLevel;
        this.isUnlimitedMode = isUnlimitedMode;

        shopMechanic = new ShopMechanic(selectedShopLevel, isUnlimitedMode);

        // Initialize the JFrame
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Using null layout for manual component placement
        setLayout(null);

        // Center the frame on the monitor
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);

        // Create arrays for JLabels
        tierBorders = new JLabel[5];
        unitImages = new JLabel[5];
        unitNameLabels = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            // Set an initial empty icon for units
            unitImages[i] = new JLabel(new ImageIcon());
            add(unitImages[i]);

            // Create JLabels for unit name
            unitNameLabels[i] = new JLabel();
            add(unitNameLabels[i]);

            // Set an initial empty icon for tiers
            tierBorders[i] = new JLabel(new ImageIcon());
            add(tierBorders[i]);
        }

        // Create a JButton with the refresh icon
        ImageIcon refreshIcon = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\RefreshButton.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setBounds(50, 750, refreshIcon.getIconWidth(), refreshIcon.getIconHeight());
        add(refreshButton);

        // Create JLabels with images and add them to the frame
        // Adjust positions and sizes based on image dimensions

        // Level Up icon
        ImageIcon levelUpImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LevelUp.png");
        levelUp = new JLabel(levelUpImage);
        levelUp.setBounds(50, 670, levelUpImage.getIconWidth(), levelUpImage.getIconHeight());
        add(levelUp);

        // Unit Placeholder icons (multiple)
        int numberOfPlaceholders = 5;
        int spacing = 8;
        for (int x = 0; x < numberOfPlaceholders; x++) {
            unitPlaceholder = new JLabel(new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\UnitPlaceholder.png"));
            int xOffset = x * (unitPlaceholder.getIcon().getIconWidth() + spacing);
            unitPlaceholder.setBounds(270 + xOffset, 675, unitPlaceholder.getIcon().getIconWidth(), unitPlaceholder.getIcon().getIconHeight());
            add(unitPlaceholder);
        }

        // Gold Count icon
        ImageIcon goldCountImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets/\\GoldCount.png");
        goldCount = new JLabel(goldCountImage);
        goldCount.setBounds(570, 608, goldCountImage.getIconWidth(), goldCountImage.getIconHeight());
        add(goldCount);

        // Streak Icon
        ImageIcon streakIconImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\StreakIcon.png");
        streakIcon = new JLabel(streakIconImage);
        streakIcon.setBounds(740, 615, streakIconImage.getIconWidth(), streakIconImage.getIconHeight());
        add(streakIcon);

        // Lock Shop icon
        ImageIcon lockShopImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LockShop.png");
        lockShop = new JLabel(lockShopImage);
        lockShop.setBounds(1210, 619, lockShopImage.getIconWidth(), lockShopImage.getIconHeight());
        add(lockShop);

        // Shop Background image
        ImageIcon shopBackgroundImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\ShopBackground.png");
        shopBackground = new JLabel(shopBackgroundImage);
        shopBackground.setBounds(38, 660, shopBackgroundImage.getIconWidth(), shopBackgroundImage.getIconHeight());
        add(shopBackground);

        // Level icon
        ImageIcon levelImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Level.png");
        level = new JLabel(levelImage);
        level.setBounds(33, 610, levelImage.getIconWidth(), levelImage.getIconHeight());
        add(level);

        // Level Odds icon
        ImageIcon levelOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LevelOdds.png");
        levelOdds = new JLabel(levelOddsImage);
        levelOdds.setBounds(170, 630, levelOddsImage.getIconWidth(), levelOddsImage.getIconHeight());
        add(levelOdds);

        // Set the frame properties
        setFocusable(true);
        setVisible(true);

        // Action listener for the clicking of the refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshShop();
            }
        });

        // Add key listener for the "d" key
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the "d" key is pressed
                if (e.getKeyChar() == 'd') {
                    // Handle the refresh
                    refreshShop();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        });
        refreshShop();
    }

    // Method to handle the logic for refreshing the shop
    private void refreshShop() {
        for (int i = 0; i < 5; i++) {
            shopMechanic.handleRefresh(selectedShopLevel, isUnlimitedMode);

            String randomTierFileName = shopMechanic.getRandomTierFileName();
            String randomUnit = shopMechanic.getRandomUnitFileName();

            // Example filenames:
            // "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Tier1.png"
            // "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Corki.png"
            String tierImagePath = "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\" + randomTierFileName + ".png";
            String unitImagePath = "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Units\\" + randomUnit + ".png";

            // Update JLabels with new icons
            unitImages[i].setIcon(new ImageIcon(unitImagePath));
            unitNameLabels[i].setText(randomUnit);
            tierBorders[i].setIcon(new ImageIcon(tierImagePath));

            tierBorders[i].setBounds(265 + i * 205, 670, tierBorders[i].getIcon().getIconWidth(), tierBorders[i].getIcon().getIconHeight());
            unitImages[i].setBounds(268 + i * 205, 674, 190, 110);

            unitNameLabels[i].setForeground(Color.BLACK);
            unitNameLabels[i].setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
            unitNameLabels[i].setBounds(330 + i * 205, 790, 100, 20); // Set position and size

            // Tests to verify the tier and champion
            System.out.println(tierImagePath);
            System.out.println("Randomly selected Tier: " + randomTierFileName);

            System.out.println(unitImagePath);
            System.out.println("Randomly selected Unit: " + randomUnit);

            requestFocusInWindow();
        }
    }
}
