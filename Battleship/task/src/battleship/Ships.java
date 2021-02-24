package battleship;

public enum Ships {
    AIRCRAFT_CARRIER("Aircraft carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2)
    ;

    private final String description;
    private final int value;

    Ships(String description, int value) {
        this.description = description;
        this.value = value;
    }


    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
