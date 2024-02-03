package lubiku.castleQuest.Controller.Managers;

import lubiku.castleQuest.Model.Parents.Tile;
import lubiku.castleQuest.View.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * <h2>TileManager</h2>
 * The TileManager class is responsible for managing and drawing tiles in the game.
 * It has a constructor that takes a GamePanel object as a parameter and initializes the Tile array.
 * */
public class TileManager {
    private final GamePanel GAME_PANEL;
    private final Tile[] tiles;

    private int[][] mapTileNumbers;

    /**
     * <h3>TileManager</h3>
     * Constructs a TileManager object.
     * @param GAME_PANEL The GamePanel object associated with the TileManager.
     * @see GamePanel
     */
    public TileManager(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;
        this.tiles = new Tile[10];
    }

    /**
     * <h3>createTileImages</h3>
     * Creates the block images by loading them from their respective paths.
     * Initializes the Tile array with the loaded images and sets the collision value for each tile.
     */
    public void createTileImages() {
        try {
            this.tiles[0] = new Tile();
            this.tiles[0].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/stoneFloor1.png"))));

            this.tiles[1] = new Tile();
            this.tiles[1].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/stoneFloor2.png"))));

            this.tiles[2] = new Tile();
            this.tiles[2].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/stoneFloor3.png"))));

            this.tiles[3] = new Tile();
            this.tiles[3].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/mugFloor.png"))));

            this.tiles[4] = new Tile();
            this.tiles[4].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/wall.png"))));
            this.tiles[4].setCollision(true);

            this.tiles[5] = new Tile();
            this.tiles[5].setTileImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sprites/Tiles/floorSpikes.png"))));
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * <h3>configureMapTileNumbers</h3>
     * Configures the map tile numbers based on the maximum world columns and rows.
     * @param MAX_WORLD_COLUMNS The maximum number of world columns.
     * @param MAX_WORLD_ROWS    The maximum number of world rows.
     */
    public void configureMapTileNumbers(int MAX_WORLD_COLUMNS, int MAX_WORLD_ROWS) { this.mapTileNumbers = new int[MAX_WORLD_COLUMNS][MAX_WORLD_ROWS]; }

    /**
     * <h3>drawTiles</h3>
     * Draws the tiles on the screen.
     * Considers the hero's position and the corresponding screen and world coordinates.
     * @param graphics2D The Graphics2D object used for drawing.
     */
    public void drawTiles(Graphics2D graphics2D) {
        int blockSize = GAME_PANEL.getTILE_SIZE();
        int heroScreenX = GAME_PANEL.getHero().getSCREEN_X_POSITION();
        int heroScreenY = GAME_PANEL.getHero().getSCREEN_Y_POSITION();
        int heroWorldX = GAME_PANEL.getHero().getENTITY_X_POSITION();
        int heroWorldY = GAME_PANEL.getHero().getENTITY_Y_POSITION();


        for (int row = 0; row < GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_ROWS(); row++) {
            for (int column = 0; column < GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_COLUMNS(); column++) {
                int blockNumber = this.getMapTileNumbers()[column][row];
                Tile block = this.tiles[blockNumber];

                int worldX = column * blockSize;
                int worldY = row * blockSize;

                int screenX = worldX - heroWorldX + heroScreenX;
                int screenY = worldY - heroWorldY + heroScreenY;

                graphics2D.drawImage(block.getTileImage(), screenX, screenY, blockSize, blockSize, null);
            }
        }
    }

    // ----- GETTERS -----
    public int[][] getMapTileNumbers() { return mapTileNumbers; }
    public Tile[] getTiles() { return tiles; }

    // ----- SETTERS -----
    public void setMapTileNumbers(int[][] mapTileNumbers) { this.mapTileNumbers = mapTileNumbers; }
}
