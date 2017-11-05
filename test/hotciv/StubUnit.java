package hotciv;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * Created by csdev on 10/2/17.
 */
class StubUnit implements Unit {
    private String type; private Player owner;
    private int defensiveStrength;
    private int attackingStrength;

    public StubUnit(String type, Player owner) {
        this.type = type; this.owner = owner;
        initialStrength();
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
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 0; }
    public int getDefensiveStrength() { return defensiveStrength; }
    public int getAttackingStrength() { return attackingStrength; }
}