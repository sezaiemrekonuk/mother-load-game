import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * The main Sprite class for the game
 */
public class Sprite extends Rectangle {
    private boolean dead = false;
    public final String type;
    public boolean flying = false;
    protected Image image;
    protected ImageView imageView;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    /**
     * Set the sprite to dead with dark image mask of soil.
     *
     * @param dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(0.7);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        this.setFill(new ImagePattern(new Image("/assets/underground/soil_01.png")));
        this.setEffect(lighting);
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Fit the image to the block size.
     * @param x the x position
     * @param y the y position
     */
    void fitImage(int x, int y) {
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(Definitions.BLOCK_SIZE);
        imageView.setFitWidth(Definitions.BLOCK_SIZE);
        this.setFill(new ImagePattern(imageView.getImage()));
    }
}
