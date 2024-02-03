package lubiku.castleQuest.Model.Objects;

import lubiku.castleQuest.Model.Parents.GameObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>MapObject</h2>
 * Represents a map object in the game, extending the base Object class.
 */
public class MapGameObject extends GameObject {

    /**
     * <h3>MapObject Constructor</h3>
     * Constructs a new MapObject instance.
     * Sets the object name to "Map" and loads the map image.
     */
    public MapGameObject() {
        this.setObjName("Map");
        try { this.setObjImage1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Objects/map.png")))); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
