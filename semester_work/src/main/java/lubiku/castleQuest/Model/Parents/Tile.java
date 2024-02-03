package lubiku.castleQuest.Model.Parents;

import java.awt.image.BufferedImage;

/**
 * <h2>Tile</h2>
 * The Tile class represents a single tile in the game.
 * It holds information about the tile image, collision status, and damage pit status.
 */
public class Tile {
    // Tile Image
    private BufferedImage tileImage;

    // Collision for Tile
    private boolean collision = false;

    /**
     * <h2>Tile</h2>
     * Constructs a new Tile object with default values.
     */
    public Tile() { }

    // ----- GETTERS -----
    public BufferedImage getTileImage() { return tileImage; }
    public boolean isCollision() { return collision; }


    // ----- SETTERS -----
    public void setTileImage(BufferedImage tileImage) { this.tileImage = tileImage; }
    public void setCollision(boolean collision) { this.collision = collision; }

}
