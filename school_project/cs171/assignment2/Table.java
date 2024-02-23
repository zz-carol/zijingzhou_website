/**
 * An interface representing a table where cards are played and players can
 * occupy places.
 *
 * @param <T> the type of cards that can be added to the table.
 * @param <E> the type of players that can occupy places on the table.
 */
public interface Table<T extends Card, E extends GeneralPlayer> {

	/**
	 * The number of places on the table that players can put their cards.
	 */
	final int NUMBER_OF_PLACES = 4;

	/**
	 * Adds a card to the table at the first available place.
	 *
	 * @param card the card to add to the table.
	 */
	public void addCardToPlace(T card);

	/**
     * Returns the identifiers of the cards on places 1, 2, 3, and 4 on the table
     * (in that same order). 
     * 
     * @return an array of integers representing the identifiers of all cards placed on the table
     */
	public int[] getPlaces();

	/**
	 * Checks the places on the table to see if any player occupies a place and
	 * removes any cards that they played.
	 *
	 * @param player the player to check for occupying a place.
	 */
	public void checkPlaces(E player);

}
