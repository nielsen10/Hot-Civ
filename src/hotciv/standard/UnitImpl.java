package hotciv.standard;

import hotciv.framework.GameConstants;
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
    private int defensiveStrength;
    private int moves;
    private boolean fortified;


    public UnitImpl(Position p, String t, Player pl){
        this.position = p;
        this.type = t;
        this.player = pl;
        defensiveStrength = 2;
        if(type == "archer" || type == GameConstants.LEGION || type == "settler" ) {
            this.moves = 1;
        }
        fortified = false;
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
        if(fortified){
            return 0;
        }
        return moves;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
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
        defensiveStrength += amount;
        return defensiveStrength;
    }
    public void setMoves(int amount){
        moves = amount;
    }
    public void setFortified(boolean fortified) {
        this.fortified = fortified;
    }

    public boolean isFortified() {
        return fortified;
    }

}
