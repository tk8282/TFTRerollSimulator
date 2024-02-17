package com.troy.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShopMechanic {

    private int selectedShopLevel;
    private boolean isUnlimitedMode;
    private String randomTierFileName;
    private String randomUnit;

    private int randomTier;

    // Constructor to initialize ShopMechanic with selectedShopLevel and isUnlimitedMode
    public ShopMechanic(int selectedShopLevel, boolean isUnlimitedMode) {
        this.selectedShopLevel = selectedShopLevel;
        this.isUnlimitedMode = isUnlimitedMode;
    }

    // Method to handle the refresh logic
    public void handleRefresh(int selectedShopLevel, boolean isUnlimitedMode) {
        // Create a new instance of ShopMechanic for refresh
        ShopMechanic shopMechanic = new ShopMechanic(selectedShopLevel, isUnlimitedMode);

        // Get level odds for the selected shop level
        Map<Integer, Map<Integer, Double>> levelOdds = shopMechanic.getLevelOdds();

        // Select a random tier based on the odds
        randomTier = shopMechanic.getRandomTierBasedOnOdds(levelOdds);

        // Convert the random tier to a corresponding file name
        randomTierFileName = shopMechanic.convertTierToFileName(randomTier);

        // Use the selected tier to proceed with random unit selection
        randomUnit = shopMechanic.getRandomUnit(randomTier);
    }

    // Method to get level odds for each tier
    private Map<Integer, Map<Integer, Double>> getLevelOdds() {
        Map<Integer, Map<Integer, Double>> levelOdds = new HashMap<>();

        for (int tier = 1; tier <= 5; tier++) {
            Map<Integer, Double> oddsForTier = new HashMap<>();
            double odds = LevelOdds.getLevelOdds(selectedShopLevel, tier);
            oddsForTier.put(tier, odds);

            // Add the odds map to the main map
            levelOdds.put(tier, oddsForTier);
        }

        return levelOdds;
    }

    // Method to select a random tier based on odds
    public int getRandomTierBasedOnOdds(Map<Integer, Map<Integer, Double>> levelOdds) {
        Random random = new Random();

        // Generate a random number between 0 and 100
        double randomNumber = random.nextDouble() * 100;

        // Default to the lowest tier
        int selectedTier = 1;
        double cumulativePercentage = 0.0;

        // Iterate through tiers and check if the random number falls within the percentage range
        for (Map.Entry<Integer, Map<Integer, Double>> entry : levelOdds.entrySet()) {
            int tier = entry.getKey();
            Map<Integer, Double> oddsForTier = entry.getValue();
            double percentage = oddsForTier.getOrDefault(tier, 0.0) * 100;

            // Stop once a tier is selected
            if (randomNumber <= cumulativePercentage + percentage) {
                selectedTier = tier;
                break;
            }

            cumulativePercentage += percentage;
        }

        return selectedTier;
    }

    // Method to get a random tier
    public int getRandomTier() {
        Map<Integer, Map<Integer, Double>> levelOdds = getLevelOdds();
        return getRandomTierBasedOnOdds(levelOdds);
    }

    // Method to get a random unit for a given tier
    public String getRandomUnit(int tier) {
        if (isValidTier(tier)) {
            return UnitPool.getRandomUnit(tier);
        } else {
            System.out.println("Invalid tier selected: " + tier);
            return "NoUnit";
        }
    }

    // Method to retrieve the randomly selected tier's file name
    public String getRandomTierFileName() {
        return randomTierFileName;
    }

    // Method to retrieve the randomly selected unit's file name
    public String getRandomUnitFileName() {
        return randomUnit;
    }

    public int getTierNum() {
        return randomTier;
    }

    // Method to check if a given tier is valid
    private boolean isValidTier(int tier) {
        return tier >= 1 && tier <= 5;
    }

    // Method to convert a tier to its corresponding file name
    private String convertTierToFileName(int tier) {
        switch (tier) {
            case 1:
                return "1CostBorder";
            case 2:
                return "2CostBorder";
            case 3:
                return "3CostBorder";
            case 4:
                return "4CostBorder";
            case 5:
                return "5CostBorder";
            default:
                return "InvalidTier";
        }
    }
}
