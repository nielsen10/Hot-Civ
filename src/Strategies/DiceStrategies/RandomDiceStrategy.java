package Strategies.DiceStrategies;

/**
 * Created by csdev on 10/2/17.
 */
public class RandomDiceStrategy implements DiceStrategy {
    @Override
    public int roll() {
        return (int)(6.0 * Math.random()) + 1;
    }
}
