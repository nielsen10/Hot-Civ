package Strategies.WinningStrategies;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;

/**
 * Created by csdev on 10/2/17.
 */
public class EpsilonWinningStrategy implements WinningStrategy {


    @Override
    public Player calculateWinner(GameImpl game) {
        if(game.getBlueBattlesWon() >= 3){
            return Player.BLUE;
        }
        else if(game.getRedBattlesWon() >= 3){
            return Player.RED;
        }
        return null;
    }
}
