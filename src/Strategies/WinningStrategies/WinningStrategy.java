package Strategies.WinningStrategies;

import hotciv.framework.Player;
import hotciv.GameImpl;

/**
 * Created by csdev on 9/17/17.
 */
public interface WinningStrategy {

    public Player calculateWinner(GameImpl game);
}
