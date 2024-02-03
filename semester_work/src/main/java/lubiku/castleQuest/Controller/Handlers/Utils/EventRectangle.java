package lubiku.castleQuest.Controller.Handlers.Utils;

import java.awt.*;
/**
 * <h2>EventRectangle</h2>
 * The EventRectangle class extends the Rectangle class and represents a rectangular area in a game that is associated with an event.
 * It introduces additional properties and functionality specific to handling events within the game.
 * */
public class EventRectangle extends Rectangle {
    private int eventRectangleDefaultX = 23;
    private int eventRectangleDefaultY = 23;
    private boolean wasEvent = false;

    // ----- GETTERS -----
    public int getEventRectangleDefaultX() { return eventRectangleDefaultX; }
    public int getEventRectangleDefaultY() { return eventRectangleDefaultY; }

    public boolean wasEvent() { return wasEvent; }

    // ----- SETTERS -----
    public void setEventRectangleDefaultX(int eventRectangleDefaultX) { this.eventRectangleDefaultX = eventRectangleDefaultX; }
    public void setEventRectangleDefaultY(int eventRectangleDefaultY) { this.eventRectangleDefaultY = eventRectangleDefaultY; }
}
