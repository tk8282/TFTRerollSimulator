package com.troy.shop;

public class LevelOdds {

    // 2D array that holds the odds of each cost for each level
    private static final double[][] odds = {
            {1.0, 0.0, 0.0, 0.0, 0.0},
            {0.75, 0.25, 0.0, 0.0, 0.0},
            {0.55, 0.30, 0.15, 0.0, 0.0},
            {0.45, 0.33, 0.20, 0.02, 0.0},
            {0.30, 0.40, 0.25, 0.05, 0.0},
            {0.19, 0.35, 0.35, 0.10, 0.01},
            {0.18, 0.25, 0.36, 0.18, 0.03},
            {0.10, 0.20, 0.25, 0.35, 0.10},
            {0.05, 0.10, 0.20, 0.40, 0.25},
            {0.01, 0.02, 0.12, 0.50, 0.35}
    };

    // Method to retrieve odds for a specific level and tier
    public static double getLevelOdds(int level, int tier) {
        // Checking that odds match the specified level and tier
        if (level >= 2 && level <= 11 && tier >= 1 && tier <= 5) {
            return odds[level - 2][tier - 1];
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid level or tier");
        }
    }
}
