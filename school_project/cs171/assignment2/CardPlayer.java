/* THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
    CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
    Carol Zhou*/

/**
 * A class that represents a card player.
 * 
 * For each card player instance, we should keep track of how many points 
 * they earned in the game so far, as well as whether it is their turn or not.
 * Additionally, their hand and bank of cards should be stored in two 
 * separate ArrayLists of Card objects. 
 * 
 * <p>
 * A player's points, turn, and hand of cards should all be declared
 * private fields, whereas the bank of cards should be public, as follows: 
 * <p>
 * <code>
 * 		private int points; 
 * 
 * 		private boolean turn; 
 * 
 * 		private ArrayList&lt;Card&gt; hand = new ArrayList&lt;Card&gt;(); 
 *
 * 		public ArrayList&lt;Card&gt; bank = new ArrayList&lt;Card&gt;();
 * </code>
 * <p>
 * 
 * Note that the Field Summary section below will only show you public fields, 
 * but you must declare all the fields described above in your implementation of this class,
 * including the private fields. You are free to create additional fields if deemed necessary.
 * 
 * @param <Card> the type of card used in the game
 */

import java.util.ArrayList;


public class CardPlayer extends GeneralPlayer<Card> {

    private int points;
    private boolean turn;
    private ArrayList<Card> hand = new ArrayList<Card>(); // the player's hand of cards that is private to them (not visible to anyone else)
    public ArrayList<Card> bank = new ArrayList<Card>(); // the player's bank of cards; cards that had a match

    // default constructor that creates a new card player with a default name
    public CardPlayer() {
        super();
    }

    // alternative constructor that creates a new card player with the given name
    public CardPlayer(String name) {
        super(name);
    }

    // get the number of points the player has earned
    public int getPoints() {
        return points;
    }

    // set the number of points the player has earned
    public void setPoints(int points) {
        this.points = points; 
    }

    // returns true if it is the player's turn, false otherwise
    public boolean isTurn() {
        return turn;
    }

    // sets whether it is the player's turn
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // adds a card to the player's hand
    public void addToHand(Card card) {
        hand.add(card);
    }

    // gets the player's hand of cards
    public ArrayList<Card> getHand() {
        return hand;
    }

    // returns a message showing the player's hand of cards.
    public String handToString() {
        String handCards = "";
        for(int i = 0; i < hand.size()-1; i++){
            handCards += hand.get(i).identifier + " ";
        }
        handCards += hand.get(hand.size()-1).identifier;
        return name + " hand has " + hand.size() + " cards: " + handCards;
    }

    // returns a message showing the player's bank of cards.
    public String bankToString() {
        String bankCards = "";
        for(int i = 0; i < bank.size()-1; i++){
            bankCards += bank.get(i).identifier + " ";
        }
        bankCards += bank.get(bank.size()-1).identifier;
        return name + " bank has " + bank.size() + " cards: " + bankCards;
    }

    // gets the top card from the player's hand and removes it from the hand
    public Card play() {
        if(hand.size() == 0){
            return null;
        } else {
            Card topCard = hand.get(0);
            hand.remove(0);
            return topCard;
        }
    }

}
