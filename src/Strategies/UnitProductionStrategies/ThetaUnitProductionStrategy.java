package Strategies.UnitProductionStrategies;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.CityImpl;
import hotciv.UnitImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by csdev on 10/11/17.
 */
public class ThetaUnitProductionStrategy implements UnitProductionStrategy {

    private ArrayList<Position> spawnArray = new ArrayList<>();

    public ThetaUnitProductionStrategy() {
        spawnArray.add(new Position(0,0));
        spawnArray.add(new Position(-1,0));
        spawnArray.add(new Position(-1,1));
        spawnArray.add(new Position(0,1));
        spawnArray.add(new Position(1,1));
        spawnArray.add(new Position(1,0));
        spawnArray.add(new Position(1,-1));
        spawnArray.add(new Position(0,-1));
        spawnArray.add(new Position(-1,-1));
    }
    @Override
    public void createUnit(CityImpl city, HashMap<Position, UnitImpl> unitMap, Game game) {
        int cost = 0;
        boolean cityProducingLegion = city.getProduction() == GameConstants.LEGION;
        boolean cityProducingArcher = city.getProduction() == GameConstants.ARCHER;
        boolean cityProducingSettler = city.getProduction() == GameConstants.SETTLER;
        boolean cityProducingGalley = city.getProduction() == GameConstants.GALLEY;
        if(cityProducingLegion) cost = 15;
        else if(cityProducingArcher) cost = 10;
        else if(cityProducingSettler || cityProducingGalley) cost = 30;
        boolean cityHasEnoughTreasury = city.getTreasury() >= cost;
        if(city.getProduction() != null){
            if(cityHasEnoughTreasury) {
                UnitImpl newUnit = new UnitImpl(positionForNewUnit(city.getPosition(), game, city.getProduction()), city.getProduction(), city.getOwner());
                city.addTreasury(-cost);
                unitMap.put(newUnit.getPosition(), newUnit);
            }
        }


    }

    public Position positionForNewUnit(Position p, Game game, String production){
        for (Position pos: spawnArray) {
            int column = p.getColumn()+pos.getColumn();
            int row = p.getRow()+pos.getRow();
            Position validPos = new Position(row,column);
            if(isValidPosition(game, validPos, production)) {
                return validPos;
            }
        }
        return null;
    }

    public boolean isValidPosition(Game game, Position validPos, String production) {
        boolean unitOnPosition = game.getUnitAt(validPos)!=null;
        if(unitOnPosition) return false;
        boolean isMountain = game.getTileAt(validPos).getTypeString()==GameConstants.MOUNTAINS;
        if(isMountain) return false;
        boolean producingSeaUnit = production.equals(GameConstants.GALLEY);
        boolean isOcean = game.getTileAt(validPos).getTypeString()==GameConstants.OCEANS;
        boolean isLand = game.getTileAt(validPos).getTypeString()!=GameConstants.OCEANS;
        if(!producingSeaUnit){
            if(isOcean) return false;
        } else if(producingSeaUnit) {
            if(isLand) return false;
        }

        return true;
    }
}
