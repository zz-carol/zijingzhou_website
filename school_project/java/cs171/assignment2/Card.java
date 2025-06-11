/**
 * A class that represents a playing card with a rank and suit.
 */
public class Card {
    /**
     * The rank of the card which is a number from 1 to 13.
     * Includes King, Queen, Jack, and Ace.
     */
    private int rank;
    /**
     * The suit of the card which is a number from 1 to 4
     * Can be clubs (♣), diamonds (♦), hearts (♥) or spades (♠).
     */
    private int suit;
    /**
     * The identifier of the card.
     * It is a unique number assigned to each card based on its rank and suit.
     * The identifier is calculated as suit * 100 + rank.
     */
    public final int identifier;

    /**
     * Creates a new Card with the given suit and rank.
     * It assigns the identifier as well
     *
     * @param suit The suit of the card.
     * @param rank The rank of the card.
     */
    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        this.identifier = suit * 100 + rank;
    }

    /**
     * Gets the rank of the card.
     *
     * @return The rank of the card.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the rank of the card.
     *
     * @param rank The new rank of the card.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Gets the suit of the card.
     *
     * @return The suit of the card.
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Sets the suit of the card.
     *
     * @param suit The new suit of the card.
     */
    public void setSuit(int suit) {
        this.suit = suit;
    }

}
