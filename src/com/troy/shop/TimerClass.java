package com.troy.shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TimerClass used to perform countdowns for the start of the game and timer for the "Timed" gamemode
public class TimerClass {
    private static Timer timer;

    // Method to perform a countdown with the specified duration
    // Executes the onFinish action when the countdown ends
    public static void performCountdown(int durationSeconds, Runnable onFinish) {
        // Stop the existing timer if running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Create a new timer with a 1-second delay
        timer = new Timer(1000, new ActionListener() {
            int remainingSeconds = durationSeconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there are remaining seconds
                if (remainingSeconds > 0) {
                    System.out.println("Countdown: " + remainingSeconds);
                    remainingSeconds--;
                } else {
                    // Stop the timer when the countdown reaches 0
                    timer.stop();
                    onFinish.run();  // Execute the onFinish action
                }
            }
        });

        // Start the timer
        timer.start();
    }
}
