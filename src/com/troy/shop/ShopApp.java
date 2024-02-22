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
import java.awt.Color;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;


// ShopApp class displays the visuals for the game
public class ShopApp extends JFrame {
    // Class objects
    private ShopMechanic shopMechanic;
    private UnitPool unitPool;
    private Timer timedModeTimer;

    // Instantiate buttons displayed in the game
    private JButton finishButton;
    private JButton refreshButton;

    // Images and labels placed into the game display
    private JLabel streakIcon;
    private JLabel level;
    private JLabel goldCount;
    private JLabel levelOdds;
    private JLabel lockShop;
    private JLabel levelUp;
    private JLabel levelLabel;
    private JLabel unitPlaceholder;
    private JLabel shopBackground;
    private JLabel BackgroundStage;
    private JLabel OneCostUnitOdds;
    private JLabel TwoCostUnitOdds;
    private JLabel ThreeCostUnitOdds;
    private JLabel FourCostUnitOdds;
    private JLabel FiveCostUnitOdds;
    private JLabel oneCostOddsLabel;
    private JLabel twoCostOddsLabel;
    private JLabel threeCostOddsLabel;
    private JLabel fourCostOddsLabel;
    private JLabel fiveCostOddsLabel;
    private JLabel[] tierBorders;
    private JLabel[] unitImages;
    private JLabel[] unitNameLabels;

    // Lists that store the information of the current units held in the shop
    private List<String> boughtUnits;
    private List<Integer>[] unitCost;

    // Boolean to check what mode the user selected
    private boolean isUnlimitedMode;

    // Variables used for the results window and set "Timed" mode limit to 30 seconds
    private int selectedShopLevel;
    private int totalCostOfUnits;
    private int totalCost;
    private int refreshCount;
    private int timedModeDuration = 30;

    // Constructor for the ShopApp class
    public ShopApp(int selectedShopLevel, boolean isUnlimitedMode) {

        // Add the following check to start the countdown only in timed mode
        if (!isUnlimitedMode) {
            TimerClass.performCountdown(timedModeDuration, this::handleTimedModeEnd);
        }

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

            // Create JLabels for unit names
            unitNameLabels[i] = new JLabel();
            add(unitNameLabels[i]);

            // Set an initial empty icon for tiers
            tierBorders[i] = new JLabel(new ImageIcon());
            add(tierBorders[i]);

            // Set an initial empty icon for units
            unitImages[i] = new JLabel(new ImageIcon());
            add(unitImages[i]);

            // Declare final variable for ActionListener
            final int index = i;

            // Add MouseListener to handle buy action
            // mousePressed tested to show faster response
            unitImages[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    buyUnit(index);
                }
            });
        }

        // Add finish button for unlimited mode
        if (isUnlimitedMode) {
            finishButton = new JButton("Finish");
            finishButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleFinishButtonClick();
                }
            });
            finishButton.setBounds(700, 8, 100, 50);
            add(finishButton);
        }

        // Create a JButton with the refresh icon
        // Ensure file path is correct
        ImageIcon refreshIcon = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\RefreshButton.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setBounds(50, 750, refreshIcon.getIconWidth(), refreshIcon.getIconHeight());
        add(refreshButton);

        // Create JLabels with images and add them to the frame
        // Adjust positions and sizes based on image dimensions

        // Level Up icon
        // Ensure file path is correct
        ImageIcon levelUpImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LevelUp.png");
        levelUp = new JLabel(levelUpImage);
        levelUp.setBounds(50, 670, levelUpImage.getIconWidth(), levelUpImage.getIconHeight());
        add(levelUp);

        // Unit Placeholder icons (multiple)
        int numberOfPlaceholders = 5;
        int spacing = 8;
        for (int x = 0; x < numberOfPlaceholders; x++) {
            // Ensure file path is correct
            unitPlaceholder = new JLabel(new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\UnitPlaceholder.png"));
            int xOffset = x * (unitPlaceholder.getIcon().getIconWidth() + spacing);
            unitPlaceholder.setBounds(270 + xOffset, 675, unitPlaceholder.getIcon().getIconWidth(), unitPlaceholder.getIcon().getIconHeight());
            add(unitPlaceholder);
        }

        // Gold Count icon
        // Ensure file path is correct
        ImageIcon goldCountImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets/\\GoldCount.png");
        goldCount = new JLabel(goldCountImage);
        goldCount.setBounds(570, 608, goldCountImage.getIconWidth(), goldCountImage.getIconHeight());
        add(goldCount);

        // Streak Icon
        // Ensure file path is correct
        ImageIcon streakIconImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\StreakIcon.png");
        streakIcon = new JLabel(streakIconImage);
        streakIcon.setBounds(740, 615, streakIconImage.getIconWidth(), streakIconImage.getIconHeight());
        add(streakIcon);

        // Lock Shop icon
        // Ensure file path is correct
        ImageIcon lockShopImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LockShop.png");
        lockShop = new JLabel(lockShopImage);
        lockShop.setBounds(1210, 619, lockShopImage.getIconWidth(), lockShopImage.getIconHeight());
        add(lockShop);

        // One Cost odds icon
        // Ensure file path is correct
        ImageIcon OneCostOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\1CostOdds.png");
        OneCostUnitOdds = new JLabel(OneCostOddsImage);
        OneCostUnitOdds.setBounds(260, 635, OneCostOddsImage.getIconWidth(), OneCostOddsImage.getIconHeight());
        add(OneCostUnitOdds);

        // One Cost odds icon
        // Ensure file path is correct
        ImageIcon TwoCostOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\2CostOdds.png");
        TwoCostUnitOdds = new JLabel(TwoCostOddsImage);
        TwoCostUnitOdds.setBounds(315, 638, TwoCostOddsImage.getIconWidth(), TwoCostOddsImage.getIconHeight());
        add(TwoCostUnitOdds);

        // One Cost odds icon
        // Ensure file path is correct
        ImageIcon ThreeCostOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\3CostOdds.png");
        ThreeCostUnitOdds = new JLabel(ThreeCostOddsImage);
        ThreeCostUnitOdds.setBounds(365, 637, ThreeCostOddsImage.getIconWidth(), ThreeCostOddsImage.getIconHeight());
        add(ThreeCostUnitOdds);

        // One Cost odds icon
        // Ensure file path is correct
        ImageIcon FourCostOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\4CostOdds.png");
        FourCostUnitOdds = new JLabel(FourCostOddsImage);
        FourCostUnitOdds.setBounds(415, 637, FourCostOddsImage.getIconWidth(), FourCostOddsImage.getIconHeight());
        add(FourCostUnitOdds);

        // One Cost odds icon
        // Ensure file path is correct
        ImageIcon FiveCostOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\5CostOdds.png");
        FiveCostUnitOdds = new JLabel(FiveCostOddsImage);
        FiveCostUnitOdds.setBounds(465, 636, FiveCostOddsImage.getIconWidth(), FiveCostOddsImage.getIconHeight());
        add(FiveCostUnitOdds);

        // Create JLabel for level
        levelLabel = new JLabel("Level: " + selectedShopLevel);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        levelLabel.setBounds(90, 630, 100, 20);
        add(levelLabel);

        // Calculate and set initial odds based on user input
        int[] odds = calculateOdds(selectedShopLevel);
        oneCostOddsLabel = setupOddsLabel(odds[0] + "%", 280, 635, Color.WHITE);
        twoCostOddsLabel = setupOddsLabel(odds[1] + "%", 335, 635, Color.GREEN);
        threeCostOddsLabel = setupOddsLabel(odds[2] + "%", 385, 635, Color.BLUE);
        fourCostOddsLabel = setupOddsLabel(odds[3] + "%", 435, 635, Color.MAGENTA); // Purple
        fiveCostOddsLabel = setupOddsLabel(odds[4] + "%", 485, 635, Color.YELLOW);

        // Shop Background image
        // Ensure file path is correct
        ImageIcon shopBackgroundImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\ShopBackground.png");
        shopBackground = new JLabel(shopBackgroundImage);
        shopBackground.setBounds(38, 660, shopBackgroundImage.getIconWidth(), shopBackgroundImage.getIconHeight());
        add(shopBackground);

        // Level icon
        // Ensure file path is correct
        ImageIcon levelImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\Level.png");
        level = new JLabel(levelImage);
        level.setBounds(33, 610, levelImage.getIconWidth(), levelImage.getIconHeight());
        add(level);

        // Level Odds icon
        // Ensure file path is correct
        ImageIcon levelOddsImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\LevelOdds.png");
        levelOdds = new JLabel(levelOddsImage);
        levelOdds.setBounds(170, 630, levelOddsImage.getIconWidth(), levelOddsImage.getIconHeight());
        add(levelOdds);

        //Background stage
        // Ensure file path is correct
        ImageIcon StageImage = new ImageIcon("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\BackgroundStage.png");
        BackgroundStage = new JLabel(StageImage);
        BackgroundStage.setBounds(0, 0, StageImage.getIconWidth(), StageImage.getIconHeight() +15);
        add(BackgroundStage);

        // Set the frame properties
        setFocusable(true);
        setVisible(true);

        // Add key listener for the "P" key (finish button) in unlimited mode
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the "f" key is pressed in unlimited mode
                if (isUnlimitedMode && e.getKeyChar() == 'p') {
                    handleFinishButtonClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        });

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
        // Call refreshShop at beginning to populate shop
        refreshShop();
    }

    private void performCountdown(Runnable onFinish) {
        if (!isUnlimitedMode) {
            // If not in unlimited mode, do not start the countdown
            return;
        }
        timedModeTimer = new Timer(1000, new ActionListener() {
            int remainingSeconds = timedModeDuration;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                } else {
                    // Stop the timer
                    timedModeTimer.stop();
                    onFinish.run();  // Execute the onFinish action
                }
            }
        });

        // Set the timer to stop after the specified duration
        timedModeTimer.setInitialDelay(0); // Set to 0 to start immediately

        // Start the timer
        timedModeTimer.start();
    }

    // Method to take the SelectedShopLevel from levelOdds and convert to percentages
    private int[] calculateOdds(int selectedShopLevel) {
        int[] odds = new int[5];

        for (int i = 0; i < 5; i++) {
            odds[i] = (int) (LevelOdds.getLevelOdds(selectedShopLevel, i + 1) * 100);
        }

        return odds;
    }

    // Method to set up and place Unit names in the game
    private JLabel setupOddsLabel(String text, int x, int y, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBounds(x, y, 50, 20);
        add(label);
        return label;
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
                    break;
                }
            }

            if (!alreadyBought) {
                // Add unit to the list of bought units
                boughtUnits.add(unitName + " x1");
            }

            // Remove the unit from the UnitPool
            UnitPool.removeUnit(tier, unitName);

            // Add the unit cost from the total cost
            totalCostOfUnits += tier;

            // Hide the bought unit and related components
            unitImages[index].setVisible(false);
            tierBorders[index].setVisible(false);
            unitNameLabels[index].setVisible(false);

            //play Sound effect
            buyUnitSound();

            // Update the bought unit's tier in the unitCost array
            tiers.clear();

            requestFocusInWindow();
        }
    }

    // Method to handle the logic for refreshing the shop
    private void refreshShop() {
        // Add one to refresh count
        refreshCount++;

        //play sound effect
        refreshSound();

        // Refresh for each slot in the shop (5)
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

            // Ensure file path is correct
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

            requestFocusInWindow();
        }
    }

    // Method that plays sound effects for refresh shop
    private void refreshSound() {
        // Ensure file path is correct
        playSound("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\SoundEffects\\RerollSound.wav");
    }

    // Method that plays sound effects for when a unit is bought
    private void buyUnitSound() {
        // Ensure file path is correct
        playSound("C:\\Users\\Troy\\Pictures\\TFTShopAssets\\SoundEffects\\BuyUnitSound.wav");
    }

    // Method to play a sound from the specified file path
    private void playSound(String filePath) {
        try {
            // Use the classloader to load the audio file
            InputStream audioFile = getClass().getClassLoader().getResourceAsStream(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method that runs when Timed mode ends
    private void handleTimedModeEnd() {
        System.out.println("Timed Mode Ended");

        // Open the results window
        openResultsWindow();

        System.out.println("Results Window Opened");
    }


    // Method to handle the finish button click
    private void handleFinishButtonClick() {
        // Stop the countdown timer if it's running
        if (timedModeTimer != null && timedModeTimer.isRunning()) {
            timedModeTimer.stop();
        }

        // Implement actions to perform when the finish button is clicked in unlimited mode
        System.out.println("Finish Button Clicked");

        // Open the results window
        openResultsWindow();
    }

    // Method to open the results window
    private void openResultsWindow() {
        ResultsWindow resultsWindow = new ResultsWindow(boughtUnits, totalCostOfUnits, refreshCount);
        resultsWindow.setVisible(true);

        // Close the current shop window
        dispose();
    }
}
