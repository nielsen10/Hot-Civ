package Strategies.WorldStrategy;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

/**
 * Created by csdev on 9/18/17.
 */
public class AlphaWorldStrategy implements WorldStrategy {

    private TileImpl ocean = new TileImpl(new Position(1,0), GameConstants.OCEANS);
    private TileImpl mountain = new TileImpl(new Position(2,2), GameConstants.MOUNTAINS);
    private TileImpl hills = new TileImpl(new Position(0,1 ), GameConstants.HILLS);
    private UnitImpl archer = new UnitImpl(new Position(2,0), GameConstants.ARCHER, Player.RED);
    private UnitImpl legion = new UnitImpl(new Position(3,2), GameConstants.LEGION, Player.BLUE);
    private UnitImpl settler = new UnitImpl(new Position(4,3), GameConstants.SETTLER, Player.RED);
    private CityImpl cityRed = new CityImpl(Player.RED, new Position(1,1));
    private CityImpl cityBlue = new CityImpl(Player.BLUE, new Position(4,1));


    @Override
    public void buildWorld(GameImpl game, HashMap<Position, UnitImpl> unitMap, HashMap<Position, TileImpl> tileMap, HashMap<Position, CityImpl> cityMap) {
        tileMap.put(ocean.getPosition(), ocean);
        tileMap.put(mountain.getPosition(), mountain);
        tileMap.put(hills.getPosition(), hills);
        unitMap.put(archer.getPosition(), archer);
        unitMap.put(legion.getPosition(), legion);
        unitMap.put(settler.getPosition(), settler);
        cityMap.put(cityRed.getPosition(), cityRed);
        cityMap.put(cityBlue.getPosition(), cityBlue);
    }
}
