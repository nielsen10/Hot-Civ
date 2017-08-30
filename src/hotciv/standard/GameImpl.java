package hotciv.standard;

import hotciv.framework.*;
import javafx.geometry.Pos;

import java.util.ArrayList;

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
  public TileImpl ocean = new TileImpl(new Position(1,2), "Ocean");
  public TileImpl Mountain = new TileImpl(new Position(1,3), "Mountain");
  ArrayList<TileImpl> myList = new ArrayList<TileImpl>();






  private int playerturn = 1;
  private int year = 2000;
  public Tile getTileAt( Position p ) {
    return ocean;
  }
  public Unit getUnitAt( Position p ) { return null; }
  public City getCityAt( Position p ) {
    if(playerturn == 1){
    return cityPosRed;
    }else{
    return cityPosBlue;
    }
  }

  public Player getPlayerInTurn() {

    if(playerturn == 1) {
      return Player.RED;
    }
    else {
      return Player.BLUE;
    }
  }

  public Player getWinner() { return null; }
  public int getAge() { return year; }
  public boolean moveUnit( Position from, Position to ) {
    return false;
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
