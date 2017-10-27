package Strategies.UnitProductionStrategies;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by csdev on 10/11/17.
 */
public class AlphaUnitProductionStrategy implements UnitProductionStrategy {

    private ArrayList<Position> spawnArray = new ArrayList<>();

    public AlphaUnitProductionStrategy() {
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
        if(cityProducingLegion) cost = 15;
        else if(cityProducingArcher) cost = 10;
        else if(cityProducingSettler) cost = 30;
        boolean cityHasEnoughTreasury = city.getTreasury() >= cost;
        if(city.getProduction() != null){
            if(cityHasEnoughTreasury) {
                UnitImpl newUnit = new UnitImpl(positionForNewUnit(city.getPosition(), game), city.getProduction(), city.getOwner());
                city.addTreasury(-cost);
                unitMap.put(newUnit.getPosition(), newUnit);
            }
        }


    }

    public Position positionForNewUnit(Position p, Game game){
        for (Position pos: spawnArray) {
            int column = p.getColumn()+pos.getColumn();
            int row = p.getRow()+pos.getRow();
            Position validPos = new Position(row,column);
            if(game.getUnitAt(validPos)==null) {
                return validPos;
            }
        }
        return null;
    }
}
