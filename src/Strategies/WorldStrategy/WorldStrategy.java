package Strategies.WorldStrategy;

import hotciv.framework.Position;
import hotciv.CityImpl;
import hotciv.GameImpl;
import hotciv.TileImpl;
import hotciv.UnitImpl;

import java.util.HashMap;

/**
 * Created by csdev on 9/18/17.
 */
public interface WorldStrategy {

    public void buildWorld(GameImpl game, HashMap<Position, UnitImpl> unitMap, HashMap<Position, TileImpl> tileMap, HashMap<Position, CityImpl> cityMap);
}
