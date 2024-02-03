package lubiku.castleQuest.Controller.Managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lubiku.castleQuest.Configuration.Configurators.Configurator;
import lubiku.castleQuest.Controller.Handlers.KeyHandler;
import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.View.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>HeroManager</h2>
 * The HeroManager class is responsible for managing the hero character in the game.
 * It handles the configuration of the hero's attributes and sprites.
 * The class implements the Configurator interface to enable configuration from JSON data.
 */
public class HeroManager implements Configurator {
    private Hero hero;
    private int TILE_SIZE;

    /**
     * <h3>HeroManager</h3>
     * Constructs a HeroManager object with the specified hero.
     * @param hero the hero character to be managed
     * @see Hero
     */
    public HeroManager(Hero hero) {
        this.hero = hero;
    }

    /**
     * <h3>configureJsonObject</h3>
     * Configures the Hero object based on the provided JsonObject.
     * @param config the configuration JsonObject containing the hero properties
     */
    @Override
    public void configureJsonObject(JsonObject config) {
        if (!config.get("HERO_NAME").getAsString().equals("")) { this.hero.setName(config.get("HERO_NAME").getAsString()); }
        this.hero.setENTITY_X_POSITION(config.get("HERO_X_POSITION").getAsInt() * this.getTILE_SIZE());
        this.hero.setENTITY_Y_POSITION(config.get("HERO_Y_POSITION").getAsInt() * this.getTILE_SIZE());
        this.hero.setENTITY_SPEED(config.get("HERO_SPEED").getAsInt());
        this.hero.setMaximumHealth(config.get("MAXIMUM_HEALTH").getAsInt());
        this.hero.setHealth(config.get("CURRENT_HEALTH").getAsInt());
        this.hero.setHasKey(config.get("HAS_KEY").getAsInt());
        this.hero.setHasSpecialKey(config.get("HAS_SPECIAL_KEY").getAsInt());
        this.hero.setHasMap(config.get("HAS_MAP").getAsBoolean());
        this.hero.setHasMap(config.get("HAS_ENCHANTED_SWORD").getAsBoolean());
    }
    @Override
    public void configureJsonArray(JsonArray configurator) { }

    /**
     * <h3>constructHeroSprites</h3>
     * Constructs the hero's normal movement sprites.
     * Sets up the appropriate sprite images for each direction of movement.
     */
    public void constructHeroSprites() {
        this.hero.setImageUp1(setUpSprite("heroUp1", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageUp2(setUpSprite("heroUp2", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageDown1(setUpSprite("heroDown1", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageDown2(setUpSprite("heroDown2", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageLeft1(setUpSprite("heroLeft1", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageLeft2(setUpSprite("heroLeft2", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageRight1(setUpSprite("heroRight1", this.getTILE_SIZE(), this.getTILE_SIZE()));
        this.hero.setImageRight2(setUpSprite("heroRight2", this.getTILE_SIZE(), this.getTILE_SIZE()));
    }

    /**
     * <h3>constructHeroAttackingSprites</h3>
     * Constructs the hero's attacking sprites.
     * Sets up the appropriate sprite images for each attacking direction.
     */
    public void constructHeroAttackingSprites() {
        this.hero.setImageAttackUp1(setUpSprite("Attack/heroAttackUp", this.getTILE_SIZE(), this.getTILE_SIZE() * 2));
        this.hero.setImageAttackDown1(setUpSprite("Attack/heroAttackDown", this.getTILE_SIZE(), this.getTILE_SIZE() * 2));
        this.hero.setImageAttackLeft1(setUpSprite("Attack/heroAttackLeft", this.getTILE_SIZE() * 2, this.getTILE_SIZE()));
        this.hero.setImageAttackRight1(setUpSprite("Attack/heroAttackRight",  this.getTILE_SIZE() * 2, this.getTILE_SIZE()));
    }

    /**
     * <h3>setUpSprite</h3>
     * Sets up a sprite image based on the provided image path and dimensions.
     * @param imagePath the path to the sprite image
     * @param imageWidth the desired width of the scaled image
     * @param imageHeight the desired height of the scaled image
     * @return the scaled BufferedImage
     */
    public BufferedImage setUpSprite(String imagePath, int imageWidth, int imageHeight) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Hero/" + imagePath + ".png")));
            BufferedImage scaledImage = new BufferedImage(imageWidth, imageHeight, image.getType());
            Graphics2D g = scaledImage.createGraphics();
            g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
            g.dispose();
            return scaledImage;
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    // ----- GETTERS -----
    public int getTILE_SIZE() { return TILE_SIZE; }

    // ----- SETTERS -----
    public void setHero(Hero hero) { this.hero = hero; }
    public void setTILE_SIZE(int TILE_SIZE) { this.TILE_SIZE = TILE_SIZE; }
}

