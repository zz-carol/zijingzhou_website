/* THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
    CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
    Carol Zhou*/

/**
 * 
 * This class represents a table where a game is being played.
 * 
 * It implements the Table interface and is designed to work with Card and
 * CardPlayer objects.
 * 
 * <p>
 * Each table instance must keep track of the cards that players place on the table
 * during the game. The number of places available has a fixed size (<code>NUMBER_OF_PLACES</code>),
 * so we use a regular Java array to represent a CardTable's places field. 
 * Each entry in this places array contains 
 * the cards that were added to that place, which is a more dynamic structure (we don't know 
 * in advance how many cards will be added to this place!). 
 * <p>
 * Therefore, each place
 * entry in this array will reference an ArrayList of Card objects.  
 * <p> 
 * Here is how to declare the array of ArrayLists field <code>places</code>:  
 * 
 * <p>
 * <code>
 * 		private ArrayList&lt;Card&gt;[] places = new ArrayList[NUMBER_OF_PLACES];  
 * </code>
 * <p>
 * 
 * Note that the Field Summary section below will only show you public fields, 
 * but you must declare the required field places described above, which is private.
 * You are also free to create additional fields in your class implementation, if deemed necessary.
 * 
 */

import java.util.ArrayList;

public class CardTable implements Table<Card, CardPlayer> {

    private ArrayList<Card>[] places = new ArrayList[NUMBER_OF_PLACES]; // the 4 different places on the table
    int currentPlace;

    // default constructor that initializes the ArrayLists in the places array and sets the currentPlace to 0.
    public CardTable() {
        for (int i = 0; i < NUMBER_OF_PLACES; i++) {
            places[i] = new ArrayList<Card>();
        }
        currentPlace = 0;
    }

    // adds a card to the current place on the table and increase currentPlace by 1
    public void addCardToPlace(Card card) {
        places[currentPlace].add(0, card);
        if (currentPlace < 3) {
            currentPlace++;
        } else {
            currentPlace = 0;
        }
    }

    // returns the identifiers of the cards on places 1, 2, 3, and 4 on the table (in that same order)
    public int[] getPlaces() {
        int[] cardsPlaces = new int[NUMBER_OF_PLACES];
        for (int i = 0; i < cardsPlaces.length; i++) {
            if (places[i].isEmpty()) {
                cardsPlaces[i] = -1;
            } else {
                cardsPlaces[i] = places[i].get(0).identifier;
            }
        }
        return cardsPlaces;
    }

    // checks if the previous card played by a player matches the rank of any card on the table
    // if a match is found, the player earns a point and both the player's card and the matching card on the table are removed
    public void checkPlaces(CardPlayer player) {
        int previousPlace;
        if (currentPlace == 0) {
            previousPlace = 3;
        } else {
            previousPlace = currentPlace - 1;
        }
        int previousPlaceRank = places[previousPlace].get(0).getRank();
        for (int i = 0; i < NUMBER_OF_PLACES; i++) {
            if (i != previousPlace && !(places[i].isEmpty())) {
                if (places[i].get(0).getRank() == previousPlaceRank) {
                    System.out.println("Matched ranks: " + places[i].get(0).identifier + " (on table) and " + places[previousPlace].get(0).identifier + " (" + player.name + "’s card)");
                    player.setPoints(player.getPoints()+1);
                    player.bank.add(places[previousPlace].get(0));
                    player.bank.add(places[i].get(0));
                    places[previousPlace].remove(0);
                    places[i].remove(0);
                }
            }
        }
        player.setTurn(false);
    }
	
}
