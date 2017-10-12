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
    private int attackingStrength;


    public UnitImpl(Position p, String t, Player pl) {
        this.position = p;
        this.type = t;
        this.player = pl;
        initialStrength();
        if (type == GameConstants.ARCHER || type == GameConstants.LEGION || type == GameConstants.SETTLER) {
            this.moves = 1;
        } else if (type == GameConstants.GALLEY) {
            this.moves = 2;
        }
        fortified = false;
    }

    private void initialStrength() {
       if(type == GameConstants.ARCHER) {
           defensiveStrength = 3;
           attackingStrength = 2;
       }
        else if(type ==GameConstants.LEGION) {
            defensiveStrength = 2;
            attackingStrength = 4;
        }
        else{
            defensiveStrength = 3;
            attackingStrength = 0;
        }
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
        if (fortified) {
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
        return attackingStrength;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDefensiveStrength(int amount) {
        defensiveStrength += amount;
    }

    public void setMoves(int amount) {
        moves = amount;
    }

    public void setFortified(boolean fortified) {
        this.fortified = fortified;
    }

    public boolean isFortified() {
        return fortified;
    }

    public void resetMoves() {
        if(type == GameConstants.GALLEY) moves = 2;
        else moves = 1;
    }
}
