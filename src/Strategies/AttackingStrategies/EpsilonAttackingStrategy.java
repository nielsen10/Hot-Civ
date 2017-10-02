package Strategies.AttackingStrategies;

import hotciv.framework.*;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by csdev on 10/2/17.
 */


public class EpsilonAttackingStrategy implements AttackingStrategy {

    private int initialDefensiveStrength;
    private int initialAttackingStrength;


    public static int roll() {
        return (int)(6.0 * Math.random()) + 1;
    }
    @Override
    public boolean attack(Game game, Position from, Position to) {
        initialDefensiveStrength = game.getUnitAt(to).getDefensiveStrength();
        initialAttackingStrength = game.getUnitAt(from).getAttackingStrength();
        if(getTotalStrength(game, from, initialAttackingStrength) < getTotalStrength(game, to, initialDefensiveStrength)) {
            return false;
        }
        return true;
    }

    public static int getTotalStrength(Game game, Position to, int initialStrength) {
        Unit unit = game.getUnitAt(to);
        int totalDefensiveStrength = 0;
        totalDefensiveStrength += initialStrength;
        totalDefensiveStrength += getFriendlySupport(game,to,unit.getOwner());
        totalDefensiveStrength *= getTerrainFactor(game,to);
        int dice = roll();
        totalDefensiveStrength *= dice;
        return totalDefensiveStrength;
    }

    public static Iterable<Position> get8Neighborhood(Position center) {
        final Iterator<Position> iterator = get8NeighborhoodIterator(center);
        Iterable<Position> i = new Iterable<Position>() {
            @Override
            public Iterator<Position> iterator() {
                return iterator;
            }
        };
        return i;
    }

    public static Iterator<Position> get8NeighborhoodIterator(Position center) {
        ArrayList<Position> list = new ArrayList<Position>();
        int row = center.getRow(); int col = center.getColumn();
        int r,c;
        for (int dr = -1; dr <= +1; dr++) {
            for (int dc = -1; dc <= +1; dc++) {
                r = row+dr; c = col+dc;
                // avoid positions outside the world
                if ( r >= 0 && r < GameConstants.WORLDSIZE &&
                        c >= 0 && c < GameConstants.WORLDSIZE ) {
                    // avoid center position
                    if ( r != row || c != col ){
                        list.add( new Position(r,c));
                    }
                }
            }
        }
        return list.iterator();
    }

    public static int getTerrainFactor(Game game, Position position) {
        // cities overrule underlying terrain
        if ( game.getCityAt(position) != null ) { return 3; }
        Tile t = game.getTileAt(position);
        if ( t.getTypeString() == GameConstants.FOREST ||
                t.getTypeString() == GameConstants.HILLS ) {
            return 2;
        }
        return 1;
    }

    public static int getFriendlySupport(Game game, Position position, Player player) {
        Iterator<Position> neighborhood = get8NeighborhoodIterator(position);
        Position p;
        int support = 0;
        while ( neighborhood.hasNext() ) {
            p = neighborhood.next();
            if ( game.getUnitAt(p) != null &&
                    game.getUnitAt(p).getOwner() == player ) {
                support++;
            }
        }
        return support;
    }


}
