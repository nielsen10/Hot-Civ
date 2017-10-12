package hotciv.standard;

import AbstractFactory.CivFactory;
import Strategies.AgingStrategies.AgingStrategy;
import Strategies.AttackingStrategies.AttackingStrategy;
import Strategies.DiceStrategies.DiceStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import Strategies.UnitProductionStrategies.UnitProductionStrategy;
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

    private UnitProductionStrategy unitProductionStrategy;
    private WorldStrategy worldStrategy;
    private AttackingStrategy attackingStrategy;
    private UnitActionStrategy unitActionStrategy;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private HashMap<Position,UnitImpl> unitMap = new HashMap();
    private HashMap<Position,TileImpl> tileMap = new HashMap();
    private HashMap<Position,CityImpl> cityMap = new HashMap();
    private int playerTurn = 1;
    private int year = -4000;

    private int redBattlesWon;
    private int blueBattlesWon;

    public GameImpl(CivFactory factory){
        this.winningStrategy = factory.createWinningStrategy();
        this.agingStrategy = factory.createAgingStrategy();
        this.unitActionStrategy = factory.createUnitActionStrategy();
        this.attackingStrategy = factory.createAttackingStrategy();
        this.worldStrategy = factory.createWorldStrategy();
        this.unitProductionStrategy = factory.createUnitProductionStrategy();

        for(int i=0; i<=15; i++) {
            for(int j=0; j<=15; j++) {
                Position pos = new Position(i,j);
                tileMap.put(pos, new TileImpl(pos, GameConstants.PLAINS));
            }
        }
        worldStrategy.buildWorld(this, unitMap, tileMap, cityMap);
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

        if(!attackOnEnemySucceeded(from, to)) return false;


        updateUnitPosition(from, to);
        return true;
    }

    private boolean attackOnEnemySucceeded(Position from, Position to) {
        if(getUnitAt(to) != null) {
            boolean successfulAttack = attackingStrategy.attack(this,from,to);
            if (!successfulAttack) {
                unitMap.remove(from, getUnitAt(from));
                return false;
            }
            if(getPlayerInTurn() == Player.RED) redBattlesWon ++;
            if(getPlayerInTurn() == Player.BLUE) blueBattlesWon ++;
        }
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
        boolean isMoveTooLong = verticalDistanceOnMove > 1 || verticalDistanceOnMove < -1 || horizontalDistanceOnMove > 1 || horizontalDistanceOnMove < -1;
        if (isMoveTooLong) return false;

        boolean tryingToMoveNothing = getUnitAt(from) == null;
        if (tryingToMoveNothing) return false;
        boolean hasNoMovesLeft = getUnitAt(from).getMoveCount() == 0;
        if (hasNoMovesLeft) return false;
        boolean isNotOwnUnit = getUnitAt(from).getOwner() != getPlayerInTurn();
        if (isNotOwnUnit) return false;
        boolean isOcean = getTileAt(to).getTypeString().equals(GameConstants.OCEANS) && getUnitAt(from).getTypeString() != GameConstants.GALLEY;
        if (isOcean) return false;
        boolean isLand = getTileAt(to).getTypeString()!= GameConstants.OCEANS && getUnitAt(from).getTypeString().equals(GameConstants.GALLEY);
        if(isLand) return false;
        boolean isMountain = getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        if (isMountain) return false;
        boolean movingOnOwnUnit = getUnitAt(to) != null && getUnitAt(to).getOwner() == getPlayerInTurn();
        if (movingOnOwnUnit) return false;
        return true;
    }

    public void endOfTurn() {
        boolean isEndOfRound = playerTurn == 2;

        if(isEndOfRound){
            yearUpdate();
            handleAllCities();
            resetMovesForUnits();
            getWinner();
        }
        nextPlayerInTurn();


    }

    private void resetMovesForUnits() {
        for (Position unit : unitMap.keySet()) {
            unitMap.get(unit).resetMoves();
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
        unitProductionStrategy.createUnit(city, unitMap, this);
    }

    private void addTreasuryToCity(CityImpl city) { city.addTreasury(6); }

    private void nextPlayerInTurn() {
        boolean isEndOfRound = playerTurn == 2;

        if(isEndOfRound){
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

    public int getRedBattlesWon() {
        return redBattlesWon;
    }

    public int getBlueBattlesWon() {
        return blueBattlesWon;
    }

    public void setRedBattlesWon(int redBattlesWon) {
        this.redBattlesWon = redBattlesWon;
    }

    public void setBlueBattlesWon(int blueBattlesWon) {
        this.blueBattlesWon = blueBattlesWon;
    }

    public void addCity(Position position, CityImpl city) {
        cityMap.put(position,city);
    }
}
