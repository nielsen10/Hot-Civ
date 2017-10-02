package Strategies.DiceStrategies;

/**
 * Created by csdev on 10/2/17.
 */
public class FixedDiceStrategy implements DiceStrategy {
    @Override
    public int roll() {
        return 3;
    }
}
