package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

/**
 * Created by csdev on 9/1/17.
 */
public class UnitImpl implements Unit {



    private Position position;
    private String type;
    private Player player;
    private int defensiveStrenght;
    private int moves;

    public UnitImpl(Position p, String t, Player pl, int moves){
        this.position = p;
        this.type = t;
        this.player = pl;
        defensiveStrenght = 2;
        this.moves = moves;
    }



    @Override
    public String getTypeString() {
        return this.type;
    }

    @Override
    public Player getOwner() {
        return this.player;
    }

    @Override
    public int getMoveCount() {
        return moves;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrenght;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public int setDefensiveStrength(int amount){
        defensiveStrenght += amount;
        return defensiveStrenght;
    }
    public int setMoves(){
        return 1;
    }
}
