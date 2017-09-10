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

  public CityImpl cityRed = new CityImpl(Player.RED, new Position(1,1));
  public CityImpl cityBlue = new CityImpl(Player.BLUE, new Position(10,1));
  public TileImpl ocean = new TileImpl(new Position(1,2), "ocean");
  public TileImpl mountain = new TileImpl(new Position(1,3), "mountain");
  public TileImpl plain = new TileImpl(new Position(1,4), "plain");
  public TileImpl hills = new TileImpl(new Position(3,3 ), "hills");
  public UnitImpl archer = new UnitImpl(new Position(2,2), "archer", Player.RED);
  public UnitImpl legion = new UnitImpl(new Position(2,5), "legion", Player.BLUE);
  HashMap<Position,UnitImpl> unitMap = new HashMap();
  HashMap<Position,TileImpl> tileMap = new HashMap();
  HashMap<Position,CityImpl> cityMap = new HashMap();

  public GameImpl(){
    tileMap.put(ocean.getPosition(), ocean);
    tileMap.put(mountain.getPosition(), mountain);
    tileMap.put(hills.getPosition(), hills);
    tileMap.put(plain.getPosition(), plain);
    unitMap.put(archer.getPosition(), archer);
    unitMap.put(legion.getPosition(), legion);
    cityMap.put(cityRed.getPosition(), cityRed);
    cityMap.put(cityBlue.getPosition(), cityBlue);

  }

  private int playerturn = 1;
  private int year = 2000;
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
    if(year >= 2010){
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
      year ++;
      playerturn = 1;
    }
    else{
      playerturn ++;
    }

  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}


}
