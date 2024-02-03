package lubiku.castleQuest.Controller.Handlers;

import lubiku.castleQuest.Controller.Handlers.Utils.EventRectangle;
import lubiku.castleQuest.Model.Hero;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>EventHandler</h2>
 * The EventHandler class handles events and event collisions in the game.
 * It is responsible for managing event rectangles, checking for events, and handling event interactions.
 */
public class EventHandler {
    private final GamePanel GAME_PANEL;
    private final EventRectangle[][] eventRectangle;
    private int previousEventX, previousEventY;
    private boolean canTouchEvent;

    /**
     * <h3>EventHandler</h3>
     * Creates an instance of EventHandler to handle events and event collisions in the game.
     * @param GAME_PANEL The GamePanel associated with the event handler.
     */
    public EventHandler(GamePanel GAME_PANEL) {
        this.GAME_PANEL = GAME_PANEL;
        this.eventRectangle = new EventRectangle[this.getMAX_WORLD_COLUMNS()][this.getMAX_WORLD_ROWS()];
        this.setUpEventRectangle(this.eventRectangle);
    }

    /**
     * <h3>setUpEventRectangle</h3>
     * Sets up event rectangles for the game world.
     * @param eventRectangle The 2D array of EventRectangles to set up.
     */
    private void setUpEventRectangle(EventRectangle[][] eventRectangle) {
        int column = 0;
        int row = 0;

        // Loop through the eventRectangle array
        while ((column < this.getMAX_WORLD_COLUMNS()) && (row < this.getMAX_WORLD_ROWS())) {
            eventRectangle[column][row] = new EventRectangle();

            // Set the properties of each EventRectangle
            eventRectangle[column][row].x = 23;
            eventRectangle[column][row].y = 23;
            eventRectangle[column][row].width = 2;
            eventRectangle[column][row].height = 2;

            // Set the default x and y values
            eventRectangle[column][row].setEventRectangleDefaultX(eventRectangle[column][row].x);
            eventRectangle[column][row].setEventRectangleDefaultY(eventRectangle[column][row].y);

            // Move to the next column or row
            column++;
            if (column == this.getMAX_WORLD_COLUMNS()) { column = 0; row++; }
        }
        GAME_PANEL.getMAIN_CONTROLLER().getLOGGER().log("SetUp for EventRectangles success");
    }

    /**
     * <h3>checkEvent</h3>
     * Checks for events and performs event interactions if necessary.
     */
    public void checkEvent() {
        // Check if the hero entity is more than tile away from the last event
        int xDistance = Math.abs(this.getHero().getENTITY_X_POSITION() - this.getPreviousEventX());
        int yDistance = Math.abs(this.getHero().getENTITY_Y_POSITION() - this.getPreviousEventY());
        int distance = Math.max(xDistance, yDistance);

        if (distance > this.GAME_PANEL.getTILE_SIZE()) { this.setCanTouchEvent(true);}

        if (this.isCanTouchEvent()) {
            for (int y = 0; y < getMapTileNumbers().length; y++) {
                for (int x = 0; x < getMapTileNumbers()[y].length; x++) {
                    if (getMapTileNumbers()[y][x] == 5) {
                        if (hitEvent(y, x, "any")) { damagePit(1, 1, this.GAME_PANEL.getGameState()); }
                    }
                }
            }
        }
    }

    /**
     * <h3>damagePit</h3>
     * Damages the hero by the damage pit at the specified column and row, and updates the game state.
     * @param column     The column of the pit.
     * @param row        The row of the pit.
     * @param gameState  The new game state.
     */
    private void damagePit(int column, int row, int gameState) {
        this.GAME_PANEL.setGameState(gameState);
        this.getHero().setHealth(this.getHero().getHealth() - 1);
        this.getHero().setInvisible(true);
        this.setCanTouchEvent(false);
    }


    /**
     * <h3>hitEvent</h3>
     * Checks if the hero has hit an event at the specified column and row, in the required direction.
     * @param eventColumn        The column of the event.
     * @param eventRow           The row of the event.
     * @param requiredDirection  The required direction of the hero.
     * @return                   True if the hero hit the event, false otherwise.
     */
    public boolean hitEvent(int eventColumn, int eventRow, String requiredDirection) {
        boolean hit = false;

        // Modify the hero and eventRectangle positions for intersection check
        this.getHero().getSolidArea().x = this.getHero().getENTITY_X_POSITION() + this.getHero().getSolidArea().x;
        this.getHero().getSolidArea().y = this.getHero().getENTITY_Y_POSITION() + this.getHero().getSolidArea().y;
        this.eventRectangle[eventColumn][eventRow].x = eventColumn * this.GAME_PANEL.getTILE_SIZE() + this.eventRectangle[eventColumn][eventRow].x;
        this.eventRectangle[eventColumn][eventRow].y = eventRow * this.GAME_PANEL.getTILE_SIZE() + this.eventRectangle[eventColumn][eventRow].y;

        // Check for intersection between hero and eventRectangle, and if the event hasn't occurred before
        if  (
            this.getHero().getSolidArea().intersects(this.eventRectangle[eventColumn][eventRow]) &&
            !this.eventRectangle[eventColumn][eventRow].wasEvent()) {

            // Check if the hero's direction matches the required direction or if any direction is accepted
            if (this.getHero().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;
                this.setPreviousEventX(this.getHero().getENTITY_X_POSITION());
                this.setPreviousEventY(this.getHero().getENTITY_Y_POSITION());
            }
        }

        // Reset the modified positions back to their default values
        this.getHero().getSolidArea().x = this.getHero().getSolidAreaDefaultX();
        this.getHero().getSolidArea().y = this.getHero().getSolidAreaDefaultY();
        this.eventRectangle[eventColumn][eventRow].x = this.eventRectangle[eventColumn][eventRow].getEventRectangleDefaultX();
        this.eventRectangle[eventColumn][eventRow].y = this.eventRectangle[eventColumn][eventRow].getEventRectangleDefaultY();

        return hit;
    }


    // ----- GETTERS -----
    public int getPreviousEventX() { return previousEventX; }
    public int getPreviousEventY() { return previousEventY; }
    public boolean isCanTouchEvent() { return canTouchEvent; }

    // Globals
    private int[][] getMapTileNumbers() { return this.GAME_PANEL.getMAIN_CONTROLLER().getMAIN_MANAGER().getTILE_MANAGER().getMapTileNumbers(); }
    private int getMAX_WORLD_COLUMNS() { return this.GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_COLUMNS(); }
    private int getMAX_WORLD_ROWS() { return this.GAME_PANEL.getMAIN_CONTROLLER().getMAX_WORLD_ROWS(); }
    private Hero getHero() { return this.GAME_PANEL.getHero(); }

    // ----- SETTERS -----
    public void setPreviousEventX(int previousEventX) { this.previousEventX = previousEventX; }
    public void setPreviousEventY(int previousEventY) { this.previousEventY = previousEventY; }
    public void setCanTouchEvent(boolean canTouchEvent) { this.canTouchEvent = canTouchEvent; }
}
