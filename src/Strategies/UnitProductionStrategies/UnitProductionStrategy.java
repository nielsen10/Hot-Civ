package Strategies.UnitProductionStrategies;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by csdev on 10/11/17.
 */
public interface UnitProductionStrategy {
    public void createUnit(CityImpl city, HashMap<Position, UnitImpl> unitMap, Game game);
}
