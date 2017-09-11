package hotciv.standard;

import hotciv.framework.*;

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

    private CityImpl cityRed = new CityImpl(Player.RED, new Position(1,1));
    private CityImpl cityBlue = new CityImpl(Player.BLUE, new Position(4,1));
    private TileImpl ocean = new TileImpl(new Position(1,0), "ocean");
    private TileImpl mountain = new TileImpl(new Position(2,2), "mountain");
    private TileImpl hills = new TileImpl(new Position(0,1 ), "hills");
    private UnitImpl archer = new UnitImpl(new Position(2,0), "archer", Player.RED);
    private UnitImpl legion = new UnitImpl(new Position(3,2), "legion", Player.BLUE);
    private UnitImpl settler = new UnitImpl(new Position(4,3), "settler", Player.RED);
    private HashMap<Position,UnitImpl> unitMap = new HashMap();
    private HashMap<Position,TileImpl> tileMap = new HashMap();
    private HashMap<Position,CityImpl> cityMap = new HashMap();

    public GameImpl(){
        for(int i=0; i<=15; i++) {
            for(int j=0; j<=15; j++) {
                Position pos = new Position(i,j);
                tileMap.put(pos, new TileImpl(pos, "plain"));
            }
        }
        tileMap.put(ocean.getPosition(), ocean);
        tileMap.put(mountain.getPosition(), mountain);
        tileMap.put(hills.getPosition(), hills);
        unitMap.put(archer.getPosition(), archer);
        unitMap.put(legion.getPosition(), legion);
        unitMap.put(settler.getPosition(), settler);
        cityMap.put(cityRed.getPosition(), cityRed);
        cityMap.put(cityBlue.getPosition(), cityBlue);


    }

    private int playerturn = 1;
    private int year = 4000;
    public Tile getTileAt( Position p ) {



        return tileMap.get(p);

    }

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
        if(year <= 3000){
            return Player.RED;
        }
        return null; }
    public int getAge() { return year; }
    public boolean moveUnit( Position from, Position to ) {
        try {
            if (getUnitAt(from).getOwner() != getPlayerInTurn()) {
                return false;
            }
        } catch (NullPointerException e){

        }
        try{
            if(tileMap.get(to).equals(ocean) || tileMap.get(to).equals(mountain)){
                return false;
            }
        } catch (NullPointerException e) {}

        try {
            if (cityMap.get(to).getOwner() != getPlayerInTurn()) {
                cityMap.get(to).setPlayer(getPlayerInTurn());
            }
        }catch (NullPointerException e){

        }
        UnitImpl unit = unitMap.get(from);
        unitMap.remove(from, unit);
        unit.setPosition(to);
        unitMap.put(unit.getPosition(), unit);

        return true;
    }
    public void endOfTurn() {
        if(playerturn == 2){
            year -= 100;
            playerturn = 1;
            cityRed.addTreasury(6);
            cityBlue.addTreasury(6);
            int cost = 0;
            if(cityRed.getProduction() == "legion") { cost = 15; }
            else if(cityRed.getProduction() == "archer") { cost = 10; }
            if(cityRed.getTreasury() >= cost) {
                UnitImpl newRedUnit = new UnitImpl(cityRed.getPosition(),cityRed.getProduction(), Player.RED);
                cityRed.addTreasury(-cost);
                unitMap.put(newRedUnit.getPosition(), newRedUnit);
            }
            cost = 0;
            if(cityBlue.getProduction() == "legion") { cost = 15; }
            else if(cityBlue.getProduction() == "archer") { cost = 10; }
            if (cityBlue.getTreasury() >= cost) {
                UnitImpl newBlueUnit = new UnitImpl(cityBlue.getPosition(),cityBlue.getProduction(), Player.BLUE);
                cityBlue.addTreasury(-cost);
                unitMap.put(newBlueUnit.getPosition(), newBlueUnit);
            }
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
    public void performUnitActionAt( Position p ) {}


}
