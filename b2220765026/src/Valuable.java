/**
 * Valuable class that represents a general valuable sprites in the game.
 * It has attributes name, worth, and weight to represent the valuable.
 */
public class Valuable {
    private String name;
    private double worth;
    private double weight;

    public Valuable(String name, double worth, double weight) {
        this.name = name;
        this.worth = worth;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWorth() {
        return worth;
    }

    public double getWeight() {
        return weight;
    }
}
