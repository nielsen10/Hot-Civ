package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * Created by csdev on 8/28/17.
 */
public class CityImpl implements City {

    public Player player;
    public Position position;

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


}
