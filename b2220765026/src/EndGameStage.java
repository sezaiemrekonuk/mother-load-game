import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * EndGameStage is a popup window that appears when the game is over.
 */
public class EndGameStage extends Stage {
    private String type;
    private Drill drill; // the drill object for getting the state of drill at the end.
    private Stage gameStage; // the game stage to close when the game is over.

    public EndGameStage(String type, Drill drill, Stage gameStage) {
        this.type = type;
        this.drill = drill;
        this.gameStage = gameStage;
        this.setTitle("Game Over");
    }

    /**
     * Creates end game content.
     *
     * @return Parent, the root node of the content.
     */
    public Parent createContent() {
        Pane root = new Pane();

        // Set background color to green if the game is over because of fuel,
        // red if the game is over because of lava.
        root.setBackground(new Background(new BackgroundFill(type == "fuel" ? Color.GREEN : Color.RED, null, null)));
        root.setPrefSize(Definitions.WIDTH, Definitions.HEIGHT);

        // game over text
        Text text = new Text(this.type == "fuel" ? "Game Over! You ran out of fuel." : "Game Over! You fell into lava.");
        text.setFill(Color.WHITE);
        text.setFont(Font.font(30));
        text.setTranslateX(Definitions.WIDTH / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setTranslateY(Definitions.HEIGHT / 2 - text.getLayoutBounds().getHeight() / 2);

        // if the game is over because of fuel, show the money earned.
        if (type == "fuel") {
            double money = drill.getMoney();
            Text moneyText = new Text(String.format("You earned $%.0f.", money));
            moneyText.setFill(Color.YELLOW);
            moneyText.setFont(Font.font(20));
            moneyText.setTranslateX(Definitions.WIDTH / 2 - moneyText.getLayoutBounds().getWidth() / 2);
            moneyText.setTranslateY(Definitions.HEIGHT / 2 - moneyText.getLayoutBounds().getHeight() / 2 + 20);
            root.getChildren().add(moneyText);
        }

        root.getChildren().add(text);

        // play again button
        Button playAgain = new Button("Play Again");
        playAgain.setFont(Font.font(20));
        playAgain.cursorProperty().setValue(Cursor.HAND);
        playAgain.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            playAgain.setTranslateX(Definitions.WIDTH / 2 - newValue.getWidth() / 2);
            playAgain.setTranslateY(Definitions.HEIGHT / 2 - newValue.getHeight() / 2 + 60);
        });

        // sets the button to the front so that it is visible.
        playAgain.toFront();
        playAgain.setOnAction(e -> {
            gameStage.close();
            Main main = new Main();
            main.start(new Stage());
        });

        root.getChildren().add(playAgain);

        return root;
    }
}
