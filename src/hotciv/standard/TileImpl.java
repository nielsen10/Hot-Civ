package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by csdev on 8/29/17.
 */
public class TileImpl implements Tile {


    public Position position;
    public String type;

    public TileImpl(Position p, String t) {
        this.position = p;
        this.type = t;

    }


    @Override
    public String getTypeString() {
        return this.type;
    }
    public Position getTilePos(){
        return this.position;
    }
}
