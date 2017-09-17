package hotciv.framework.Strategies.AgingStrategy;

/**
 * Created by csdev on 9/17/17.
 */
public class AlphaAgingStrategy implements AgingStrategy {
    @Override
    public int endOfTurn(int year) {
        return 100;
    }
}
