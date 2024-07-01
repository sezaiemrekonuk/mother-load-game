import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * The Rock class represents a rock in the game.
 */
public class Rock extends Sprite {
    Rock(int x, int y) {
        super(x, y, Definitions.BLOCK_SIZE, Definitions.BLOCK_SIZE, "rock", Color.GRAY);
        int random = (int) (Math.random() * 3) + 1;
        this.imageView  = new ImageView(new Image("/assets/underground/obstacle_0" + random + ".png"));
        this.fitImage(x, y);
        this.setFill(new ImagePattern(imageView.getImage()));
    }
}