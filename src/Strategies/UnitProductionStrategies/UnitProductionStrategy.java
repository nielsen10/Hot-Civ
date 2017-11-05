package Strategies.UnitProductionStrategies;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.CityImpl;
import hotciv.UnitImpl;

import java.util.HashMap;

/**
 * Created by csdev on 10/11/17.
 */
public interface UnitProductionStrategy {
    public void createUnit(CityImpl city, HashMap<Position, UnitImpl> unitMap, Game game);
}
