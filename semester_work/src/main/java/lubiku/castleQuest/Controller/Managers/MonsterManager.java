package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.Model.Enemies.MonsterSlime;
import lubiku.castleQuest.Model.Enemies.MonsterZombie;
import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.View.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>MonsterManager</h2>
 * The MonsterManager class manages the monsters in the game.
 * It handles the configuration of monsters from JSON data, sprite setup, and sprite loading.
 */
public class MonsterManager implements Configurator {
    private int TILE_SIZE;

    // Monsters
    private Monster[] monsters;
    private final GamePanel GAME_PANEL;

    /**
     * <h3>MonsterManager</h3>
     * Constructs a MonsterManager with the specified GamePanel.
     * @param GAME_PANEL The GamePanel to associate with the MonsterManager.
     */
    public MonsterManager(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;
    }

    @Override
    public void configureJsonObject(JsonObject configurator) { }

    /**
     * <h3>configureJsonArray</h3>
     * Configures the monsters using the provided JSON array.
     * Extracts the monster data from the JSON array and sets up the monsters accordingly.
     * @param config The JSON array containing the monster configuration.
     */
    @Override
    public void configureJsonArray(JsonArray config) {
        this.setMonsters(new Monster[config.size()]);
        for (int i = 0; i < config.size(); i++) {
            JsonObject objectConfig = config.get(i).getAsJsonObject();

            String objectType = objectConfig.get("NAME").getAsString();
            int speed = objectConfig.get("SPEED").getAsInt();
            int maximum_health = objectConfig.get("MAXIMUM_HEALTH").getAsInt();
            int current_health = objectConfig.get("CURRENT_HEALTH").getAsInt();
            int positionX = objectConfig.get("position").getAsJsonObject().get("positionX").getAsInt();
            int positionY = objectConfig.get("position").getAsJsonObject().get("positionY").getAsInt();
            switch (objectType) {
                case "MonsterZombie" -> {
                    monsters[i] = new MonsterZombie(this.GAME_PANEL);
                    monsters[i].setName("MonsterZombie");
                    setUpEnemy(monsters[i], objectType, positionX, positionY, speed, maximum_health, current_health);
                }
                case "MonsterSlime" -> {
                    monsters[i] = new MonsterSlime(this.GAME_PANEL);
                    monsters[i].setName("MonsterSlime");
                    setUpEnemy(monsters[i], objectType, positionX, positionY, speed, maximum_health, current_health);
                }
                default -> System.out.println("Wrong Object Type");
            }
        }
    }

    /**
     * <h3>setUpEnemy</h3>
     * Sets up the properties and sprites of the given monster.
     * @param monster         The monster object to be set up.
     * @param objectType      The type of the monster.
     * @param positionX       The X position of the monster.
     * @param positionY       The Y position of the monster.
     * @param speed           The speed of the monster.
     * @param maximum_health  The maximum health of the monster.
     * @param current_health  The current health of the monster.
     */
    private void setUpEnemy(Monster monster, String objectType, int positionX, int positionY, int speed, int maximum_health, int current_health) {
        monster.setName(objectType);
        monster.setMonster(true);
        monster.setENTITY_X_POSITION(positionX * 48);
        monster.setENTITY_Y_POSITION(positionY * 48);
        monster.setENTITY_SPEED(speed);
        monster.setMaximumHealth(maximum_health);
        monster.setHealth(monster.getMaximumHealth());

        // Solid Area
        monster.setSolidArea(new Rectangle(0,0,40,40));
        monster.setSolidAreaDefaultX(0);
        monster.setSolidAreaDefaultY(0);

        constructEnemySprites(monster);
    }

    /**
     * <h3>constructEnemySprites</h3>
     * Constructs the sprites based on the given monster.
     * @param monster  The monster object for which to construct the sprites.
     */
    public void constructEnemySprites(Monster monster) {
        if (Objects.equals(monster.getName(), "MonsterZombie")) {
            monster.setImageUp1(setUpSprite("Zombie/zombieUp1"));
            monster.setImageUp2(setUpSprite("Zombie/zombieUp2"));
            monster.setImageDown1(setUpSprite("Zombie/zombieDown1"));
            monster.setImageDown2(setUpSprite("Zombie/zombieDown2"));
            monster.setImageLeft1(setUpSprite("Zombie/zombieLeft1"));
            monster.setImageLeft2(setUpSprite("Zombie/zombieLeft2"));
            monster.setImageRight1(setUpSprite("Zombie/zombieRight1"));
            monster.setImageRight2(setUpSprite("Zombie/zombieRight2"));
        }
        if (Objects.equals(monster.getName(), "MonsterSlime")) {
            monster.setImageUp1(setUpSprite("Slime/slimeUp1"));
            monster.setImageUp2(setUpSprite("Slime/slimeUp2"));
            monster.setImageDown1(setUpSprite("Slime/slimeDown1"));
            monster.setImageDown2(setUpSprite("Slime/slimeDown2"));
            monster.setImageLeft1(setUpSprite("Slime/slimeLeft1"));
            monster.setImageLeft2(setUpSprite("Slime/slimeLeft2"));
            monster.setImageRight1(setUpSprite("Slime/slimeRight1"));
            monster.setImageRight2(setUpSprite("Slime/slimeRight2"));
        }
    }

    /**
     * <h3>setUpSprite</h3>
     * Sets up and returns a BufferedImage for the specified sprite image.
     * @param imagePath The path of the sprite image.
     * @return The BufferedImage of the sprite image.
     */
    public BufferedImage setUpSprite(String imagePath) {
        try { return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Monsters/" + imagePath + ".png"))); }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    // ----- GETTERS -----
    public int getTILE_SIZE() { return TILE_SIZE; }
    public Monster[] getMonsters() { return monsters; }

    // ----- SETTERS -----
    public void setMonsters(Monster[] monsters) { this.monsters = monsters; }
}
