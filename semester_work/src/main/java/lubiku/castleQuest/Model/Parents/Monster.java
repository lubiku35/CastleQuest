package lubiku.castleQuest.Model.Parents;

import lubiku.castleQuest.View.GamePanel;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

/**
 * <h2>Monster</h2>
 * The Monster class represents a generic monster entity in the game.
 * It extends the Entity class and provides additional functionality specific to monsters.
 * Each monster has an action lock counter and is associated with a GamePanel.
 */
public class Monster extends Entity {

    private int actionLockCounter = 0;
    private final GamePanel GAME_PANEL;

    /**
     * <h3>Monster</h3>
     * Constructs a new Monster object with the specified GamePanel.
     * @param GAME_PANEL The GamePanel associated with the monster.
     */
    public Monster(GamePanel GAME_PANEL) {
        super(GAME_PANEL);
        this.GAME_PANEL = GAME_PANEL;

        // DEFAULT DIRECTION
        this.setDirection("right");
    }

    /**
     * <h3>drawMonster</h3>
     * Draws the specified monster on the game panel.
     * @param graphics2D The Graphics2D object used for drawing.
     * @param monster The monster to be drawn.
     */
    public void drawMonster(Graphics2D graphics2D, Monster monster) {
        if (!monster.isAlive()) {
            return;
        }

        int blockSize = GAME_PANEL.getTILE_SIZE();
        int heroScreenX = GAME_PANEL.getHero().getSCREEN_X_POSITION();
        int heroScreenY = GAME_PANEL.getHero().getSCREEN_Y_POSITION();
        int heroWorldX = GAME_PANEL.getHero().getENTITY_X_POSITION();
        int heroWorldY = GAME_PANEL.getHero().getENTITY_Y_POSITION();

        int screenX = monster.getENTITY_X_POSITION() - heroWorldX + heroScreenX;
        int screenY = monster.getENTITY_Y_POSITION() - heroWorldY + heroScreenY;

        // Check if the monster is within the camera frame
        if (!isWithinCameraFrame(monster, blockSize, heroWorldX, heroWorldY, heroScreenX, heroScreenY)) {
            return;
        }

        // Draw monster HP bar
        drawMonsterHPBar(graphics2D, monster, screenX, screenY);

        // Change opacity level based on isInvisible status
        if (monster.isInvisible()) {
            changeAlpha(graphics2D, 0.4F);
        }

        // Handle dying animation if the monster is dying
        if (monster.isDying()) {
            dyingAnimation(monster, graphics2D);
        }

        // Set up current sprite image based on direction
        monster.setUpCurrentSprite(monster, monster.getDirection());
        graphics2D.drawImage(monster.getCurrentImage(), screenX, screenY, blockSize, blockSize, null);

        // Reset alpha
        changeAlpha(graphics2D, 1F);
    }

    /**
     * <h3>drawMonsterHPBar</h3>
     * Draws the HP bar for the monster.
     *
     * @param graphics2D The Graphics2D object used for drawing.
     * @param monster The monster.
     * @param screenX The x-coordinate of the monster on the screen.
     * @param screenY The y-coordinate of the monster on the screen.
     */
    private void drawMonsterHPBar(Graphics2D graphics2D, Monster monster, int screenX, int screenY) {
        int hpBarHeight = 10;
        int hpBarWidth = GAME_PANEL.getTILE_SIZE();

        // Draw HP bar background
        graphics2D.setColor(new Color(35, 35, 35));
        graphics2D.fillRect(screenX - 1, screenY - 16, hpBarWidth + 2, hpBarHeight);

        // Calculate HP bar value
        double oneScale = (double) hpBarWidth / monster.getMaximumHealth();
        double hpBarValue = oneScale * monster.getHealth();

        // Draw HP bar
        graphics2D.setColor(new Color(255, 0, 30));
        graphics2D.fillRect(screenX, screenY - 15, (int) hpBarValue, hpBarHeight);
    }

    /**
     * <h3>isWithinCameraFrame</h3>
     * Checks if the monster is within the camera frame.
     *
     * @param monster The monster to be checked.
     * @param blockSize The size of a block on the game panel.
     * @param heroWorldX The x-coordinate of the hero in the world.
     * @param heroWorldY The y-coordinate of the hero in the world.
     * @param heroScreenX The x-coordinate of the hero on the screen.
     * @param heroScreenY The y-coordinate of the hero on the screen.
     * @return True if the monster is within the camera frame, false otherwise.
     */
    private boolean isWithinCameraFrame(Monster monster, int blockSize, int heroWorldX, int heroWorldY, int heroScreenX, int heroScreenY) {
        int monsterX = monster.getENTITY_X_POSITION();
        int monsterY = monster.getENTITY_Y_POSITION();

        return monsterX + blockSize > heroWorldX - heroScreenX &&
                monsterX - blockSize < heroWorldX + heroScreenX &&
                monsterY + blockSize > heroWorldY - heroScreenY &&
                monsterY - blockSize < heroWorldY + heroScreenY;
    }

    /**
     * <h3>updateMonster</h3>
     * Updates the state of the monster, including its actions, collisions, and interactions with other entities.
     * @param monster The monster to be updated.
     */
    public void updateMonster(Monster monster) {
        if (monster.isAlive()) {
            monster.setMonsterAction(monster);

            // Tile Collision
            monster.setCollision(false);
            GAME_PANEL.getMAIN_CONTROLLER().getCOLLISION_HANDLER().checkTile(monster);

            // NPC Collision
            GAME_PANEL.getMAIN_CONTROLLER().getCOLLISION_HANDLER().checkEntity(monster, GAME_PANEL.getNpc());

            // Hero Collision
            boolean contactHERO = GAME_PANEL.getMAIN_CONTROLLER().getCOLLISION_HANDLER().checkEntity(monster, GAME_PANEL.getHero());
            interactWithHero(contactHERO);

            allowMoveEntity(monster, monster.isCollision(), monster.getDirection(), false);

            // Handling the changes of images
            this.setSpriteFrameCounter(getSpriteFrameCounter() + 1);
            if (getSpriteFrameCounter() % 16 == 0) {
                if (getSpriteNumber() == 1) { setSpriteNumber(2); }
                else if (getSpriteNumber() == 2) { setSpriteNumber(1); }
            }
            monster.handleInvisibility(monster, monster.isInvisible());
        }
    }

    /**
     * <h3>interactWithHero</h3>
     * Interacts with the hero based on the contact status.
     * If there is contact with the hero and the hero is not invisible, the hero's health is reduced by 1 and the hero becomes invisible.
     * @param contactHERO The boolean value indicating if there is contact with the hero.
     */
    private void interactWithHero(boolean contactHERO) {
        if (contactHERO && !GAME_PANEL.getHero().isInvisible()) {
            GAME_PANEL.getHero().setHealth(GAME_PANEL.getHero().getHealth() - 1);
            GAME_PANEL.getHero().setInvisible(true);
        }
    }

    /**
     * <h3>setMonsterAction</h3>
     * Sets the action for the specified monster.
     * The action lock counter is incremented, and if it reaches the threshold of 120, the monster's direction is randomly set.
     * @param monster The monster object for which to set the action.
     */
    private void setMonsterAction(Monster monster) {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1;
            if (i <= 25) { monster.setDirection("up"); }
            else if (i <= 50) { monster.setDirection("down");}
            else if (i <=  75) { monster.setDirection("left");}
            else { monster.setDirection("right");}
            actionLockCounter = 0;
        }
    }

    /**
     * <h3>reactionToDamage</h3>
     * Performs the reaction to damage for the specified monster based on the hero's direction.
     * The monster's action lock counter is reset, and its direction is set based on its type and the hero's direction.
     * @param monster The monster object to react to damage.
     */
    public void reactionToDamage(Monster monster) {
        monster.actionLockCounter = 0;

        if (Objects.equals(monster.getName(), "MonsterZombie")){
            switch (GAME_PANEL.getHero().getDirection()) {
                case "up" -> monster.setDirection("down");
                case "down" -> monster.setDirection("up");
                case "left" -> monster.setDirection("right");
                case "right" -> monster.setDirection("left");

            }
        } else if (Objects.equals(monster.getName(), "MonsterSlime")) {
            switch (GAME_PANEL.getHero().getDirection()) {
                case "up" -> monster.setDirection("up");
                case "down" -> monster.setDirection("down");
                case "left" -> monster.setDirection("left");
                case "right" -> monster.setDirection("right");
            }
        }
    }

    /**
     * <h3>dyingAnimation</h3>
     * Performs the dying animation for the specified monster by changing the alpha value of the graphics.
     * The alpha value alternates between 0 and 1 to create a blinking effect.
     *
     * @param monster     The monster object to perform the dying animation on.
     * @param graphics2D  The Graphics2D object to modify the alpha value.
     */
    private void dyingAnimation(Monster monster, Graphics2D graphics2D) {
        monster.setDyingCounter(monster.getDyingCounter() + 1);
        int i = 5;
        if (monster.getDyingCounter() <= i) { changeAlpha(graphics2D, 0f); }
        else if (monster.getDyingCounter() > i && monster.getDyingCounter() <= i*2) { changeAlpha(graphics2D, 1f); }
        else if (monster.getDyingCounter() > i*2 && monster.getDyingCounter() <= i*3) { changeAlpha(graphics2D, 0f); }
        else if (monster.getDyingCounter() > i*3 && monster.getDyingCounter() <= i*4) { changeAlpha(graphics2D, 1f); }
        else if (monster.getDyingCounter() > i*4 && monster.getDyingCounter() <= i*5) { changeAlpha(graphics2D, 0f); }
        else if (monster.getDyingCounter() > i*5 && monster.getDyingCounter() <= i*6) { changeAlpha(graphics2D, 1f); }
        else if (monster.getDyingCounter() > i*6 && monster.getDyingCounter() <= i*7) { changeAlpha(graphics2D, 0f); }
        else if (monster.getDyingCounter() > i*7 && monster.getDyingCounter() <= i*8) { changeAlpha(graphics2D, 1f); }
        else if (monster.getDyingCounter() > i*8) {
            monster.setDying(false);
            monster.setAlive(false);
        }
    }

    /**
     * <h3>changeAlpha</h3>
     * Changes the alpha value of the specified Graphics2D object.
     *
     * @param graphics2D   The Graphics2D object to apply the alpha value to.
     * @param alphaValue   The desired alpha value. Should be a float between 0.0 and 1.0, where 0.0 represents fully transparent and 1.0 represents fully opaque.
     */
    private void changeAlpha(Graphics2D graphics2D, float alphaValue) { graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue)); }
}

