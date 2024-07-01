import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * The UpgradeCenter class represents the upgrade center in the game.
 */
public class UpgradeCenter extends Sprite implements Store {
    private static final int WIDTH = 165;
    private static final int HEIGHT = 210;

    public UpgradeCenter(int x, int y) {
        super(x, y, Definitions.BLOCK_SIZE * 3, Definitions.BLOCK_SIZE * 2, "upgrade_center", Color.KHAKI);
        this.image = new Image("assets/extras/sprite/Overground.png");
        this.imageView = new ImageView(this.image);
        this.imageView.setViewport(new Rectangle2D(WIDTH, 0, 350, HEIGHT));
        this.setTranslateY(this.getTranslateY() - Definitions.BLOCK_SIZE);
        this.setFill(new ImagePattern(imageView.getImage(), WIDTH, 0, 300, HEIGHT, false));

    }
}
