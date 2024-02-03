package lubiku.castleQuest.Model.Enemies;

import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>MonsterSlime</h2>
 * The MonsterSlime class represents a slime monster in the CastleQuest game.
 * It is a subclass of the Monster class and inherits its properties and behavior.
 * @see Monster
 */
public class MonsterSlime extends Monster {

    /**
     * <h3>MonsterSlime</h3>
     * Constructs a new instance of the MonsterSlime class with the specified game panel.
     * @param gamePanel The GamePanel instance associated with the monster.
     */
    public MonsterSlime(GamePanel gamePanel) {
        super(gamePanel);
    }
}
