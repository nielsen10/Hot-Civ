package Strategies.WinningStrategies;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;

/**
 * Created by csdev on 9/17/17.
 */
public class BetaWinningStrategy implements WinningStrategy {
    @Override
    public Player getWinner(GameImpl game) {
        if (game.getCityAt(new Position(1,1)).getOwner() == game.getCityAt(new Position(4,1)).getOwner()){
            return game.getCityAt(new Position(1,1)).getOwner();
        }
        return null;
    }
}
