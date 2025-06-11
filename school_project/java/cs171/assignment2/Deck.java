/**
 * This class represents a standard deck of 52 playing cards.
 */
public class Deck {

    /** The number of cards in a deck. */
    static final int CARD_COUNT = 52;

    /** An array of Card objects representing the cards in the deck. */
    Card[] cards = new Card[CARD_COUNT];

    /**
     * Constructs a new deck of cards, consisting of 52 cards in four suits (hearts,
     * diamonds, clubs, and spades)
     * and 13 ranks (Ace, 2-10, Jack, Queen, and King).
     */
    public Deck() {
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                /**
                 * Create a new Card object with the given suit and rank and add it to the cards
                 * array.
                 */
                this.cards[suit * 13 + rank] = new Card(suit + 1, rank + 1);
            }
        }
    }
}
