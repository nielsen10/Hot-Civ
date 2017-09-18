package Strategies.AgingStrategies;

/**
 * Created by csdev on 9/17/17.
 */
public class BetaAgingStrategy implements AgingStrategy {
    private int year = -4000;

    @Override
    public int calculateYear() {
        if (year < -100) {
            year += 100;
        } else if (year == -100) {
            year += 99;
        } else if (year == -1) {
            year += 2;
        } else if (year == 1) {
            year += 49;
        } else if (year >= 50 && year < 1750) {
            year += 50;
        } else if (year >= 1750 && year < 1900) {
            year += 25;
        } else if (year >= 1900 && year < 1970) {
            year += 5;
        } else {
            year += 1;
        }
        return year;
    }
}
