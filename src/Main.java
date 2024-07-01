import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        String[] attributes;
        try {
            attributes = FileReader.readFile("assets/attributes_of_valuables.txt", true, true);
            for (int i = 1; i < attributes.length; i++) {
                String[] attribute = attributes[i].split("\t");
                // KEY, WORTH, WEIGHT
                Valuable valuable = new Valuable(attribute[0], Double.parseDouble(attribute[1]), Double.parseDouble(attribute[2]));
                Definitions.VALUABLES.put(attribute[0], valuable);
            }
            launch(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }


    /**
     * Start the application.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        Game game = new Game();
        Drill drill = game.drill;
        Scene scene = new Scene(game.createContent());

        // Set the key event handlers
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    drill.setFacing("left");
                    drill.moveLeft(game.sprites());
                    break;
                case RIGHT:
                    drill.setFacing("right");
                    drill.moveRight(game.sprites());
                    break;
                case UP:
                    drill.setFacing("up");
                    // set current position to fit the grid in y position for once
                    if (!drill.flying) drill.setTranslateY(drill.getTranslateY() - drill.getTranslateY() % Definitions.BLOCK_SIZE);
                    drill.flying = true;
                    drill.fly(game.sprites());
                    break;
                case DOWN:
                    drill.setFacing("down");
                    drill.moveDown(game.sprites());
                    break;
                case SPACE:
                    game.teleportToTop();
                    break;
                case R:
                    start(stage);
                    break;
            }
        });

        // on up key release, stop flying
        scene.setOnKeyReleased(e -> {
            if (Objects.requireNonNull(e.getCode()) == KeyCode.UP) {
                drill.flying = false;
            }
            if (!Game.isRunning) {
                EndGameStage endGameStage = new EndGameStage(drill.isDead() ? "lava" : "fuel", drill, stage);
                Scene endGameScene = new Scene(endGameStage.createContent());
                stage.setScene(endGameScene);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Drill Game");
        stage.show();
    }
}