package Strategies.AttackingStrategies;

import hotciv.framework.Game;
import hotciv.framework.Position;

/**
 * Created by csdev on 10/2/17.
 */
public class AlphaAttackingStrategy implements AttackingStrategy {
      @Override
    public boolean attack(Game game, Position from, Position to) {
        return true;
    }
}
