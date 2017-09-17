package Strategies.AgingStrategies;

import hotciv.standard.GameImpl;

/**
 * Created by csdev on 9/17/17.
 */
public class AlphaAgingStrategy implements AgingStrategy {
    @Override
    public int endOfTurn(GameImpl year) {
        return 100;
    }
}
