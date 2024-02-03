package lubiku.castleQuest.Model.Parents;

import lubiku.castleQuest.View.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <h2>Object</h2>
 * Represents an object in the game.
 */
public class GameObject {

    private int OBJECT_X_POSITION, OBJECT_Y_POSITION;
    private BufferedImage objImage1, objImage2, objImage3;
    private String objName;
    private boolean objCollision = true;

    // Object Solid Area
    private Rectangle objectSolidArea = new Rectangle(0,0,48,48);
    private int solidAreaDefaultX, solidAreaDefaultY;

    /**
     * <h3>Object</h3>
     * Constructs a new Object instance.
     */
    public GameObject() { }

    /**
     * <h3>draw</h3>
     * Draws the object on the screen if it is within the camera frame.
     * @param graphics2D the graphics context used for drawing
     * @param gamePanel  the game panel containing the object and the hero
     */
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int blockSize = gamePanel.getTILE_SIZE();
        int heroScreenX = gamePanel.getHero().getSCREEN_X_POSITION();
        int heroScreenY = gamePanel.getHero().getSCREEN_Y_POSITION();
        int heroWorldX = gamePanel.getHero().getENTITY_X_POSITION();
        int heroWorldY = gamePanel.getHero().getENTITY_Y_POSITION();

        int screenX = this.getOBJECT_X_POSITION() - heroWorldX + heroScreenX;
        int screenY = this.getOBJECT_Y_POSITION() - heroWorldY + heroScreenY;

        if (isInCameraFrame(blockSize, heroWorldX, heroScreenX, heroWorldY, heroScreenY)) {
            graphics2D.drawImage(getObjImage1(), screenX, screenY, blockSize, blockSize, null);
        }
    }

    /**
     * <h3>isInCameraFrame</h3>
     * Checks if the object is within the camera frame.
     * @param blockSize    the size of a block (tile)
     * @param heroWorldX   the X position of the hero in the world
     * @param heroScreenX  the X position of the hero on the screen
     * @param heroWorldY   the Y position of the hero in the world
     * @param heroScreenY  the Y position of the hero on the screen
     * @return true if the object is within the camera frame, false otherwise
     */
    private boolean isInCameraFrame(int blockSize, int heroWorldX, int heroScreenX, int heroWorldY, int heroScreenY) {
        int objectX = getOBJECT_X_POSITION();
        int objectY = getOBJECT_Y_POSITION();

        return objectX + blockSize > heroWorldX - heroScreenX &&
                objectX - blockSize < heroWorldX + heroScreenX &&
                objectY + blockSize > heroWorldY - heroScreenY &&
                objectY - blockSize < heroWorldY + heroScreenY;
    }

    // ----- GETTERS -----
    public int getOBJECT_X_POSITION() { return OBJECT_X_POSITION; }
    public int getOBJECT_Y_POSITION() { return OBJECT_Y_POSITION; }

    // Images
    public BufferedImage getObjImage1() { return objImage1; }
    public BufferedImage getObjImage2() { return objImage2; }
    public BufferedImage getObjImage3() { return objImage3; }

    public String getObjName() { return objName; }
    public boolean isObjCollision() { return objCollision; }

    // SolidArea
    public Rectangle getObjectSolidArea() { return objectSolidArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }

    // ----- SETTERS -----
    public void setOBJECT_X_POSITION(int OBJECT_X_POSITION) { this.OBJECT_X_POSITION = OBJECT_X_POSITION; }
    public void setOBJECT_Y_POSITION(int OBJECT_Y_POSITION) { this.OBJECT_Y_POSITION = OBJECT_Y_POSITION; }
    public void setObjName(String objName) { this.objName = objName; }
    public void setObjCollision(boolean objCollision) { this.objCollision = objCollision; }

    // Images
    public void setObjImage1(BufferedImage objImage1)  { this.objImage1 = objImage1; }
    public void setObjImage2(BufferedImage objImage2) { this.objImage2 = objImage2; }
    public void setObjImage3(BufferedImage objImage3) { this.objImage3 = objImage3; }

    // SolidArea
    public void setObjectSolidArea(Rectangle objectSolidArea) { this.objectSolidArea = objectSolidArea; }
}
