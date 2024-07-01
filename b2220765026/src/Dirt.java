import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * Dirt class which is derived from Sprite class.
 */
public class Dirt extends Sprite {
    boolean isTop;

    Dirt(int x, int y, boolean isTop) {
        super(x, y, Definitions.BLOCK_SIZE, Definitions.BLOCK_SIZE, "dirt", isTop ? Color.GREEN : Color.BROWN);
        this.isTop = isTop;

        // check for if the dirt is on the top
        if (isTop) {
            double random = Math.random();
            if (random < 0.5) {
                this.imageView = new ImageView(new Image("/assets/underground/top_01.png"));
            } else {
                this.imageView = new ImageView(new Image("/assets/underground/top_02.png"));
            }
        } else {
            // else put a random dirt image.
            int random = (int) (Math.random() * 5) + 1;
            imageView = new ImageView(new Image("/assets/underground/soil_0" + random + ".png"));
        }

        this.fitImage(x, y);
        this.setFill(new ImagePattern(imageView.getImage()));

    }
}
