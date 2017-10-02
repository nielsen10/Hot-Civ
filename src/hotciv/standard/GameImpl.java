package hotciv.standard;

import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.DiceStrategies.DiceStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
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

    private DiceStrategy diceStrategy;
    private AttackingStrategy attackingStrategy;
    private UnitActionStrategy unitActionStrategy;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private HashMap<Position,UnitImpl> unitMap = new HashMap();
    private HashMap<Position,TileImpl> tileMap = new HashMap();
    private HashMap<Position,CityImpl> cityMap = new HashMap();
    private ArrayList<Position> spawnArray = new ArrayList<>();
    private int playerTurn = 1;
    private int year = -4000;
    private int redBattlesWon;
    private int blueBattlesWon;

    public GameImpl(AgingStrategy agingStrategy, WinningStrategy winningStrategy, UnitActionStrategy unitActionStrategy, WorldStrategy worldStrategy, AttackingStrategy attackingStrategy){
        this.winningStrategy = winningStrategy;
        this.agingStrategy = agingStrategy;
        this.unitActionStrategy = unitActionStrategy;
        this.attackingStrategy = attackingStrategy;

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

    public Unit getUnitAt( Position p ) { return unitMap.get(p); }

    public City getCityAt( Position p ) { return cityMap.get(p); }

    public Player getPlayerInTurn() {
        if(playerTurn == 1) {
            return Player.RED;
        }
        else {
            return Player.BLUE;
        }
    }

    public Player getWinner() { return winningStrategy.calculateWinner(this); }

    public int getAge() { return year; }

    public boolean moveUnit(Position from, Position to) {
        if (! isMovePossible(from, to)) return false;
        isMoveOnCity(to);
        if(!succesfulAttack(from, to)) {
            unitMap.remove(from, getUnitAt(from));
            return false;
        }
        if(getPlayerInTurn() == Player.RED) redBattlesWon ++;
        if(getPlayerInTurn() == Player.BLUE) blueBattlesWon ++;
        updateUnitPosition(from, to);
        return true;
    }

    private boolean succesfulAttack(Position from, Position to) {
        return attackingStrategy.attack(this,from, to, diceStrategy);
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
        boolean isMoveTooLong = verticalDistanceOnMove > 1 || verticalDistanceOnMove < -1 || horizontalDistanceOnMove > 1 || horizontalDistanceOnMove < -1;
        if (isMoveTooLong) return false;

        boolean tryingToMoveNothing = getUnitAt(from) == null;
        if (tryingToMoveNothing) return false;
        boolean hasNoMovesLeft = getUnitAt(from).getMoveCount() == 0;
        if (hasNoMovesLeft) return false;
        boolean isNotOwnUnit = getUnitAt(from).getOwner() != getPlayerInTurn();
        if (isNotOwnUnit) return false;
        boolean isOcean = getTileAt(to).getTypeString() == GameConstants.OCEANS;
        if (isOcean) return false;
        boolean isMountain = getTileAt(to).getTypeString() == GameConstants.MOUNTAINS;
        if (isMountain) return false;
        boolean movingOnOwnUnit = getUnitAt(to) != null && getUnitAt(to).getOwner() == getPlayerInTurn();
        if (movingOnOwnUnit) return false;
        return true;
    }

    public void endOfTurn() {
        boolean isEndOFTurn = playerTurn == 2;

        if(isEndOFTurn){
            yearUpdate();
            handleAllCities();
            resetMovesForUnits();
            getWinner();
        }
        nextPlayerInTurn();
    }

    private void resetMovesForUnits() {
        for (Position unit : unitMap.keySet()) {
            unitMap.get(unit).setMoves(1);
        }
    }

    private void handleAllCities() {
        for (Position cityImpl : cityMap.keySet()) {
            CityImpl city = cityMap.get(cityImpl);
            addTreasuryToCity(city);
            createUnitsFromCity(city);
        }
    }

    private void createUnitsFromCity(CityImpl city) {
        int cost = 0;
        boolean cityProducingLegion = city.getProduction() == GameConstants.LEGION;
        boolean cityProducingArcher = city.getProduction() == GameConstants.ARCHER;
        boolean cityProducingSettler = city.getProduction() == GameConstants.SETTLER;
        if(cityProducingLegion) cost = 15;
        else if(cityProducingArcher) cost = 10;
        else if(cityProducingSettler) cost = 30;
        boolean cityHasEnoughTreasury = city.getTreasury() >= cost;
        if(cityHasEnoughTreasury) {
            UnitImpl newUnit = new UnitImpl(positionForNewUnit(city.getPosition()), city.getProduction(), city.getOwner());
            city.addTreasury(-cost);
            unitMap.put(newUnit.getPosition(), newUnit);
        }
    }

    private void addTreasuryToCity(CityImpl city) { city.addTreasury(6); }

    private void nextPlayerInTurn() {
        boolean isEndOFTurn = playerTurn == 2;

        if(isEndOFTurn){
            playerTurn=1;
        } else {
            playerTurn++;
        }
    }

    private void yearUpdate() { year = agingStrategy.calculateYear(); }

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
    public int getRedBattlesWon() {
        return redBattlesWon;
    }

    public int getBlueBattlesWon() {
        return blueBattlesWon;
    }
}
