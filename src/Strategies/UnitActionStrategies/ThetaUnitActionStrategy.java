package Strategies.UnitActionStrategies;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.CityImpl;
import hotciv.GameImpl;
import hotciv.UnitImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by csdev on 10/12/17.
 */
public class ThetaUnitActionStrategy implements UnitActionStrategy {

    private ArrayList<Position> spawnArray = new ArrayList<>();

    public ThetaUnitActionStrategy() {
        spawnArray.add(new Position(0, 0));
        spawnArray.add(new Position(-1, 0));
        spawnArray.add(new Position(-1, 1));
        spawnArray.add(new Position(0, 1));
        spawnArray.add(new Position(1, 1));
        spawnArray.add(new Position(1, 0));
        spawnArray.add(new Position(1, -1));
        spawnArray.add(new Position(0, -1));
        spawnArray.add(new Position(-1, -1));
    }

    @Override
    public void deployUnitAction(Position p, GameImpl game, HashMap<Position, UnitImpl> unitMap, HashMap<Position, CityImpl> cityMap) {
        if (game.getUnitAt(p).getTypeString() == GameConstants.GALLEY) {
            cityMap.put(positionForNewCity(p, game), new CityImpl(game.getUnitAt(p).getOwner(), p));
            unitMap.remove(p, game.getUnitAt(p));
        }

    }

    public Position positionForNewCity(Position p, Game game){
        for (Position pos: spawnArray) {
            int column = p.getColumn()+pos.getColumn();
            int row = p.getRow()+pos.getRow();
            Position validPos = new Position(row,column);
            if(isValidPosition(game, validPos)) {
                return validPos;
            }
        }
        return null;
    }

    private boolean isValidPosition(Game game, Position validPos) {
        boolean isMountain = game.getTileAt(validPos).getTypeString()==GameConstants.MOUNTAINS;
        if(isMountain) return false;
        boolean isOcean = game.getTileAt(validPos).getTypeString()==GameConstants.OCEANS;
        if(isOcean) return false;
        boolean emptyTile = game.getCityAt(validPos) == null;
        if(!emptyTile) return false;
        return true;
    }
}
