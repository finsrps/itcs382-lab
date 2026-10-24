
package bikeshare;

public enum Station {
    LIBRARY("Library", "L"),
    CANTEEN("Canteen", "C"),
    DORM("Dorm", "D");

    private final String label;
    private final String prefix;

    Station(String label, String prefix) {
        this.label = label;
        this.prefix = prefix;
    }

    public String getLabel() {
        return label;
    }

    public String getPrefix() {
        return prefix;
    }

    public static Station fromInput(String input) {
        for (Station station : values()) {
            if (station.label.equalsIgnoreCase(input)) {
                return station;
            }
        }
        throw new IllegalArgumentException("Unknown station: " + input);
    }
}
