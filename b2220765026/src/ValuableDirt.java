import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * The ValuableDirt class represents the valuable dirt in the game.
 */
public class ValuableDirt extends Sprite{
    private Valuable values; // The valuable object that the valuable dirt contains.

    public ValuableDirt(int x, int y, Valuable valuable) {
        super(x, y, Definitions.BLOCK_SIZE, Definitions.BLOCK_SIZE, "valuable", Color.TRANSPARENT);
        setValues(valuable);
        this.imageView = new ImageView(new Image("assets/underground/valuable_"+ valuable.getName().toLowerCase() +".png"));
        this.fitImage(x, y);
        this.setFill(new ImagePattern(imageView.getImage()));
    }

    public Valuable getValues() {
        return values;
    }

    public void setValues(Valuable values) {
        this.values = values;
    }
}
