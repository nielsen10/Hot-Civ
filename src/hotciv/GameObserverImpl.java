package hotciv;

import AbstractFactory.CivFactory;
import hotciv.framework.*;

/**
 * Created by csdev on 11/9/17.
 */
public class GameObserverImpl extends GameImpl {

    private Game game;
    private GameObserver gameObserver;

    public GameObserverImpl(CivFactory factory, Game game) {
        super(factory);
        this.game = game;

    }
    @Override
    public boolean moveUnit(Position from, Position to) {
        if(!super.moveUnit(from,to)) return false;
        gameObserver.worldChangedAt(from);
        gameObserver.worldChangedAt(to);

        return true;
    }

    @Override
    public void endOfTurn() {
        super.endOfTurn();
        gameObserver.turnEnds(getPlayerInTurn(), game.getAge());
    }

    @Override
    public void changeProductionInCityAt( Position p, String unitType ) {
        super.changeProductionInCityAt(p,unitType);
        gameObserver.tileFocusChangedAt(p);
    }

    @Override
    public void performUnitActionAt( Position p ) {
        super.performUnitActionAt(p);
        gameObserver.worldChangedAt(p);
    }

    @Override
    public void setTileFocus(Position position) {
        super.setTileFocus(position);
        gameObserver.tileFocusChangedAt(position);
    }

    @Override
    public void addObserver(GameObserver observer) {
        super.addObserver(observer);
        this.gameObserver = observer;
    }
}
