package lubiku.castleQuest.Controller.Handlers;

import lubiku.castleQuest.Controller.MainManager;
import lubiku.castleQuest.Model.Parents.Entity;
import lubiku.castleQuest.View.GamePanel;

import java.awt.*;

/**
 * <h2>CollisionHandler</h2>
 * Handles collisions between entities, objects, and tiles in the game.
 */
public class CollisionHandler {
    private GamePanel GAME_PANEL;
    private final MainManager MAIN_MANAGER;

    /**
     * <h3>CollisionHandler</h3>
     * Constructs a CollisionHandler object with the specified MainManager and GamePanel.
     *
     * @param MAIN_MANAGER The MainManager object.
     * @param GAME_PANEL   The GamePanel object.
     */
    public CollisionHandler(MainManager MAIN_MANAGER, GamePanel GAME_PANEL) { 
        this.MAIN_MANAGER = MAIN_MANAGER;
        this.GAME_PANEL = GAME_PANEL;
    }

    // --- MONSTER SECTION ---

    /**
     * <h3>checkMonster</h3>
     * Checks for collision between an entity and monsters (NPC or hero).
     * @param entity The entity to check collision for.
     * @return The index of the monster if collision occurs, otherwise 999.
     */
    public int checkMonster(Entity entity) {
        // Relatively big index to avoid object index error
        int index = 999;
        for (int i = 0; i < this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters().length; i++) {
            if (this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[i] != null) {

                // Get Entity's solid area position
                entity.setSolidArea(new Rectangle(
                                entity.getENTITY_X_POSITION() + entity.getSolidAreaDefaultX(),
                                entity.getENTITY_Y_POSITION() + entity.getSolidAreaDefaultY(),
                                32,
                                32
                        )
                );

                // Get Monster's solid area position,
                this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[i].setSolidArea(
                        new Rectangle(
                                getMonsterXPos(i) + getMonsterDefaultSolidAreaX(i),
                                getMonsterYPos(i) + getMonsterDefaultSolidAreaY(i),
                                48,
                                48
                        )
                );

                // Check collision based on the entity's direction
                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().translate(0, -entity.getENTITY_SPEED());
                        if (checkMonsterIntersection(entity.getSolidArea(), i) ) { entity.setCollision(true);  index = i; }
                    }
                    case "down" -> {
                        entity.getSolidArea().translate(0, +entity.getENTITY_SPEED());
                        if (checkMonsterIntersection(entity.getSolidArea(), i) ) { entity.setCollision(true);  index = i; }
                    }
                    case "left" -> {
                        entity.getSolidArea().translate(-entity.getENTITY_SPEED(), 0);
                        if (checkMonsterIntersection(entity.getSolidArea(), i) ) { entity.setCollision(true); index = i; }
                    }
                    case "right" -> {
                        entity.getSolidArea().translate(+entity.getENTITY_SPEED(), 0);
                        if (checkMonsterIntersection(entity.getSolidArea(), i) ) { entity.setCollision(true); index = i; }
                    }
                }
                // Reset Values
                resetMonsterValues(entity, i);
            }
        }
        return index;
    }

    /**
     * <h3>checkMonsterIntersection</h3>
     * Checks if the entity's solid area intersects with a monster's solid area.
     * @param entitySolidArea The entity's solid area.
     * @param index           The index of the monster.
     * @return True if the collision occurs, false otherwise.
     */
    public boolean checkMonsterIntersection(Rectangle entitySolidArea, int index) { return entitySolidArea.intersects(this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidArea()); }

    /**
     * <h3>resetMonsterValues</h3>
     * Resets the values of a monster entity and its corresponding solid area.
     * Sets the solid area's position to its default position.
     * @param entity The monster entity to reset.
     * @param index  The index of the monster entity in the monster manager.
     */
    public void resetMonsterValues(Entity entity, int index) {
        resetEntityValues(entity);
        this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidArea().x = this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidAreaDefaultX();
        this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidArea().y = this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidAreaDefaultY();
    }

    /**
     * <h3>getMonsterXPos</h3>
     * Retrieves the X position of a monster entity based on its index.
     *
     * @param index The index of the monster entity in the monster manager.
     * @return The X position of the monster entity.
     */
    public int getMonsterXPos(int index) { return this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getENTITY_X_POSITION(); }

    /**
     * <h3>getMonsterYPos</h3>
     * Retrieves the Y position of a monster entity based on its index.
     *
     * @param index The index of the monster entity in the monster manager.
     * @return The Y position of the monster entity.
     */
    public int getMonsterYPos(int index) { return this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getENTITY_Y_POSITION(); }

    /**
     * <h3>getMonsterDefaultSolidAreaY</h3>
     * Retrieves the default X position of the solid area of a monster entity based on its index.
     *
     * @param index The index of the monster entity in the monster manager.
     * @return The default X position of the solid area.
     */
    public int getMonsterDefaultSolidAreaX(int index) { return this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidAreaDefaultX(); }

    /**
     * <h3>getMonsterDefaultSolidAreaY</h3>
     * Retrieves the default Y position of the solid area of a monster entity based on its index.
     *
     * @param index The index of the monster entity in the monster manager.
     * @return The default Y position of the solid area.
     */
    public int getMonsterDefaultSolidAreaY(int index) { return this.MAIN_MANAGER.getMONSTER_MANAGER().getMonsters()[index].getSolidAreaDefaultY(); }


    // --- ENTITY SECTION ---

    /**
     * <h3>checkEntity</h3>
     * Checks for contact between an entity and its target entity.
     * Sets the solid area positions for both entities based on their default positions.
     * Checks for intersection between the entity's solid area and the target entity's solid area based on the direction of the entity.
     * Resets the values of the entity and the target entity.
     *
     * @param entity The entity to check contact for.
     * @param target The target entity to check contact with.
     * @return True if there is contact, false otherwise.
     */
    public boolean checkEntity(Entity entity, Entity target) {
        boolean contact = false;

        if (target != null) {

            // Set Entity's solid area position
            if (entity.isMonster()) {
                entity.setSolidArea(new Rectangle(
                                entity.getENTITY_X_POSITION() + entity.getSolidAreaDefaultX(),
                                entity.getENTITY_Y_POSITION() + entity.getSolidAreaDefaultY(),
                                48,
                                48
                        )
                );
            } else {
                entity.setSolidArea(new Rectangle(
                                entity.getENTITY_X_POSITION() + entity.getSolidAreaDefaultX(),
                                entity.getENTITY_Y_POSITION() + entity.getSolidAreaDefaultY(),
                                35,
                                33
                        )
                );
            }

            // Set Object's solid area position,
            target.setSolidArea(
                new Rectangle(
                    target.getENTITY_X_POSITION() + target.getSolidAreaDefaultX(),
                    target.getENTITY_Y_POSITION() + target.getSolidAreaDefaultY(),
                    35,
                    33
                )
            );

            // Check collision based on the entity's direction
            switch (entity.getDirection()) {
                case "up" -> {
                    entity.getSolidArea().translate(0, -entity.getENTITY_SPEED());
                    if (checkEntityIntersection(entity.getSolidArea(), target.getSolidArea())) { entity.setCollision(true); contact = true; }
                }
                case "down" -> {
                    entity.getSolidArea().translate(0, +entity.getENTITY_SPEED());
                    if (checkEntityIntersection(entity.getSolidArea(), target.getSolidArea())) { entity.setCollision(true); contact = true; }
                }
                case "left" -> {
                    entity.getSolidArea().translate(-entity.getENTITY_SPEED(), 0);
                    if (checkEntityIntersection(entity.getSolidArea(), target.getSolidArea())) { entity.setCollision(true); contact = true; }
                }
                case "right" -> {
                    entity.getSolidArea().translate(+entity.getENTITY_SPEED(), 0);
                    if (checkEntityIntersection(entity.getSolidArea(), target.getSolidArea())) { entity.setCollision(true); contact = true; }
                }
            }

            // Reset Values
            resetEntityValues(entity, target);
        }
        return contact;
    }

    /**
     * <h3>checkEntityIntersection</h3>
     * Checks if there is any intersection between the solid areas of two entities.
     *
     * @param entitySolidArea  The solid area of the entity.
     * @param targetSolidArea  The solid area of the target entity.
     * @return True if there is an intersection, false otherwise.
     */
    public boolean checkEntityIntersection(Rectangle entitySolidArea, Rectangle targetSolidArea) { return entitySolidArea.intersects(targetSolidArea); }

    /**
     * <h3>resetEntityValues</h3>
     * Resets the values of an entity and its target entity.
     * Resets the solid area positions of both entities.
     *
     * @param entity The entity to reset.
     * @param target The target entity to reset.
     */
    public void resetEntityValues(Entity entity, Entity target) { resetEntityValues(entity); resetEntityValues(target); }


    // --- OBJECT SECTION ---

    /**
     * <h3>checkObject</h3>
     * Checks for collision between an entity and objects in the game world.
     * Returns the index of the collided object if the entity is a hero, otherwise returns a default value of 999.
     *
     * @param entity  The entity to check collision for.
     * @param isHero  True if the entity is a hero, false otherwise.
     * @return The index of the collided object if the entity is a hero, or 999 if it is not.
     */
    public int checkObject(Entity entity, boolean isHero) {
        // Relatively big index to avoid object index error
        int index = 999;
        for (int i = 0; i < this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects().length; i++) {
            if (this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[i] != null) {

                // Get Entity's solid area position
                entity.setSolidArea(new Rectangle(
                        entity.getENTITY_X_POSITION() + entity.getSolidAreaDefaultX(),
                        entity.getENTITY_Y_POSITION() + entity.getSolidAreaDefaultY(),
                        32,
                        32
                        )
                );

                // Get Object's solid area position,
                this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[i].setObjectSolidArea(
                        new Rectangle(
                                getObjectXPos(i) + getObjectDefaultSolidAreaX(i),
                                getObjectYPos(i) + getObjectDefaultSolidAreaY(i),
                                48,
                                48
                        )
                );

                // Check collision based on the entity's direction
                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().translate(0, -entity.getENTITY_SPEED());
                        if (checkObjectIntersection(entity.getSolidArea(), i) && checkObjectCollision(i)) { entity.setCollision(true); if (isHero) { index = i; } }
                    }
                    case "down" -> {
                        entity.getSolidArea().translate(0, +entity.getENTITY_SPEED());
                        if (checkObjectIntersection(entity.getSolidArea(), i) && checkObjectCollision(i)) { entity.setCollision(true); if (isHero) { index = i; } }
                    }
                    case "left" -> {
                        entity.getSolidArea().translate(-entity.getENTITY_SPEED(), 0);
                        if (checkObjectIntersection(entity.getSolidArea(), i) && checkObjectCollision(i)) { entity.setCollision(true); if (isHero) { index = i; } }
                    }
                    case "right" -> {
                        entity.getSolidArea().translate(+entity.getENTITY_SPEED(), 0);
                        if (checkObjectIntersection(entity.getSolidArea(), i) && checkObjectCollision(i)) { entity.setCollision(true); if (isHero) { index = i; } }
                    }
                }
                // Reset Values
                resetObjectValues(entity, i);
            }
        }
        return index;
    }

    /**
     * <h3>checkObjectIntersection</h3>
     * Checks if there is any intersection between the solid area of an entity and an object at the specified index.
     *
     * @param entitySolidArea The solid area of the entity.
     * @param index           The index of the object to check intersection with.
     * @return True if there is an intersection, false otherwise.
     */
    public boolean checkObjectIntersection(Rectangle entitySolidArea, int index) { return entitySolidArea.intersects(this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getObjectSolidArea()); }

    /**
     * <h3>checkObjectCollision</h3>
     * Checks if there is a collision with an object at the specified index.
     *
     * @param index The index of the object to check collision with.
     * @return True if there is a collision, false otherwise.
     */
    public boolean checkObjectCollision(int index) { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].isObjCollision(); }

    /**
     * <h3>resetObjectValues</h3>
     * Resets the values of an entity and an object at the specified index.
     * Resets the entity's values and restores the object's solid area position to its default position.
     *
     * @param entity The entity to reset.
     * @param index  The index of the object to reset.
     */
    public void resetObjectValues(Entity entity, int index) {
        resetEntityValues(entity);
        this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getObjectSolidArea().x = this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getSolidAreaDefaultX();
        this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getObjectSolidArea().y = this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getSolidAreaDefaultY();
    }

    /**
     * <h3>getObjectXPos</h3>
     * Returns the X position of the object at the specified index.
     *
     * @param index The index of the object.
     * @return The X position of the object.
     */
    public int getObjectXPos(int index) { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getOBJECT_X_POSITION(); }

    /**
     * <h3>getObjectYPos</h3>
     * Returns the Y position of the object at the specified index.
     *
     * @param index The index of the object.
     * @return The Y position of the object.
     */
    public int getObjectYPos(int index) { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getOBJECT_Y_POSITION(); }

    /**
     * <h3>getObjectDefaultSolidAreaX</h3>
     * Returns the default X position of the solid area of the object at the specified index.
     *
     * @param index The index of the object.
     * @return The default X position of the object's solid area.
     */
    public int getObjectDefaultSolidAreaX(int index) { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getSolidAreaDefaultX(); }

    /**
     * <h3>getObjectDefaultSolidAreaY</h3>
     * Returns the default Y position of the solid area of the object at the specified index.
     *
     * @param index The index of the object.
     * @return The default Y position of the object's solid area.
     */
    public int getObjectDefaultSolidAreaY(int index) { return this.MAIN_MANAGER.getOBJECT_MANAGER().getObjects()[index].getSolidAreaDefaultY(); }


    // --- TILE SECTION ---

    /**
     * <h3>checkTile</h3>
     * Checks for collision between the entity and tiles in the game world.
     * @param entity The entity to check tile collision for.
     */
    public void checkTile(Entity entity) {

        // Calculate the entity's position in the world coordinates
        int entityLeftWorldX = entity.getENTITY_X_POSITION() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getENTITY_X_POSITION() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getENTITY_Y_POSITION() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getENTITY_Y_POSITION() + entity.getSolidArea().y + entity.getSolidArea().height;

        // Calculate the column and row indices of the tiles that the entity overlaps with
        int entityLeftCol = entityLeftWorldX / this.GAME_PANEL.getTILE_SIZE();
        int entityRightCol = entityRightWorldX / this.GAME_PANEL.getTILE_SIZE();
        int entityTopRow = entityTopWorldY / this.GAME_PANEL.getTILE_SIZE();
        int entityBottomRow = entityBottomWorldY / this.GAME_PANEL.getTILE_SIZE();

        int[][] mapBlockNumbers = this.MAIN_MANAGER.getTILE_MANAGER().getMapTileNumbers();

        switch (entity.getDirection()) {
            case "up" -> this.handleUpTileCollision(entity, mapBlockNumbers, entityTopWorldY, entityLeftCol, entityRightCol);
            case "down" -> this.handleDownTileCollision(entity, mapBlockNumbers, entityBottomWorldY, entityLeftCol, entityRightCol);
            case "left" ->  this.handleLeftTileCollision(entity, mapBlockNumbers, entityLeftWorldX, entityTopRow, entityBottomRow);
            case "right" -> this.handleRightTileCollision(entity, mapBlockNumbers, entityRightWorldX, entityTopRow, entityBottomRow);
        }
    }

    // Tile Collision Handlers

    /**
     * <h3>handleUpTileCollision</h3>
     * Handles the tile collision when the entity is moving up.
     *
     * @param entity            The entity involved in the collision.
     * @param mapBlockNumbers   The 2D array representing the tile map.
     * @param entityTopWorldY   The top Y-coordinate of the entity in the world.
     * @param entityLeftCol     The left column index of the entity in the tile map.
     * @param entityRightCol    The right column index of the entity in the tile map.
     */
    private void handleUpTileCollision(Entity entity, int[][] mapBlockNumbers, int entityTopWorldY, int entityLeftCol, int entityRightCol) {
        int entityTopRow = (entityTopWorldY - entity.getENTITY_SPEED()) / this.GAME_PANEL.getTILE_SIZE();
        int blockOne = mapBlockNumbers[entityLeftCol][entityTopRow];
        int blockTwo = mapBlockNumbers[entityRightCol][entityTopRow];
        checkCollision(entity, blockOne, blockTwo);
    }

    /**
     * <h3>handleDownTileCollision</h3>
     * Handles the tile collision when the entity is moving up.
     *
     * @param entity               The entity involved in the collision.
     * @param mapBlockNumbers      The 2D array representing the tile map.
     * @param entityBottomWorldY   The bottom Y-coordinate of the entity in the world.
     * @param entityLeftCol        The left column index of the entity in the tile map.
     * @param entityRightCol       The right column index of the entity in the tile map.
     */
    private void handleDownTileCollision(Entity entity, int[][] mapBlockNumbers, int entityBottomWorldY, int entityLeftCol, int entityRightCol) {
        int entityBottomRow = (entityBottomWorldY + entity.getENTITY_SPEED()) / this.GAME_PANEL.getTILE_SIZE();
        int blockOne = mapBlockNumbers[entityLeftCol][entityBottomRow];
        int blockTwo = mapBlockNumbers[entityRightCol][entityBottomRow];
        checkCollision(entity, blockOne, blockTwo);
    }
    /**
     * <h3>handleLeftTileCollision</h3>
     * Handles the tile collision when the entity is moving up.
     *
     * @param entity            The entity involved in the collision.
     * @param mapBlockNumbers   The 2D array representing the tile map.
     * @param entityLeftWorldX  The left X-coordinate of the entity in the world.
     * @param entityTopRow      The top row index of the entity in the tile map.
     * @param entityBottomRow   The bottom row index of the entity in the tile map.
     */
    private void handleLeftTileCollision(Entity entity, int[][] mapBlockNumbers, int entityLeftWorldX, int entityTopRow, int entityBottomRow) {
        int entityLeftCol = (entityLeftWorldX - entity.getENTITY_SPEED()) / this.GAME_PANEL.getTILE_SIZE();
        int blockOne = mapBlockNumbers[entityLeftCol][entityTopRow];
        int blockTwo = mapBlockNumbers[entityLeftCol][entityBottomRow];
        checkCollision(entity, blockOne, blockTwo);
    }
    /**
     * <h3>handleRightTileCollision</h3>
     * Handles the tile collision when the entity is moving up.
     *
     * @param entity             The entity involved in the collision.
     * @param mapBlockNumbers    The 2D array representing the tile map.
     * @param entityRightWorldX  The right X-coordinate of the entity in the world.
     * @param entityTopRow       The top row index of the entity in the tile map.
     * @param entityBottomRow    The bottom row index of the entity in the tile map.
     */
    private void handleRightTileCollision(Entity entity, int[][] mapBlockNumbers, int entityRightWorldX, int entityTopRow, int entityBottomRow) {
        int entityRightCol = (entityRightWorldX + entity.getENTITY_SPEED()) / this.GAME_PANEL.getTILE_SIZE();
        int blockOne = mapBlockNumbers[entityRightCol][entityTopRow];
        int blockTwo = mapBlockNumbers[entityRightCol][entityBottomRow];
        checkCollision(entity, blockOne, blockTwo);
    }

    /**
     * <h3>checkCollision</h3>
     * Checks the collision between the entity and the specified tiles.
     *
     * @param entity   The entity involved in the collision.
     * @param blockOne The first block index to check collision with.
     * @param blockTwo The second block index to check collision with.
     */
    private void checkCollision(Entity entity, int blockOne, int blockTwo) {
        if (this.MAIN_MANAGER.getTILE_MANAGER().getTiles()[blockOne].isCollision() ||
                this.MAIN_MANAGER.getTILE_MANAGER().getTiles()[blockTwo].isCollision()
        ) { entity.setCollision(true); }
    }

    // GLOBALS

    /**
     * <h3>resetEntityValues</h3>
     * Resets the values of the entity.
     * @param entity The entity to reset the values for.
     */
    public void resetEntityValues(Entity entity) {
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
    }

    public void setGAME_PANEL(GamePanel GAME_PANEL) { this.GAME_PANEL = GAME_PANEL; }
}
