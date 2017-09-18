package Strategies.AgingStrategies;

/**
 * Created by csdev on 9/17/17.
 */
public class AlphaAgingStrategy implements AgingStrategy {
    private int year = -4000;

    @Override
    public int calculateYear() {
        year += 100;
        return year;
    }
}
