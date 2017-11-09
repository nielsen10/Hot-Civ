package Strategies.WinningStrategies;

import hotciv.framework.Player;
import hotciv.GameImpl;

/**
 * Created by csdev on 10/2/17.
 */
public class ZetaWinningStrategy implements WinningStrategy {
    WinningStrategy epsilonWin, betaWin, winState;
    public ZetaWinningStrategy(WinningStrategy epsilonWin, WinningStrategy betaWin){
        this.epsilonWin = epsilonWin;
        this.betaWin = betaWin;
        this.winState = null;
    }
    @Override
    public Player calculateWinner(GameImpl game) {
        if (game.getAge() < -2000){
            winState = betaWin;
            game.setBlueBattlesWon(0);
            game.setRedBattlesWon(0);
        }else{
            winState = epsilonWin;
        }

        return winState.calculateWinner(game);
    }
}
