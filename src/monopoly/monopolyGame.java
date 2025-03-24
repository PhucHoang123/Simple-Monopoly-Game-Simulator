package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class monopolyGame {
    private static final int NUMBER_OF_MOVES = 1000000;
    private static final int NUMBER_OF_SIDES_ON_DICE = 6;
    
    private static final int CHANCE_CARD = 1;
    private static final int COMMUNITY_CHEST_CARD = 2;
    
    private static List<String> chanceCards;
    private static List<String> communityChestCards;

    static {
        // Initialize chance cards
        chanceCards = new ArrayList<>();
        chanceCards.add("Advance to Boardwalk");
        chanceCards.add("Advance to Go (Collect $200)");
        // Add other chance cards...

        // Initialize community chest cards
        communityChestCards = new ArrayList<>();
        communityChestCards.add("Advance to Go (Collect $200)");
        communityChestCards.add("Bank error in your favor. Collect $200");
        // Add other community chest cards...

        // Shuffle the card lists
        Collections.shuffle(chanceCards);
        Collections.shuffle(communityChestCards);
    }
    
    private static class MonopolyBoard {
        
     // Constants for board squares
        
        private static final int GO = 0;
        private static final int COMMUNITY_CHEST_1 = 2;
      
        private static final int CHANCE_1 = 7;
        private static final int COMMUNITY_CHEST_2 = 17;
        private static final int CHANCE_2 = 22;
        private static final int GO_TO_JAIL = 30;
        private static final int COMMUNITY_CHEST_3 = 33;
        private static final int CHANCE_3 = 36;
      

        // Other board squares
        // ...

        // Add more constants for the other board squares
        

        
        private Map<Integer, Integer> squaresLandedOn;

        public MonopolyBoard() {
        	
            squaresLandedOn = new HashMap<>();
            // Initialize the squares with zero landings
            for (int i = 0; i < 40; i++) {
                squaresLandedOn.put(i, 0);
            }
            
        }

        public void recordLanding(int square) {
            squaresLandedOn.put(square, squaresLandedOn.get(square) + 1);
        }

        private static void performSquareAction(Player player, int square) {
            switch (square) {
                case GO:
                    break;
                case COMMUNITY_CHEST_1:
                case COMMUNITY_CHEST_2:
                case COMMUNITY_CHEST_3:
                    drawCard(player, COMMUNITY_CHEST_CARD);
                    break;
                case CHANCE_1:
                case CHANCE_2:
                case CHANCE_3:
                    drawCard(player, CHANCE_CARD);
                    break;
                case MonopolyBoard.GO_TO_JAIL:
                    goToJail(player);
                    break;
                // Implement other square actions...
            }
        }

        private static void drawCard(Player player, int cardType) {
            String card;
            if (cardType == CHANCE_CARD) {
                card = chanceCards.remove(0); // Draw the top card from the chance cards
                chanceCards.add(card); // Move the drawn card to the bottom of the deck
            } else {
                card = communityChestCards.remove(0); // Draw the top card from the community chest cards
                communityChestCards.add(card); // Move the drawn card to the bottom of the deck
            }

            // Perform action based on the card drawn
            // Update player's attributes or position accordingly
            // You can implement a switch or if-else block here to handle each card individually.
            // For example:
            if (card.equals("Get Out of Jail Free")) {
                player.useGetOutOfJailFreeCard();
            } else if (card.equals("Go to Jail. Go directly to Jail, do not pass Go, do not collect $200")) {
                goToJail(player);
            } else if (card.equals("Advance to Go (Collect $200)")) {
                player.position = MonopolyBoard.GO;
                // Handle the case where the player passes Go and collects $200
            } else {
                // Handle other cards...
            }
        }
        
        private static void goToJail(Player player) {
            player.position = MonopolyBoard.GO_TO_JAIL;
            player.jailTurns = 3;
        }
        
        public static class Player {
        private boolean getOutOfJailFreeCard;
        private int jailTurns;
        
        

        public Player() {
            this.getOutOfJailFreeCard = false;
            this.jailTurns = 0;
        }

        public boolean hasGetOutOfJailFreeCard() {
            return getOutOfJailFreeCard;
        }

        public void useGetOutOfJailFreeCard() {
            getOutOfJailFreeCard = false;
        }

        public void incrementJailTurns() {
            jailTurns++;
        }

        public void resetJailTurns() {
            jailTurns = 0;
        }

        public int getJailTurns() {
            return jailTurns;
        }

      
    }

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        int[] numberOfRuns = { 1000, 10000, 100000, NUMBER_OF_MOVES };
        for (int n : numberOfRuns) {
            System.out.println("Simulating " + n + " moves...");

            // Strategy A
            MonopolyBoard boardA = new MonopolyBoard();
            Player playerA = new Player();
            simulateGame(playerA, boardA, n, true);

            // Strategy B
            MonopolyBoard boardB = new MonopolyBoard();
            Player playerB = new Player();
            simulateGame(playerB, boardB, n, false);

            // Print statistics for Strategy A
            System.out.println("Strategy A:");
            printStatistics(playerA, boardA);

            // Print statistics for Strategy B
            System.out.println("Strategy B:");
            printStatistics(playerB, boardB);
        }
    }

    private static void simulateGame(Player player, MonopolyBoard board, int numberOfMoves, boolean useStrategyA) {
        Random random = new Random();
        int currentPosition = MonopolyBoard.GO;
        for (int i = 0; i < numberOfMoves; i++) {
            int diceRoll1 = random.nextInt(NUMBER_OF_SIDES_ON_DICE) + 1;
            int diceRoll2 = random.nextInt(NUMBER_OF_SIDES_ON_DICE) + 1;

            if (diceRoll1 == diceRoll2) {
                // Doubles rolled
                if (player.hasGetOutOfJailFreeCard()) {
                    player.useGetOutOfJailFreeCard();
                    player.resetJailTurns();
                } else {
                    player.incrementJailTurns();
                    if (player.getJailTurns() == 3) {
                        player.resetJailTurns();
                    }
                }
            } else {
                // Not doubles rolled
                if (player.getJailTurns() > 0) {
                    // Assume the player paid the fine and got out of jail
                    player.resetJailTurns();
                }
            }

            int totalRoll = diceRoll1 + diceRoll2;
           
			int newPosition = (currentPosition + totalRoll) % 40;
            board.recordLanding(newPosition);
            currentPosition = newPosition;

              }
            }
        
    
    
    private static void printStatistics(Player player, MonopolyBoard board) {
        // Array to store square names in the same order as their IDs
        String[] SQUARE_NAMES = {
        		"GO", "Mediterranean Avenue", "Community Chest 1", "Baltic Avenue", "Income Tax",
        	    "Reading Railroad", "Oriental Avenue", "Chance 1", "Vermont Avenue", "Connecticut Avenue",
        	    "Jail / Just Visiting", "St. Charles Place", "Electric Company", "States Avenue", "Virginia Avenue",
        	    "Pennsylvania Railroad", "St. James Place", "Community Chest 2", "Tennessee Avenue", "New York Avenue",
        	    "Free Parking", "Kentucky Avenue", "Chance 2", "Indiana Avenue", "Illinois Avenue",
        	    "B&O Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens",
        	    "Go To Jail", "Pacific Avenue", "North Carolina Avenue", "Community Chest 3", "Pennsylvania Avenue",
        	    "Short Line Railroad", "Chance 3", "Park Place", "Luxury Tax", "Boardwalk"
        };

        System.out.println("Number of times the player has landed on each square:");
        for (int square : board.squaresLandedOn.keySet()) {
            System.out.println(SQUARE_NAMES[square] + ": " + board.squaresLandedOn.get(square));
        }

        System.out.println("Percentage of time the player has landed on each square:");
        for (int square : board.squaresLandedOn.keySet()) {
            int landings = board.squaresLandedOn.get(square);
            double percentage = (landings * 100.0) / NUMBER_OF_MOVES;
            System.out.println(SQUARE_NAMES[square] + ": " + percentage + "%");
        }
    }


    }}
