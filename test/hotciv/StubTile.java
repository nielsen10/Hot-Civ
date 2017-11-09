package hotciv;

import hotciv.framework.Tile;

/**
 * Created by csdev on 10/2/17.
 */
class StubTile implements Tile {
    private String type;
    public StubTile(String type, int r, int c) { this.type = type; }
    public String getTypeString() { return type; }
}
