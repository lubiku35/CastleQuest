package lubiku.castleQuest.Model.Parents;

import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.View.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <h2>Entity</h2>
 * The Entity class represents a game entity with position, attributes, and behaviors.
 * It provides methods to manipulate and interact with the entity.
 */
public class Entity {

    // Entity Position, Direction and Movement
    private int ENTITY_X_POSITION, ENTITY_Y_POSITION, ENTITY_SPEED;
    private String direction;

    // Entity Attributes
    private String name;
    private int maximumHealth;
    private int health;

    // Entity - Types Booleans
    private boolean isMonster = false;

    // Entity Collision Variables
    private int solidAreaDefaultX, solidAreaDefaultY;
    private Rectangle solidArea;
    private boolean isCollision = false;

    // Entity Variables for Invisibility
    private boolean isInvisible = false;
    private int invisibilityCounter = 0;

    // Entity Variables for Attacking
    private int tempScreenX, tempScreenY;
    private Rectangle attackArea = new Rectangle(0, 0, 0,0);
    private boolean isAttacking = false;

    // Entity Variables for Dying
    private boolean isAlive = true;
    private boolean isDying = false;
    private int dyingCounter = 0;

    // Entity Images / Sprites
    // Application as default setting using 2 sprites for each direction
    // Attacking Image / Sprite is only by 1
    private BufferedImage imageUp1, imageUp2, imageDown1, imageDown2, imageLeft1, imageLeft2, imageRight1, imageRight2;
    private BufferedImage imageAttackUp1, imageAttackDown1, imageAttackLeft1,  imageAttackRight1;
    private BufferedImage currentImage;
    private int spriteFrameCounter = 0;
    private int spriteNumber = 1;


    // Entity type NPC - Dialogues
    private String[] dialogues = new String[3];

    // GameWindow
    private GamePanel GAME_PANEL;


    /**
     * <h3>Entity</h3>
     * Constructs an Entity object with the specified GamePanel.
     * @param GAME_PANEL The GamePanel associated with the entity.
     */
    public Entity(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;
    }

    /**
     *  <h3>Entity</h3>
     *  Constructs an empty Entity object primary for testing purposes.
     */
    public Entity() { }

    /**
     * <h3>draw</h3>
     * Draws the entity on the graphics context.
     * @param graphics2D The graphics context to draw on.
     * @param gamePanel  The game panel.
     */
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int blockSize = gamePanel.getTILE_SIZE();
        int heroScreenX = gamePanel.getHero().getSCREEN_X_POSITION();
        int heroScreenY = gamePanel.getHero().getSCREEN_Y_POSITION();
        int heroWorldX = gamePanel.getHero().getENTITY_X_POSITION();
        int heroWorldY = gamePanel.getHero().getENTITY_Y_POSITION();

        int screenX = this.getENTITY_X_POSITION() - heroWorldX + heroScreenX;
        int screenY = this.getENTITY_Y_POSITION() - heroWorldY + heroScreenY;

        // IF IS IN CAMERA FRAME
        if( this.getENTITY_X_POSITION() + blockSize > heroWorldX - heroScreenX &&
                this.getENTITY_X_POSITION() - blockSize < heroWorldX + heroScreenX &&
                this.getENTITY_Y_POSITION() + blockSize > heroWorldY - heroScreenY &&
                this.getENTITY_Y_POSITION() - blockSize < heroWorldY + heroScreenY
        ) {
            // IMAGE SETUP BASED ON DIRECTION
            setUpCurrentSprite(this, this.getDirection());
            graphics2D.drawImage(this.getCurrentImage(), screenX, screenY, blockSize, blockSize, null);
        }
    }

    /**
     * <h3>setUpCurrentSprite</h3>
     * Sets up the current sprite image for the entity based on the specified direction.
     * @param entity    The entity.
     * @param direction The direction of movement.
     */
    public void setUpCurrentSprite(Entity entity, String direction) {
        switch (direction) {
            case "up" -> {
                if (getSpriteNumber() == 1) { entity.setCurrentImage(entity.getImageUp1()); }
                else if (getSpriteNumber() == 2) { entity.setCurrentImage(entity.getImageUp2()); }
            }
            case "down" -> {
                if (getSpriteNumber() == 1) { entity.setCurrentImage(entity.getImageDown1()); }
                else if (getSpriteNumber() == 2) { entity.setCurrentImage(entity.getImageDown2()); }
            }
            case "left" -> {
                if (getSpriteNumber() == 1) { entity.setCurrentImage(entity.getImageLeft1()); }
                else if (getSpriteNumber() == 2) { entity.setCurrentImage(entity.getImageLeft2()); }
            }
            case "right" -> {
                if (getSpriteNumber() == 1) { entity.setCurrentImage(entity.getImageRight1()); }
                else if (getSpriteNumber() == 2) { entity.setCurrentImage(entity.getImageRight2()); }
            }
        }
    }

    /**
     * <h3>setUpCurrentHeroSprite</h3>
     * Sets up the current sprite image for the hero based on the specified direction and attack state.
     * @param hero       The hero entity.
     * @param direction  The direction of movement.
     * @param isAttacking Indicates if the hero is currently attacking.
     */
    public void setUpCurrentHeroSprite(Hero hero, String direction, boolean isAttacking) {
        // For clear positioning
       hero.setTempScreenX(hero.getSCREEN_X_POSITION());
       hero.setTempScreenY(hero.getSCREEN_Y_POSITION());

        switch (direction) {
            case "up" -> {
                if (!isAttacking) {
                    // Not Attacking
                    if (getSpriteNumber() == 1) { hero.setCurrentImage(hero.getImageUp1()); }
                    else if (getSpriteNumber() == 2) { hero.setCurrentImage(hero.getImageUp2()); }
                } else {
                    // Rearrange the position to correct one
                    hero.setTempScreenY(hero.getSCREEN_Y_POSITION() - GAME_PANEL.getTILE_SIZE());
                    // Attacking
                    hero.setCurrentImage(hero.getImageAttackUp1());
                    System.out.println("Attacking Image");
                }
            }
            case "down" -> {
                if (!isAttacking) {
                    // Not Attacking
                    if (getSpriteNumber() == 1) { hero.setCurrentImage(hero.getImageDown1()); }
                    else if (getSpriteNumber() == 2) { hero.setCurrentImage(hero.getImageDown2()); }
                } else {
                    // Attacking
                    hero.setCurrentImage(hero.getImageAttackDown1());
                    System.out.println("Attacking Image");
                }
            }
            case "left" -> {
                if (!isAttacking) {
                    // Not Attacking
                    if (getSpriteNumber() == 1) { hero.setCurrentImage(hero.getImageLeft1()); }
                    else if (getSpriteNumber() == 2) { hero.setCurrentImage(hero.getImageLeft2()); }
                } else {
                    // Rearrange the position to correct one
                    hero.setTempScreenX(hero.getSCREEN_X_POSITION() - GAME_PANEL.getTILE_SIZE());
                    // Attacking
                    hero.setCurrentImage(hero.getImageAttackLeft1());
                    System.out.println("Attacking Image");
                }
            }
            case "right" -> {
                if (!isAttacking) {
                    // Not Attacking
                    if (getSpriteNumber() == 1) { hero.setCurrentImage(hero.getImageRight1()); }
                    else if (getSpriteNumber() == 2) { hero.setCurrentImage(hero.getImageRight2()); }
                } else {
                    // Attacking
                    hero.setCurrentImage(hero.getImageAttackRight1());
                }
            }
        }
    }

    /**
     * <h3>allowMoveEntity</h3>
     * Allows the entity to move based on the provided parameters.
     * If there is no collision and the ENTER key is not pressed,
     * the entity's position is updated according to the specified direction.
     *
     * @param entity        The entity to move.
     * @param isCollision   Indicates if there is a collision.
     * @param direction     The direction in which the entity should move.
     * @param isENTERPressed Indicates if the ENTER key is pressed.
     */
    public void allowMoveEntity(Entity entity, boolean isCollision, String direction, boolean isENTERPressed) {
        if (!isCollision && !isENTERPressed) {
            switch (direction) {
                case "up" -> entity.setENTITY_Y_POSITION(entity.getENTITY_Y_POSITION() - entity.getENTITY_SPEED());
                case "down" -> entity.setENTITY_Y_POSITION(entity.getENTITY_Y_POSITION() + entity.getENTITY_SPEED());
                case "left" -> entity.setENTITY_X_POSITION(entity.getENTITY_X_POSITION() - entity.getENTITY_SPEED());
                case "right" -> entity.setENTITY_X_POSITION(entity.getENTITY_X_POSITION() + entity.getENTITY_SPEED());
            }
        }
    }

    /**
     * <h3>handleInvisibility</h3>
     * Handles the invisibility of an entity.
     * If the entity is currently invisible, it increments the invisibility counter.
     * If the counter exceeds 60, the entity is set back to visible and the counter is reset.
     *
     * @param entity      The entity to handle invisibility for.
     * @param isInvisible Indicates if the entity is currently invisible.
     */
    public void handleInvisibility(Entity entity, boolean isInvisible) {
        if (isInvisible) {
            entity.setInvisibilityCounter(entity.getInvisibilityCounter() + 1);
            if (entity.getInvisibilityCounter() > 60) { entity.setInvisible(false); entity.setInvisibilityCounter(0); }
        }
    }

    // ----- GETTERS -----

    // Entity Position, Direction and Movement
    public int getENTITY_X_POSITION() { return ENTITY_X_POSITION; }
    public int getENTITY_Y_POSITION() { return ENTITY_Y_POSITION; }
    public int getENTITY_SPEED() { return ENTITY_SPEED; }
    public String getDirection() { return direction; }

    // Entity Attributes
    public String getName() { return name; }
    public int getMaximumHealth() { return maximumHealth; }
    public int getHealth() { return health; }

    // Entity - Types Booleans
    public boolean isMonster() { return isMonster; }

    // Entity Collision Variables
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
    public Rectangle getSolidArea() { return solidArea; }
    public boolean isCollision() { return isCollision; }

    // Entity Variables for Invisibility
    public boolean isInvisible() { return isInvisible; }
    public int getInvisibilityCounter() { return invisibilityCounter; }

    // Entity Variables for Attacking
    public int getTempScreenX() { return tempScreenX; }
    public int getTempScreenY() { return tempScreenY; }
    public Rectangle getAttackArea() { return attackArea; }
    public boolean isAttacking() { return isAttacking; }

    // Entity Variables for Alive / Dying
    public boolean isAlive() { return isAlive; }
    public boolean isDying() { return isDying; }
    public int getDyingCounter() { return dyingCounter; }


    // Entity Images / Sprites
    // Application as default setting using 2 sprites for each direction
    // Attacking Image / Sprite is only by 1
    public BufferedImage getImageUp1() { return imageUp1; }
    public BufferedImage getImageUp2() { return imageUp2; }
    public BufferedImage getImageDown1() { return imageDown1; }
    public BufferedImage getImageDown2() { return imageDown2; }
    public BufferedImage getImageLeft1() { return imageLeft1; }
    public BufferedImage getImageLeft2() { return imageLeft2; }
    public BufferedImage getImageRight1() { return imageRight1; }
    public BufferedImage getImageRight2() { return imageRight2; }

    public BufferedImage getImageAttackUp1() { return imageAttackUp1; }
    public BufferedImage getImageAttackDown1() { return imageAttackDown1; }
    public BufferedImage getImageAttackLeft1() { return imageAttackLeft1; }
    public BufferedImage getImageAttackRight1() { return imageAttackRight1; }

    public BufferedImage getCurrentImage() { return currentImage; }

    public int getSpriteFrameCounter() { return spriteFrameCounter; }
    public int getSpriteNumber() { return spriteNumber; }

    // Entity type NPC - Dialogues
    public String[] getDialogues() { return dialogues; }


    // ----- SETTERS -----

    // Entity Position, Direction and Movement
    public void setENTITY_X_POSITION(int ENTITY_X_POSITION) { this.ENTITY_X_POSITION = ENTITY_X_POSITION; }
    public void setENTITY_Y_POSITION(int ENTITY_Y_POSITION) { this.ENTITY_Y_POSITION = ENTITY_Y_POSITION; }
    public void setENTITY_SPEED(int ENTITY_SPEED) { this.ENTITY_SPEED = ENTITY_SPEED; }
    public void setDirection(String direction) { this.direction = direction; }

    // Entity Attributes
    public void setMaximumHealth(int maximumHealth) { this.maximumHealth = maximumHealth; }
    public void setHealth(int health) { this.health = health; }
    public void setName(String name) { this.name = name; }

    // Entity - Types Booleans
    public void setMonster(boolean monster) { isMonster = monster; }

    // Entity Collision Variables
    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }
    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }
    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }
    public void setCollision(boolean collision) { isCollision = collision; }

    // Entity Variables for Invisibility
    public void setInvisible(boolean invisible) { isInvisible = invisible; }
    public void setInvisibilityCounter(int invisibilityCounter) { this.invisibilityCounter = invisibilityCounter; }

    // Entity Variables for Attacking
    public void setTempScreenX(int tempScreenX) { this.tempScreenX = tempScreenX; }
    public void setTempScreenY(int tempScreenY) { this.tempScreenY = tempScreenY; }
    public void setAttackArea(Rectangle attackArea) { this.attackArea = attackArea; }
    public void setAttacking(boolean attacking) { isAttacking = attacking; }

    // Entity Variables for Dying
    public void setAlive(boolean alive) { isAlive = alive; }
    public void setDying(boolean dying) { isDying = dying; }
    public void setDyingCounter(int dyingCounter) { this.dyingCounter = dyingCounter; }

    // Entity Images / Sprites
    // Application as default setting using 2 sprites for each direction
    // Attacking Image / Sprite is only by 1
    public void setImageUp1(BufferedImage imageUp1) { this.imageUp1 = imageUp1; }
    public void setImageUp2(BufferedImage imageUp2) { this.imageUp2 = imageUp2; }
    public void setImageDown1(BufferedImage imageDown1) { this.imageDown1 = imageDown1; }
    public void setImageDown2(BufferedImage imageDown2) { this.imageDown2 = imageDown2; }
    public void setImageLeft1(BufferedImage imageLeft1) { this.imageLeft1 = imageLeft1; }
    public void setImageLeft2(BufferedImage imageLeft2) { this.imageLeft2 = imageLeft2; }
    public void setImageRight1(BufferedImage imageRight1) { this.imageRight1 = imageRight1; }
    public void setImageRight2(BufferedImage imageRight2) { this.imageRight2 = imageRight2; }

    public void setImageAttackUp1(BufferedImage imageAttackUp1) { this.imageAttackUp1 = imageAttackUp1; }
    public void setImageAttackDown1(BufferedImage imageAttackDown1) { this.imageAttackDown1 = imageAttackDown1; }
    public void setImageAttackLeft1(BufferedImage imageAttackLeft1) { this.imageAttackLeft1 = imageAttackLeft1; }
    public void setImageAttackRight1(BufferedImage imageAttackRight1) { this.imageAttackRight1 = imageAttackRight1; }

    public void setCurrentImage(BufferedImage currentImage) { this.currentImage = currentImage; }

    public void setSpriteFrameCounter(int spriteFrameCounter) { this.spriteFrameCounter = spriteFrameCounter; }
    public void setSpriteNumber(int spriteNumber) { this.spriteNumber = spriteNumber; }
}
