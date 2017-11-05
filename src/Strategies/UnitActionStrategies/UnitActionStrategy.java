package Strategies.UnitActionStrategies;

import hotciv.framework.Position;
import hotciv.CityImpl;
import hotciv.GameImpl;
import hotciv.UnitImpl;

import java.util.HashMap;

/**
 * Created by csdev on 9/17/17.
 */
public interface UnitActionStrategy {
    public void deployUnitAction(Position p, GameImpl game, HashMap<Position, UnitImpl> unitMap, HashMap<Position, CityImpl> cityMap);
}
