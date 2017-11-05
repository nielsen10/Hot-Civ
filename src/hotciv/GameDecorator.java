package hotciv;

import hotciv.framework.*;


/**
 * Created by csdev on 10/27/17.
 */
public class GameDecorator implements Game {

    private Game game;

    public GameDecorator(Game game) {
        this.game = game;
    }


    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println(getPlayerInTurn() + " moves " + getUnitAt(from).getTypeString() + " from " + from.toString() + " to " + to.toString());
        return game.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println(getPlayerInTurn() + " ends turn");
        game.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        System.out.println(getPlayerInTurn() + " changes workforce focus in city at " + p.toString() + " to " + balance);
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println(getPlayerInTurn() + " changes production in city at " + p.toString() + " to " + unitType);
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println(getPlayerInTurn() + " performs unit action on " + getUnitAt(p).getTypeString() + " at " + p.toString());
        game.performUnitActionAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
