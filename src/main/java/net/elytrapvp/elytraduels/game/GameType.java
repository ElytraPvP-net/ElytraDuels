package net.elytrapvp.elytraduels.game;

/**
 * Represents a form of match.
 */
public enum GameType {
    UNRANKED("Unranked"),
    RANKED("Ranked"),
    PARTY("Party"),
    DUEL("Duel"),
    FFA("FFA");

    private final String name;

    /**
     * Registers a new Game type
     * @param name Name as a string.
     */
    GameType(String name) {
        this.name = name;
    }

    /**
     * Get the name of the GameType
     * @return Name
     */
    public String getName() {
        return name;
    }
}