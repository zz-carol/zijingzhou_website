/**
 * An abstract class representing a general player.
 *
 * @param <T> the type of the value returned by the player's play method.
 */
public abstract class GeneralPlayer<T> {

    /** The name of the player. */
    public String name;

    /**
     * Creates a new GeneralPlayer with a default name.
     */
    public GeneralPlayer() {
        this.name = "General Player";
    }

    /**
     * Creates a new GeneralPlayer with the given name.
     *
     * @param name the name of the player.
     */
    public GeneralPlayer(String name) {
        this.name = name;
    }

    /**
     * Makes a move or takes an action, depending on the implementation.
     *
     * @return the result of the player's action or move.
     */
    public abstract T play();
}
