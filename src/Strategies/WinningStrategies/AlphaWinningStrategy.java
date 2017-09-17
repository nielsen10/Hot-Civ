package Strategies.WinningStrategies;

import hotciv.framework.Player;
import hotciv.standard.GameImpl;

/**
 * Created by csdev on 9/17/17.
 */
public class AlphaWinningStrategy implements WinningStrategy {
    @Override
    public Player getWinner(GameImpl game) {
        if(game.getAge() >=-3000) {
            return Player.RED;
        } else {
            return null;
        }
    }
}
