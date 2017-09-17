package hotciv.framework.Strategies.AgingStrategy;

/**
 * Created by csdev on 9/17/17.
 */
public class BetaAgingStrategy implements AgingStrategy {
    @Override
    public int endOfTurn(int year) {
        return 100;
    }
}
