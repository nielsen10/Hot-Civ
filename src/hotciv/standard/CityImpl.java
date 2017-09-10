package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;
import javafx.geometry.Pos;

/**
 * Created by csdev on 8/28/17.
 */
public class CityImpl implements City {

    private Player player;
    private Position position;

    public CityImpl(Player color, Position p) {

        player = color;
        position = p;

    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public Position getPosition(){
        return position;
    }

    public void setPlayer(Player p){
        player = p;
    }


}
