# TFT Shop Emulator 
## Overview
This Java application simulates the Team Fight Tactics (TFT) shop. The user can choose between two gamemodes: unlimited and timed mode (30 seconds). The unit data is updated with the current set (Set 10) and has accurate odds and unit pools. At the end of the session, a results screen is displayed with player's stats.

## Prerequisites
Before Running the simulator, make sure you have the necessary assets from the 'TFTRerollSimualtorAssets' repository.
* Ensure wherever your assets file is located, that you update the program with the correct file path for your machine. 
  
	The assets folder should be formatted like this: 
	* TFTRerollSimulatorAssets/
   		- 1CostBorder.png
   		- 2CostBorder.png
   		- 3CostBorder.png
   		- ... (Other image files)
   		- **Units**
  			- (Unit image files)
		- **Sound Effects**
    		- (BuyUnit and Reroll .wav files)
## Files
1. ShopApp.java: Main class for the shopping simulation. Handles the core logic of buying units, refreshing the shop, and displaying the visuals.

2. StartMenu.java: GUI class representing the initial menu where users choose game mode, level, and start the simulation.

3. ShopMechanic.java: Class responsible for shop mechanics, including unit tier selection and random unit generation based on level odds.

4. ResultsWindow.java: GUI class for displaying the results of the simulator, including bought units, total cost, and reroll count.

5. TimerClass.java: Class for handling countdowns used in the start menu and timed mode.

6. UnitPool.java: Class storing information about unit names and the number of copies available for each unit.

7. LevelOdds.java: Class containing the unit odds for each level.

## How to Run
1. Compile and Run 'StartMenu.java'
   - Ensure you have Java installed on your system
   - Open terminal or command prompt and navigate to directory that contains: 'StartMenu.java'
   - Compile and run the file using:
   		- javac Startmenu.java
   		- java StartMenu

## Notes
* The application uses Swing for the graphical interface.
* Unit odds are based on the selected level at the Start Menu
* Results are displayed after the simulation
  * In Unlimited mode clicking the "Finish" button or hitting "p" will end the game and bring up the results window
  * In Timed mode the game will close immediately after 30 seconds and bring up the results window
