package Strategies.AgingStrategies;

import hotciv.standard.GameImpl;

/**
 * Created by csdev on 9/17/17.
 */
public class BetaAgingStrategy implements AgingStrategy {
    @Override
    public int endOfTurn(GameImpl game) {
        if(game.getAge() < -100) {
            return 100;
        } else if(game.getAge() == -100) {
            return 99;
        } else if(game.getAge() == -1) {
            return 2;
        } else if(game.getAge() == 1) {
            return 49;
        } else if(game.getAge() >= 50 && game.getAge() < 1750){
            return 50;
        } else if(game.getAge() >= 1750 && game.getAge() < 1900){
            return 25;
        } else if(game.getAge() >= 1900 && game.getAge() < 1970){
            return 5;
        } else {
            return 1;
        }
    }
}
