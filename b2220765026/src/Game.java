import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main Game class that contains the game loop and the game logic.
 */
public class Game {
    public static boolean isRunning = false; // game running state

    private final Pane root;
    private final Background background;
    public final Drill drill;
    public AnimationTimer timer;

    private final StatusText fuel;
    private final StatusText money;
    private final StatusText haul;
    private final StatusText upgrade;

    private FuelCenter fuelCenter;
    private UpgradeCenter upgradeCenter;


    public Game() {
        isRunning = true;
        this.root = new Pane();
        this.drill = new Drill(300, 100);
        this.fuel = new StatusText("Fuel", drill.getFuel(), 20);
        this.money = new StatusText("Money", drill.getMoney(), 40);
        this.haul = new StatusText("Haul", drill.getHaul(), 60);
        this.upgrade = new StatusText("Drill Bit", drill.getDrillingBit().getName(), 80);
        this.background = new Background(new BackgroundFill(Color.BLUE, null, null));
        this.fuelCenter = new FuelCenter(100, 100);
        this.upgradeCenter = new UpgradeCenter(400, 100);
    }

    /**
     * Creates the whole game content and implements the game loop.
     * @return the root node of tha game
     */
    public Parent createContent() {
        root.setBackground(background);
        root.setPrefSize(Definitions.WIDTH, Definitions.HEIGHT);

        addTexts();
        createTerrain();
        addFunctionalObjects();

        timer = new AnimationTimer() {
            // ticks for scrolling and collision to be less frequent.
            double tick = 1;
            double collisionTick = 1;

            @Override
            public void handle(long now) {
                if (checkDrillFuelIsDrained()) {
                    endGame("fuel");
                } else {
                    update();
                    if (tick++ % 2 == 0) {
                        scroll();
                        gravity();
                        tick = 1;
                    }
                    if (collisionTick++ % 20 == 0) {
                        checkStoreCollision();
                        collisionTick = 1;
                    }
                }
            }
        };
        timer.start();

        return root;
    }

    /**
     * Returns the list of all sprites in the game.
     * 
     * @return the list of all sprites in the game
     */
    public List<Sprite> sprites() {
        return root.getChildren().stream().filter(s -> s instanceof Sprite).map(s -> (Sprite) s).collect(Collectors.toList());
    }

    /**
     * Adds the text objects to the game.
     */
    private void addTexts() {
        root.getChildren().addAll(fuel, money, haul, upgrade);
    }

    /**
     * Adds the functional objects to the game.
     */
    private void addFunctionalObjects() {
        root.getChildren().addAll(fuelCenter, upgradeCenter, drill);
    }

    /**
     * Adds valuable soils to the terrain, such as gold, silver, etc. using randomizing technique.
     * 
     * @param terrain Current generation of terrain
     * @param y Position of the soil on the y-axis
     * @param x Position of the soil on the x-axis
     * @param randomValuableIndex Randomized number to determine the valuable soil
     */
    private void addValuables(List<Sprite> terrain, int y, int x, double randomValuableIndex) {
        String valuableName;
        if (randomValuableIndex < 2) {
            valuableName = "Amazonite";
        } else if (randomValuableIndex < 3) {
            valuableName = "Diamond";
        } else if (randomValuableIndex < 5) {
            valuableName = "Ruby";
        } else if (randomValuableIndex < 9) {
            valuableName = "Emerald";
        } else if (randomValuableIndex < 14) {
            valuableName = "Einsteinium";
        } else if (randomValuableIndex < 19) {
            valuableName = "Platinum";
        } else if (randomValuableIndex < 28) {
            valuableName = "Goldium";
        } else if (randomValuableIndex < 55) {
            valuableName = "Silverium";
        } else if (randomValuableIndex < 75) {
            valuableName = "Bronzium";
        } else {
            valuableName = "Ironium";
        }
        
        ValuableDirt valuableDirt = new ValuableDirt(x, y, Definitions.VALUABLES.get(valuableName));
        terrain.add(valuableDirt);
    }

    /**
     * Creates the base terrain of the game.
     */
    private void createTerrain() {
        List<Sprite> terrain = new ArrayList<>();
        for (int y = 150; y < Definitions.HEIGHT; y += Definitions.BLOCK_SIZE) {
            if (y == 150) {
                for (int x = 0; x < Definitions.WIDTH; x += Definitions.BLOCK_SIZE) {
                    Dirt dirt = new Dirt(x, y, true);
                    terrain.add(dirt);
                }
            } else {
                Rock rock = new Rock(0, y);
                Rock rock2 = new Rock(Definitions.WIDTH - Definitions.BLOCK_SIZE, y);
                root.getChildren().addAll(rock, rock2);
                for (int x = 50; x < Definitions.WIDTH - 50; x += Definitions.BLOCK_SIZE) {
                    double random = Math.random();
                    if (random < 0.05) {
                        Lava lava = new Lava(x, y);
                        terrain.add(lava);
                    } else if (random < 0.9) {
                        Dirt dirt = new Dirt(x, y, false);
                        terrain.add(dirt);
                    } else {
                        double randomValuableIndex = Math.random() * 100;
                        addValuables(terrain, y, x, randomValuableIndex);
                    }
                }

            }
        }
        root.getChildren().addAll(terrain);
    }

    /**
     * Checks if the drill's fuel is drained.
     * @return true if the fuel is drained, false otherwise
     */
    private boolean checkDrillFuelIsDrained() {
        return drill.getFuel() <= 0;
    }

    /**
     * Updates the game state.
     * For each sprite in the game, checks if the drill intersects with the sprite.
     * If the drill intersects with a dirt, the dirt is removed and the drill's haul and money are updated.
     * If the drill intersects with a lava, the game ends.
     * Also, each tick, the drill's fuel is decreased by 0.001.
     */
    private void update() {
        sprites().forEach(s -> {
            // if the center of drill intersects with center of dirt, remove dirt
            if (s != drill && drill.getTranslateX() == s.getTranslateX() && drill.getTranslateY() == s.getTranslateY() && !s.isDead()) {
                if (s instanceof ValuableDirt) {
                    drill.setHaul(drill.getHaul() + ((ValuableDirt) s).getValues().getWeight() * (1 / drill.getDrillingBit().getEfficiencyRate()));
                    drill.setMoney(drill.getMoney() + ((ValuableDirt) s).getValues().getWorth() * drill.getDrillingBit().getEfficiencyRate());
                    drill.setFuel(drill.getFuel() - 3 * drill.getDrillingBit().getFuelConsumptionRate());
                    money.updateValue(drill.getMoney());
                    haul.updateValue(drill.getHaul());
                    fuel.updateValue(drill.getFuel());
                } else if (s instanceof Dirt) {
                    drill.setFuel(drill.getFuel() - 1);
                    fuel.updateValue(drill.getFuel());
                } else if (s instanceof Lava) {
                    drill.setDead(true);
                    endGame("lava");
                }

                if (!(s instanceof Store))
                    s.setDead(true);
            }
        });
        drill.setFuel(drill.getFuel() - 0.001);
        fuel.updateValue(drill.getFuel());
    }

    /**
     * Checks if the drill collides with the store objects.
     */
    private void checkStoreCollision() {
        if ((drill.getTranslateX() == fuelCenter.getTranslateX() || drill.getTranslateX() == fuelCenter.getTranslateX() + 50) && drill.getTranslateY() == fuelCenter.getTranslateY() + Definitions.BLOCK_SIZE && drill.getMoney() >= Definitions.FUEL_PRICE && drill.getFuel() < drill.getMaxFuel() - Definitions.FUEL_FILL_AMOUNT) {
            drill.setFuel(drill.getFuel() + Definitions.FUEL_FILL_AMOUNT);
            drill.setMoney(drill.getMoney() - Definitions.FUEL_PRICE);
            fuel.updateValue(drill.getFuel());
            money.updateValue(drill.getMoney());
        }
        if (drill.getTranslateX() == upgradeCenter.getTranslateX() && drill.getTranslateY() == upgradeCenter.getTranslateY()) {
            // upgrade center
        }
    }

    /**
     * Ends the game with the given reason.
     * @param reason the reason for ending the game
     */
    private void endGame(String reason) {
        timer.stop();
        isRunning = false;
    }

    /**
     * If the drill is not flying and there is no dirt below the drill,
     * the drill falls down.
     */
    private void gravity() {
        if (!drill.flying && (sprites().stream().noneMatch(s -> s.getTranslateX() == drill.getTranslateX() && s.getTranslateY() == drill.getTranslateY() + 50) || sprites().stream().anyMatch(s -> s.getTranslateX() == drill.getTranslateX() && s.getTranslateY() == drill.getTranslateY() + 50 && s.isDead()))) {
            drill.setTranslateY(drill.getTranslateY() + 5);
        }

    }

    /**
     * Scrolls the terrain layer.
     * This method implements a scrolling technique that creates a new terrain layer if the bottom of the screen is empty.
     * and moves the existing terrain layer up.
     * Also, if drill goes up to the top of the screen, the whole terrain layer moves down.
     */
    private void scroll() {
        if (drill.getTranslateY() >= 550) {
            createTerrainLayerForScrolling();
            sprites().forEach(s -> s.setTranslateY(s.getTranslateY() - Definitions.BLOCK_SIZE));
        } else if (drill.getTranslateY() <= 350 && drill.getTranslateY() > 300 && drill.flying && sprites().stream().anyMatch(s -> s.getTranslateY() == 100)) {
            sprites().forEach(s -> s.setTranslateY(s.getTranslateY() + Definitions.BLOCK_SIZE));
        }

        getToFront(new ArrayList<Shape>() {{
            add(drill);
            // add every text
            addAll(root.getChildren().stream().filter(s -> s instanceof Text).map(s -> (Text) s).collect(Collectors.toList()));
        }});
    }

    /**
     * Brings the given shapes to the front of the screen.
     * @param shapes the list of shapes to bring to the front
     */
    private void getToFront(List<Shape> shapes) {
        shapes.forEach(s -> s.toFront());
    }

    /**
     * Also works like the createTerrain method but only creates the terrain layer for scrolling.
     */
    private void createTerrainLayerForScrolling() {
        // if the bottom of the screen is empty
        int yPositionOfNewLayer = 800;
        if (sprites().stream().noneMatch(s -> s.getTranslateY() == yPositionOfNewLayer)) {
            List<Sprite> terrain = new ArrayList<>();
            for (int y = yPositionOfNewLayer; y < yPositionOfNewLayer + Definitions.BLOCK_SIZE; y += Definitions.BLOCK_SIZE) {
                Rock rock = new Rock(0, y);
                Rock rock2 = new Rock(Definitions.WIDTH - Definitions.BLOCK_SIZE, y);
                root.getChildren().addAll(rock, rock2);
                for (int x = 50; x < Definitions.WIDTH - 50; x += Definitions.BLOCK_SIZE) {
                    double random = Math.random();
                    if (random < 0.05) {
                        Lava lava = new Lava(x, y);
                        terrain.add(lava);
                    } else if (random < 0.9) {
                        Dirt dirt = new Dirt(x, y, false);
                        terrain.add(dirt);
                    } else {
                        double randomValuableIndex = Math.random() * 100;
                        addValuables(terrain, y, x, randomValuableIndex);
                    }
                }
            }
            root.getChildren().addAll(terrain);
        }
    }

    /**
     * Teleports the drill to the top of the screen.
     */
    public void teleportToTop() {
        drill.setCanTeleport(true);
        if (drill.getMoney() > 10000 && drill.getFuel() > Definitions.FUEL_FILL_AMOUNT) {

            while (sprites().stream().noneMatch(s -> s.getTranslateY() == 150 && s instanceof Dirt && ((Dirt) s).isTop)) {
                sprites().forEach(s -> s.setTranslateY(s.getTranslateY() + Definitions.BLOCK_SIZE));
            }

            drill.setTranslateY(100);
            drill.setTranslateX(300);

            drill.setFuel(drill.getFuel() - Definitions.FUEL_FILL_AMOUNT);
            fuel.updateValue(drill.getFuel());

            drill.setMoney(drill.getMoney() - Definitions.TELEPORTATION_COST);
            money.updateValue(drill.getMoney());
        }
    }
}