import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Lava class
 * Represents the lava blocks in the game
 */
public class Lava extends Sprite {
    public Lava(int x, int y) {
        super(x, y, Definitions.BLOCK_SIZE, Definitions.BLOCK_SIZE, "lava", Color.TRANSPARENT);
        this.imageView = new ImageView(new Image("assets/underground/lava_0"+ (int)(Math.random() * 3 + 1) +".png"));
        this.fitImage(x, y);
        this.setFill(new ImagePattern(imageView.getImage()));
    }
}
