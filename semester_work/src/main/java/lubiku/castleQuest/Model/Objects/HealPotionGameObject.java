package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>HealPotionObject</h2>
 * Represents a heal potion object in the game, extending the base Object class.
 */
public class HealPotionGameObject extends GameObject {

    /**
     * <h3>HealPotionObject Constructor</h3>
     * Constructs a new HealPotionObject instance.
     * Sets the object name to "HealPotion", loads the heal potion image, and sets the collision property to true.
     */
    public HealPotionGameObject() {
        this.setObjName("HealPotion");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/healPotion.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
        this.setObjCollision(true);
    }
}
