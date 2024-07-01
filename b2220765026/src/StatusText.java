import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Status text is a text object that displays the status of the game.
 */
public class StatusText extends Text {
    private String type;
    private double value;

    /**
     * Numeric value constructor.
     * @param type the type of status
     * @param value the value of the status in double
     * @param y the y position of the text
     */
    public StatusText(String type, double value, int y) {
        super(String.format("%s: %.2f", type, value));
        this.type = type;
        this.value = value;
        setFont(Font.font(20));
        setFill(Color.WHITE);
        setTranslateX(10);
        setTranslateY(y);
    }

    /**
     * String value constructor.
     * @param type the type of status
     * @param value the value of the status in string
     * @param y the y position of the text
     */
    public StatusText(String type, String value, int y) {
        super(String.format("%s: %s", type, value));
        this.type = type;
        updateValue(value);
        setFont(Font.font(20));
        setFill(Color.WHITE);
        setTranslateX(10);
        setTranslateY(y);
    }

    /**
     * Updates the value of the status text.
     * @param value value in double
     */
    public void updateValue(double value) {
        setText(String.format("%s: %.2f", this.type, value));
    }

    /**
     * Updates the value of the status text.
     * @param value value in string
     */
    public void updateValue(String value) {
        value = value.substring(0, 1).toUpperCase() + value.substring(1);
        setText(String.format("%s: %s", this.type, value));
    }
}
