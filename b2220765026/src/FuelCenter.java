import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * FuelCenter class is a subclass of Sprite class and implements Store interface.
 * Store interface is implemented to provide the ability to collect all the stores in the game.
 */
public class FuelCenter extends Sprite implements Store {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 200;

    public FuelCenter(int x, int y) {
        super(x, y, Definitions.BLOCK_SIZE * 2, Definitions.BLOCK_SIZE * 2, "fuel_center", Color.KHAKI);
        this.image = new Image("assets/extras/sprite/Overground.png");
        this.imageView = new ImageView(this.image);
        // set viewport to show only fuel center in the png file.
        this.imageView.setViewport(new Rectangle2D(0, 0, WIDTH, HEIGHT));
        this.imageView.setFitWidth(Definitions.BLOCK_SIZE * 2);
        this.imageView.setFitHeight(Definitions.BLOCK_SIZE * 2);
        this.setTranslateY(this.getTranslateY() - Definitions.BLOCK_SIZE);
        this.setFill(new ImagePattern(imageView.getImage(), 0, 0, WIDTH, HEIGHT, false));
    }
}
