import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.List;

/**
 * A functional class for the drill.
 */
public class Drill extends Sprite {
    private double fuel = 100;
    private double haul = 0;
    private double money = 0;
    private double maxFuel = 100;
    private double maxHaul = 10000;
    private DrillingBit drillingBit;
    private boolean canTeleport;

    Drill(int x, int y) {
        super(x, y, Definitions.BLOCK_SIZE, Definitions.BLOCK_SIZE, "drill", Color.BLACK);
        this.image = new Image("/assets/drill/drill_01.png");
        this.imageView = new ImageView(image);
        this.fitImage(x, y);
        this.setFill(new ImagePattern(imageView.getImage()));
        this.drillingBit = new DrillingBit("stock", 0, 0.8, 2, 1);
    }

    /**
     * Checks if the drill is in a valid position.
     * @return true if the drill is in the grid position.
     */
    private boolean checkPosition() {
        boolean x = getTranslateX() % Definitions.BLOCK_SIZE == 0;
        boolean y = getTranslateY() % Definitions.BLOCK_SIZE == 0;

        return x&&y;
    }

    /**
     * Moves the drill to the left.
     * @param sprites the list of sprites in the game.
     */
    public void moveLeft(List<Sprite> sprites) {
        if (checkPosition() && checkSide("left", sprites))
            setTranslateX(getTranslateX() - Definitions.BLOCK_SIZE);
    }

    /**
     * Moves the drill to the right.
     * @param sprites the list of sprites in the game.
     */
    public void moveRight(List<Sprite> sprites) {
        if (checkPosition() && checkSide("right", sprites))
            setTranslateX(getTranslateX() + Definitions.BLOCK_SIZE);
    }

    /**
     * Moves the drill up.
     * @param sprites the list of sprites in the game.
     */
    public void fly(List<Sprite> sprites) {
        if (checkSide("up", sprites))
            setTranslateY(getTranslateY() - (double) Definitions.BLOCK_SIZE / 5);

    }

    /**
     * Moves the drill down.
     * @param sprites the list of sprites in the game.
     */
    public void moveDown(List<Sprite> sprites) {
        if (checkPosition() && checkSide("down", sprites))
            setTranslateY(getTranslateY() + Definitions.BLOCK_SIZE);
    }

    /**
     * Checks if the drill can move to the specified side.
     *
     * @param side the side to check.
     * @param sprites the list of sprites in the game.
     * @return true if the drill can move to the specified side, false otherwise.
     */
    private boolean checkSide(String side, List<Sprite> sprites) {
        switch (side){
            case "left":
                return sprites.stream().anyMatch(s -> s.getTranslateX() == getTranslateX() - 50 && s.getTranslateY() == getTranslateY() && !s.type.equals("rock")) || sprites.stream().noneMatch(s -> s.getTranslateX() == getTranslateX() - 50 && s.getTranslateY() == getTranslateY());
            case "right":
                return sprites.stream().anyMatch(s -> s.getTranslateX() == getTranslateX() + 50 && s.getTranslateY() == getTranslateY() && !s.type.equals("rock")) || sprites.stream().noneMatch(s -> s.getTranslateX() == getTranslateX() + 50 && s.getTranslateY() == getTranslateY());
            case "up":
                return sprites.stream().anyMatch(s -> s.getTranslateX() == getTranslateX() && s.getTranslateY() == getTranslateY() - 50 && s.isDead()) || sprites.stream().noneMatch(s -> s.getTranslateX() == getTranslateX() && s.getTranslateY() == getTranslateY() - 50);
            case "down":
                return sprites.stream().anyMatch(s -> s.getTranslateX() == getTranslateX() && s.getTranslateY() == getTranslateY() + 50 && !s.type.equals("rock")) || sprites.stream().noneMatch(s -> s.getTranslateX() == getTranslateX() && s.getTranslateY() == getTranslateY() + 50);
            default:
                return false;
        }
    }

    /**
     * Sets the direction the drill is facing.
     * @param direction
     */
    public void setFacing(String direction) {
        switch (direction) {
            case "left":
                image = new Image("/assets/drill/drill_01.png");
                break;
            case "right":
                image = new Image("/assets/drill/drill_59.png");
                break;
            case "up":
                image = new Image("/assets/drill/drill_22.png");
                break;
            case "down":
                image = new Image("/assets/drill/drill_43.png");
                break;
        }
        imageView.setImage(image);
        this.fitImage((int) getTranslateX(), (int) getTranslateY());
    }


    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        if (fuel > maxFuel) {
            this.fuel = maxFuel;
        } else if (fuel < 0) {
            this.fuel = 0;
        } else
            this.fuel = fuel;
    }

    public double getHaul() {
        return haul;
    }

    public void setHaul(double haul) {
        this.haul = haul;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public DrillingBit getDrillingBit() {
        return drillingBit;
    }

    public void setDrillingBit(DrillingBit drillingBit) {
        this.drillingBit = drillingBit;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public double getMaxHaul() {
        return maxHaul;
    }

    public void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }

    public void setMaxHaul(double maxHaul) {
        this.maxHaul = maxHaul;
    }

    public boolean isCanTeleport() {
        return canTeleport;
    }

    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }
}
