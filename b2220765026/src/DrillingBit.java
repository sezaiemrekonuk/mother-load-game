/**
 * DrillingBit class represents a drilling bit that is used in the drilling process.
 */
public class DrillingBit {
    private String name;
    private double price;
    private double efficiencyRate; // The efficiency rate of the drilling bit.
    private double speed;
    private double fuelConsumptionRate; // The fuel consumption rate of the drilling bit.

    public DrillingBit(String name, double price, double efficiencyRate, double fuelConsumptionRate, double speed) {
        this.name = name;
        this.price = price;
        this.efficiencyRate = efficiencyRate;
        this.speed = speed;
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getEfficiencyRate() {
        return efficiencyRate;
    }

    public void setEfficiencyRate(double efficiency) {
        this.efficiencyRate = efficiency;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(double fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }
}
