# Simple-Monopoly-Game-Simulator

This is a **Java-based simulation of the Monopoly board game**, designed to run statistical analyses on square landings using two different player strategies. The program performs multiple simulation runs and records how often each square is landed on.

## Features
- Simulates **1,000 to 1,000,000 Monopoly moves**.
- Supports **two player strategies** to compare statistical outcomes.
- Tracks and prints:
  - Number of landings on each square
  - Percentage of total landings per square
- Includes mechanics for:
  - **Chance and Community Chest cards**
  - **Jail behavior and Get Out of Jail Free cards**
  - Dice rolling logic and doubles handling
  - Square actions like "Go to Jail"

## Structure
- `monopolyGame.java`: Main driver file containing all logic
  - `MonopolyBoard`: Represents the game board and square effects
  - `Player`: Represents a player with jail logic and card handling
  - `simulateGame(...)`: Simulates a full game session
  - `printStatistics(...)`: Prints landing stats for each square

## How to Run
1. Compile the program:
   ```bash
   javac monopolyGame.java
   ```
2. Run the simulation:
   ```bash
   java monopoly.monopolyGame
   ```
   *(Make sure your package name is set correctly if you're using folders.)*

## What You Can Learn From This
- How random simulations behave over large samples
- The probability distribution of landing on various Monopoly squares
- Basic use of **Java collections**, **OOP**, and **randomized simulations**

## Future Improvements
- Add money tracking and property buying
- Simulate more complex player decisions
- Visualize data using charts
- Extend card logic to include all official card effects

## License
This simulation is for educational purposes and not affiliated with the official Monopoly brand.

