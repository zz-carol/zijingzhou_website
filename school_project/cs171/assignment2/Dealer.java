import java.util.Random;

/**
 * This class represents a dealer who shuffles a deck of cards and
 * distributes them to players.
 */
public class Dealer {
    private Deck deck;

    /**
     * Creates a new Dealer object with the specified players and deck.
     * 
     * @param players the array of CardPlayer objects to distribute the cards to
     * @param deck    the Deck object to shuffle and distribute
     */
    public Dealer(CardPlayer[] players, Deck deck) {
        this.deck = deck;
        this.shuffle();
        this.distribute(players, this.deck);
    }

    /**
     * Distributes the cards in the deck to the specified players.
     * 
     * @param players the array of CardPlayer objects to distribute the cards to
     * @param deck    the Deck object to distribute
     */
    private void distribute(CardPlayer[] players, Deck deck) {
        for (int cardCounter = 0; cardCounter < deck.cards.length; cardCounter += players.length) {
            for (int playerCounter = 0; playerCounter < players.length; playerCounter++) {
                players[playerCounter].addToHand(deck.cards[cardCounter + playerCounter]);
            }
        }
    }

    /**
     * Shuffles the cards in the deck; it generates two random variables as the card
     * index and swaps them.
     */
    private void shuffle() {
        Random randomNumber = new Random();
        for (int card = 0; card < this.deck.cards.length; card++) {
            swap(randomNumber.nextInt(deck.cards.length), randomNumber.nextInt(deck.cards.length));
        }
    }

    /**
     * Swaps the positions of two cards in the deck.
     * 
     * @param random1 the index of the first card to swap
     * @param random2 the index of the second card to swap
     */
    private void swap(int random1, int random2) {
        Card temp;
        temp = this.deck.cards[random1];
        this.deck.cards[random1] = this.deck.cards[random2];
        this.deck.cards[random2] = temp;
    }

    /**
     * Prints out the identifier of each card in the deck.
     */
    public void showDeck() {
        for (Card card : this.deck.cards) {
            System.out.println(card.identifier);
        }
    }
}
