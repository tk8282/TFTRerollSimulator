package com.troy.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// ShopApp class extending JFrame for the main shop application
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
    private List<String> boughtUnits;
    private List<Integer>[] unitCost;
    private int totalCostOfUnits;
    private UnitPool unitPool;
    private int totalCost;
    private int refreshCount;

    // Constructor for the ShopApp class
    public ShopApp(int selectedShopLevel, boolean isUnlimitedMode) {
        this.selectedShopLevel = selectedShopLevel;
        this.isUnlimitedMode = isUnlimitedMode;

        shopMechanic = new ShopMechanic(selectedShopLevel, isUnlimitedMode);
        unitPool = new UnitPool();

        boughtUnits = new ArrayList<>();
        totalCost = 0;
        refreshCount = 0;

        // Initialize the array to hold the cost of units
        unitCost = new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            unitCost[i] = new ArrayList<>();
        }

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

            // Create JLabels for unit names
            unitNameLabels[i] = new JLabel();
            add(unitNameLabels[i]);

            // Set an initial empty icon for tiers
            tierBorders[i] = new JLabel(new ImageIcon());
            add(tierBorders[i]);

            // Declare final variable for ActionListener
            final int index = i;

            // Add MouseListener to handle buy action
            //mousePressed tested to show faster response
            unitImages[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    buyUnit(index);
                }
            });
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

    // Method to handle the logic for buying a unit
    private void buyUnit(int index) {
        // Check if the unit is not already bought
        if (index >= 0 && index < 5 && unitImages[index].isVisible()) {
            String unitName = unitNameLabels[index].getText();

            // Retrieve the tier from the unitCost array
            List<Integer> tiers = unitCost[index];
            int tier = tiers.isEmpty() ? 0 : tiers.get(0);

            // Check if the unit is already in the boughtUnits list
            boolean alreadyBought = false;
            for (String boughtUnit : boughtUnits) {
                if (boughtUnit.startsWith(unitName)) {
                    alreadyBought = true;
                    // Update the existing entry by incrementing the count
                    int count = Integer.parseInt(boughtUnit.split(" x")[1]);
                    count++;
                    boughtUnits.remove(boughtUnit);
                    boughtUnits.add(unitName + " x" + count);
                    System.out.println("Updated bought unit: " + boughtUnits);
                    break;
                }
            }

            if (!alreadyBought) {
                // Add unit to the list of bought units
                boughtUnits.add(unitName + " x1");
                System.out.println("New bought unit: " + boughtUnits);
            }

            // Remove the unit from the UnitPool
            UnitPool.removeUnit(tier, unitName);

            // Add the unit cost from the total cost
            totalCostOfUnits += tier;

            // Print relevant information after removal
            System.out.println("Removed 1 " + unitName + " from tier " + tier + ". Remaining count: " + UnitPool.getRemainingCount(tier, unitName));
            System.out.println("New bought unit: " + boughtUnits);
            System.out.println("Total Cost of Units: " + totalCostOfUnits);

            // Hide the bought unit and related components
            unitImages[index].setVisible(false);
            tierBorders[index].setVisible(false);
            unitNameLabels[index].setVisible(false);

            // Update the bought unit's tier in the unitCost array
            tiers.clear();

            requestFocusInWindow();
        }
    }

    // Method to handle the logic for refreshing the shop
    private void refreshShop() {
        for (int i = 0; i < 5; i++) {
            shopMechanic.handleRefresh(selectedShopLevel, isUnlimitedMode);

            // Initialize variables for selected unit and tier
            String randomUnit;
            int tier;
            String randomTierFileName;

            do {
                randomTierFileName = shopMechanic.getRandomTierFileName();
                randomUnit = shopMechanic.getRandomUnitFileName();

                // Get the tier of the selected unit
                tier = shopMechanic.getTierNum();
            } while (!UnitPool.containsUnit(tier, randomUnit) || UnitPool.getRemainingCount(tier, randomUnit) == 0);

            // Example filenames:
            // "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Tier1.png"
            // "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Corki.png"
            String tierImagePath = "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\" + randomTierFileName + ".png";
            String unitImagePath = "C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Units\\" + randomUnit + ".png";

            // Update JLabels with new icons
            unitImages[i].setIcon(new ImageIcon(unitImagePath));
            unitNameLabels[i].setText(randomUnit);
            tierBorders[i].setIcon(new ImageIcon(tierImagePath));

            // Use the tier returned by shopMechanic for consistency
            int unitTier = shopMechanic.getTierNum();

            // Update the unitCost array with the new tier
            List<Integer> tiers = unitCost[i];
            tiers.clear();
            tiers.add(unitTier);

            tierBorders[i].setBounds(265 + i * 205, 670, tierBorders[i].getIcon().getIconWidth(), tierBorders[i].getIcon().getIconHeight());
            unitImages[i].setBounds(268 + i * 205, 674, 190, 110);

            unitNameLabels[i].setForeground(Color.BLACK);
            unitNameLabels[i].setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
            unitNameLabels[i].setBounds(330 + i * 205, 790, 100, 20); // Set position and size

            // Make the slots visible again
            unitImages[i].setVisible(true);
            tierBorders[i].setVisible(true);
            unitNameLabels[i].setVisible(true);

            // Tests to verify the tier and champion
            System.out.println(tierImagePath);
            System.out.println("Randomly selected Tier: " + randomTierFileName);

            System.out.println(unitImagePath);
            System.out.println("Randomly selected Unit: " + randomUnit);

            refreshCount++;
            requestFocusInWindow();
        }
    }
}
