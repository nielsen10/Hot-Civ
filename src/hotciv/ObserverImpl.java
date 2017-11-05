package hotciv;

import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

/**
 * Created by csdev on 11/5/17.
 */
public class ObserverImpl implements GameObserver {


    @Override
    public void worldChangedAt(Position pos) {

    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {

    }

    @Override
    public void tileFocusChangedAt(Position position) {

    }
}
