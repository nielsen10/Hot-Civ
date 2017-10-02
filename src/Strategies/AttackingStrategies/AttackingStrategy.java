package Strategies.AttackingStrategies;

import Strategies.DiceStrategies.DiceStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

/**
 * Created by csdev on 10/2/17.
 */
public interface AttackingStrategy {

    public boolean attack(Game game, Position from, Position to, DiceStrategy diceStrategy);
}
