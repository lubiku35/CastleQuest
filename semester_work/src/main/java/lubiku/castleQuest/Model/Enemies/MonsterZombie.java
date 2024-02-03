package lubiku.castleQuest.Model.Enemies;

import lubiku.castleQuest.Model.Parents.Monster;
import lubiku.castleQuest.View.GamePanel;

/**
 * <h2>MonsterZombie</h2>
 * The MonsterZombie class represents a zombie monster in the CastleQuest game.
 * It is a subclass of the Monster class and inherits its properties and behavior.
 * @see Monster
 */
public class MonsterZombie extends Monster {

    /**
     * <h3>MonsterZombie</h3>
     * Constructs a new instance of the MonsterZombie class with the specified game panel.
     * @param gamePanel The GamePanel instance associated with the monster.
     */
    public MonsterZombie(GamePanel gamePanel) {
        super(gamePanel);
    }

}
