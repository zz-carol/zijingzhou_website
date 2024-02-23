/**
 * The Main class represents the entry point for the card game.
 */
public class Main {

    /**
     * The main method initializes the game and starts playing until there's a
     * winner.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {

        // Create a new deck of cards.
        Deck deck = new Deck();

        // Create two players and assign their names.
        CardPlayer[] players = new CardPlayer[2];
        players[0] = new CardPlayer("Player 1");
        players[1] = new CardPlayer("Player 2");

        // Create a dealer and assign the players and deck to it.
        Dealer dealer = new Dealer(players, deck);

        // Create a new table to place the cards on.
        CardTable table = new CardTable();

        // Set the first player's turn.
        players[0].setTurn(true);

        // Print headers for table places.
        System.out.println("    CardTable Places     ");
        System.out.println("-------------------------");
        System.out.println("| p1  | p2  | p3  | p4  |");
        System.out.println("-------------------------");
        
        int numItrs = 0; // keep track of how many iterations are played
        
        // Play until there's a winner.
        while (players[0].getHand().size() != 0 && players[1].getHand().size() != 0) {
            if (players[0].isTurn()) {
                // Player 1 plays a card.
                table.addCardToPlace(players[0].play());
                table.checkPlaces(players[0]);

                // Set Player 2's turn.
                players[1].setTurn(true);

            } else if (players[1].isTurn()) {
                // Player 2 plays a card.
                table.addCardToPlace(players[1].play());
                table.checkPlaces(players[1]);

                // Set Player 1's turn.
                players[0].setTurn(true);

            }
            numItrs++; // update number of iterations counter

            // Show the cards on the table
            System.out.printf("| %3d | %3d | %3d | %3d | \n", 
            		table.getPlaces()[0], table.getPlaces()[1], table.getPlaces()[2], table.getPlaces()[3]); 
        }
        
        System.out.println("-------------------------");
        System.out.println("End of game. Total #iterations = " + numItrs);

        // Display each player's bank of cards: 
        for (CardPlayer player : players) {
        	System.out.println(player.bankToString());
        }
                
        // Display the winner of the game: 
        CardPlayer winner = players[0];
        for (CardPlayer player : players) {
            if (player.getPoints() > winner.getPoints()) {
                winner = player;
            }
        }
        System.out.println("The winner is: " + winner.name + " (Points: " + winner.getPoints() + ")");
        
    }
}
