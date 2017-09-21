package hotciv.standard;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.WinningStrategies.WinningStrategy;
import Strategies.WorldStrategy.WorldStrategy;
import Strategies.UnitActionStrategies.UnitActionStrategy;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.HashMap;

/** Skeleton implementation of HotCiv.

 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

public class GameImpl implements Game {

    private WorldStrategy worldStrategy;
    private UnitActionStrategy unitActionStrategy;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private HashMap<Position,UnitImpl> unitMap = new HashMap();
    private HashMap<Position,TileImpl> tileMap = new HashMap();
    private HashMap<Position,CityImpl> cityMap = new HashMap();
    private ArrayList<Position> spawnArray = new ArrayList<>();
    private int playerturn = 1;
    private int year = -4000;

    public GameImpl(AgingStrategy agingStrategy, WinningStrategy winningStrategy, UnitActionStrategy unitActionStrategy, WorldStrategy worldStrategy){
        this.winningStrategy = winningStrategy;
        this.agingStrategy = agingStrategy;
        this.unitActionStrategy = unitActionStrategy;
        this.worldStrategy = worldStrategy;

        for(int i=0; i<=15; i++) {
            for(int j=0; j<=15; j++) {
                Position pos = new Position(i,j);
                tileMap.put(pos, new TileImpl(pos, GameConstants.PLAINS));
            }
        }
        worldStrategy.buildWorld(this, unitMap, tileMap, cityMap);

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

    public Tile getTileAt( Position p ) { return tileMap.get(p); }

    public Unit getUnitAt( Position p ) {
        return unitMap.get(p);
    }

    public City getCityAt( Position p ) {
        return cityMap.get(p);
    }

    public Player getPlayerInTurn() {
        if(playerturn == 1) {
            return Player.RED;
        }
        else {
            return Player.BLUE;
        }
    }

    public Player getWinner() {
        return winningStrategy.calculateWinner(this);
    }

    public int getAge() { return year; }

    public boolean moveUnit(Position from, Position to) {
        if (!isMovePossible(from, to)) { return false; }
        isMoveOnCity(to);
        updateUnitPosition(from, to);
        return true;
    }

    private void updateUnitPosition(Position from, Position to) {
        UnitImpl movingUnit = (UnitImpl) getUnitAt(from);
        movingUnit.setMoves(movingUnit.getMoveCount() - 1);
        unitMap.remove(from, movingUnit);
        movingUnit.setPosition(to);
        unitMap.put(movingUnit.getPosition(), movingUnit);
    }

    private void isMoveOnCity(Position to) {
        CityImpl cityUnderAttack = (CityImpl) getCityAt(to);
        if (cityUnderAttack != null) {
            if (cityUnderAttack.getOwner() != getPlayerInTurn()) {
                cityUnderAttack.setPlayer(getPlayerInTurn());
            }
        }
    }

    private boolean isMovePossible(Position from, Position to) {
        int verticalDistanceOnMove = from.getColumn() - to.getColumn();
        int horizontalDistanceOnMove = from.getRow() - to.getRow();
        if (verticalDistanceOnMove >= 2 && verticalDistanceOnMove <= -2 && horizontalDistanceOnMove >= 2 && horizontalDistanceOnMove <= -2) { return false; }

        if (getUnitAt(from) == null) {
            return false;
        }
        if (getUnitAt(from).getMoveCount() == 0) {
            return false;
        }
        if (getUnitAt(from).getOwner() != getPlayerInTurn()) {
            return false;
        }
        if (getTileAt(to).equals(GameConstants.OCEANS)) {
            return false;
        }
        if (getTileAt(to).equals(GameConstants.MOUNTAINS)) {
            return false;
        }
        return true;
    }

    public void endOfTurn() {
        if(playerturn == 2){
            year = agingStrategy.calculateYear();
            playerturn = 1;
            for (Position cityImpl : cityMap.keySet()) {
                int cost = 0;
                CityImpl city = cityMap.get(cityImpl);
                city.addTreasury(6);
                if(city.getProduction() == GameConstants.LEGION) { cost = 15; }
                else if(city.getProduction() == GameConstants.ARCHER) { cost = 10; }
                else if(city.getProduction() == GameConstants.SETTLER) { cost = 30;}
                if(city.getTreasury() >= cost) {
                    UnitImpl newRedUnit = new UnitImpl(positionForNewUnit(city.getPosition()),city.getProduction(), Player.RED);
                    city.addTreasury(-cost);
                    unitMap.put(newRedUnit.getPosition(), newRedUnit);
                }

            }

            for (Position unit : unitMap.keySet()) {
                unitMap.get(unit).setMoves(1);
            }
            getWinner();
        }
        else{
            playerturn ++;
        }
    }

    public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

    public void changeProductionInCityAt( Position p, String unitType ) {
        CityImpl city = (CityImpl) getCityAt(p);
        city.setProduction(unitType);
    }

    public void performUnitActionAt( Position p ) {
        unitActionStrategy.deployUnitAction(p, this, unitMap, cityMap);
    }

    public Position positionForNewUnit (Position p){
        for (Position pos: spawnArray) {
            int column = p.getColumn()+pos.getColumn();
            int row = p.getRow()+pos.getRow();
            Position validPos = new Position(row,column);
            if(getUnitAt(validPos)==null) {
                return validPos;
            }
        }
        return null;
    }
}
