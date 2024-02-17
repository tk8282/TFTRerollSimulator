package com.troy.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitPool {
    // Map to store unit pools for different tiers
    private static final Map<Integer, Map<String, Integer>> unitPools = new HashMap<>();

    // Static initialization block to populate unit pools
    static {
        // Initialize unit pools for each cost tier
        // 1 Cost units
        Map<String, Integer> oneCostUnits = new HashMap<>();
        oneCostUnits.put("Annie", 22);
        oneCostUnits.put("Corki", 22);
        oneCostUnits.put("Evelynn", 22);
        oneCostUnits.put("Jinx", 22);
        oneCostUnits.put("Kennen", 22);
        oneCostUnits.put("KSante", 22);
        oneCostUnits.put("Lillia", 22);
        oneCostUnits.put("Nami", 22);
        oneCostUnits.put("Olaf", 22);
        oneCostUnits.put("Tahm Kench", 22);
        oneCostUnits.put("Taric", 22);
        oneCostUnits.put("Vi", 22);
        oneCostUnits.put("Yasuo", 22);
        unitPools.put(1, oneCostUnits);

        // 2 Cost units
        Map<String, Integer> twoCostUnits = new HashMap<>();
        twoCostUnits.put("Aphelios", 20);
        twoCostUnits.put("Bard", 20);
        twoCostUnits.put("Garen", 20);
        twoCostUnits.put("Gnar", 20);
        twoCostUnits.put("Gragas", 20);
        twoCostUnits.put("Jax", 20);
        twoCostUnits.put("Kaisa", 20);
        twoCostUnits.put("Katarina", 20);
        twoCostUnits.put("Kayle", 20);
        twoCostUnits.put("Pantheon", 20);
        twoCostUnits.put("Senna", 20);
        twoCostUnits.put("Seraphine", 20);
        twoCostUnits.put("Twitch", 20);
        unitPools.put(2, twoCostUnits);

        // 3 Cost units
        Map<String, Integer> threeCostUnits = new HashMap<>();
        threeCostUnits.put("Amumu", 17);
        threeCostUnits.put("Ekko", 17);
        threeCostUnits.put("Lulu", 17);
        threeCostUnits.put("Lux", 17);
        threeCostUnits.put("Miss Fortune", 17);
        threeCostUnits.put("Mordekaiser", 17);
        threeCostUnits.put("Neeko", 17);
        threeCostUnits.put("Riven", 17);
        threeCostUnits.put("Samira", 17);
        threeCostUnits.put("Sett", 17);
        threeCostUnits.put("Urgot", 17);
        threeCostUnits.put("Vex", 17);
        threeCostUnits.put("Yone", 17);
        unitPools.put(3, threeCostUnits);

        // 4 Cost units
        Map<String, Integer> fourCostUnits = new HashMap<>();
        fourCostUnits.put("Ahri", 10);
        fourCostUnits.put("Akali", 10);
        fourCostUnits.put("Blitzcrank", 10);
        fourCostUnits.put("Caitlyn", 10);
        fourCostUnits.put("Ezreal", 10);
        fourCostUnits.put("Karthus", 10);
        fourCostUnits.put("Poppy", 10);
        fourCostUnits.put("Thresh", 10);
        fourCostUnits.put("Twisted Fate", 10);
        fourCostUnits.put("Viego", 10);
        fourCostUnits.put("Zac", 10);
        fourCostUnits.put("Zed", 10);
        unitPools.put(4, fourCostUnits);

        // 5 Cost units
        Map<String, Integer> fiveCostUnits = new HashMap<>();
        fiveCostUnits.put("Illaoi", 9);
        fiveCostUnits.put("Jhin", 9);
        fiveCostUnits.put("Kayn", 9);
        fiveCostUnits.put("Lucian", 9);
        fiveCostUnits.put("Qiyana", 9);
        fiveCostUnits.put("Sona", 9);
        fiveCostUnits.put("Yorick", 9);
        fiveCostUnits.put("Ziggs", 9);
        unitPools.put(5, fiveCostUnits);
    }

    // Method to get a random unit based upon the given tier
    public static String getRandomUnit(int tier) {
        // Retrieve the unit pool for the specified tier
        Map<String, Integer> units = unitPools.get(tier);

        if (units != null && !units.isEmpty()) {
            // Filter out units with 0 count in the pool
            List<String> availableUnits = units.entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            while (!availableUnits.isEmpty()) {
                // Randomly select a unit from the filtered pool
                int randomIndex = (int) (Math.random() * availableUnits.size());
                String selectedUnit = availableUnits.get(randomIndex);

                return selectedUnit;
            }

            // If no available units found in the pool, attempt to find a different unit of the same tier
            List<String> allUnits = unitPools.entrySet().stream()
                    .filter(entry -> entry.getKey() == tier)
                    .flatMap(entry -> entry.getValue().keySet().stream())
                    .collect(Collectors.toList());

            List<String> availableUnitsInSameTier = allUnits.stream()
                    .filter(unit -> unitPools.get(tier).get(unit) > 0)
                    .collect(Collectors.toList());

            if (!availableUnitsInSameTier.isEmpty()) {
                int randomIndex = (int) (Math.random() * availableUnitsInSameTier.size());
                String selectedUnit = availableUnitsInSameTier.get(randomIndex);

                // Decrement the count in the pool
                unitPools.get(tier).put(selectedUnit, unitPools.get(tier).get(selectedUnit) - 1);

                return selectedUnit;
            }
        }

        // Handle the case when all units are depleted or tier is invalid
        return "NoUnit";
    }

    // Check if the unit pool contains the specified unit for the given tier
    public static boolean containsUnit(int tier, String unit) {
        Map<String, Integer> units = unitPools.get(tier);
        return units != null && units.containsKey(unit) && units.get(unit) > 0;
    }


    // Method to remove the unit that is bought
    public static void removeUnit(int tier, String unit) {
        // Subtract one from the unit count in the pool
        Map<String, Integer> units = unitPools.get(tier);
        if (units != null && units.containsKey(unit)) {
            int count = units.get(unit);
            if (count > 0) {
                units.put(unit, count - 1);
            } else {
                // Handle the case where the count becomes negative
                System.out.println("Error: Attempted to remove more units than available for " + unit);
            }
        }
    }

    // New method to get the remaining count of a unit in a specific tier
    public static int getRemainingCount(int tier, String unit) {
        // Retrieve the unit pool for the specified tier
        Map<String, Integer> units = unitPools.get(tier);

        if (units != null && units.containsKey(unit)) {
            // Return the remaining count of the unit
            return units.get(unit);
        } else {
            // Handle the case when the pool is empty or tier is invalid
            return 0;
        }
    }



}
