package hotciv.standard;

import hotciv.framework.*;
import javafx.geometry.Pos;

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

  public CityImpl cityPosRed = new CityImpl(Player.RED, new Position(1,1));
  public CityImpl cityPosBlue = new CityImpl(Player.BLUE, new Position(10,1));
  public TileImpl Ocean = new TileImpl(new Position(1,2), "Ocean");
  public TileImpl Mountain = new TileImpl(new Position(1,3), "Mountain");
  public TileImpl Plain = new TileImpl(new Position(1,4), "Plain");
  public TileImpl Hills = new TileImpl(new Position(3,3 ), "Hills");
  public UnitImpl Archer = new UnitImpl(new Position(2,2), "Archer", Player.RED);
  public UnitImpl Legion = new UnitImpl(new Position(2,5), "Legion", Player.BLUE);
  HashMap<Position,UnitImpl> unitMap = new HashMap();
  HashMap<Position,TileImpl> tileMap = new HashMap();
  HashMap<Position,CityImpl> cityMap = new HashMap();

  public GameImpl(){
    tileMap.put(Ocean.position, Ocean);
    tileMap.put(Mountain.position, Mountain);
    tileMap.put(Hills.position, Hills);
    tileMap.put(Plain.position, Plain);
    unitMap.put(Archer.position,Archer);
    unitMap.put(Legion.position, Legion);
    cityMap.put(cityPosRed.position, cityPosRed);
    cityMap.put(cityPosBlue.position, cityPosBlue);

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
      if(tileMap.get(to).equals(Ocean) || tileMap.get(to).equals(Mountain)){
        return false;
      }
    } catch (NullPointerException e) {}

    try {
      if (cityMap.get(to).getOwner() != getPlayerInTurn()) {
        cityMap.get(to).player = getPlayerInTurn();
      }
    }catch (NullPointerException e){

    }
    UnitImpl unit = unitMap.get(from);
    unitMap.remove(from, unit);
    unit.position = to;
    unitMap.put(unit.position, unit);

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
