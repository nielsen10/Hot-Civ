package hotciv.standard;

import hotciv.framework.*;


/**
 * Created by csdev on 10/2/17.
 */
class GameStubForBattleTesting implements Game {
    public Tile getTileAt(Position p) {
        if (p.getRow() == 0 && p.getColumn() == 0) {
            return new StubTile(GameConstants.FOREST, 0, 0);
        }
        if (p.getRow() == 1 && p.getColumn() == 0) {
            return new StubTile(GameConstants.HILLS, 1, 0);
        }
        return new StubTile(GameConstants.PLAINS, 0, 1);
    }

    public Unit getUnitAt(Position p) {
        if (p.getRow() == 2 && p.getColumn() == 3 ||
                p.getRow() == 3 && p.getColumn() == 2 ||
                p.getRow() == 3 && p.getColumn() == 3 ||
                p.getRow() == 1 && p.getColumn() == 1) {
            return new StubUnit(GameConstants.ARCHER, Player.RED);
        }
        if (p.getRow() == 4 && p.getColumn() == 4) {
            return new StubUnit(GameConstants.ARCHER, Player.BLUE);
        }
        return null;
    }

    public City getCityAt(Position p) {
        if (p.getRow() == 1 && p.getColumn() == 1) {
            return new City() {
                public Player getOwner() {
                    return Player.RED;
                }

                public int getSize() {
                    return 1;
                }

                public String getProduction() {
                    return null;
                }

                public String getWorkforceFocus() {
                    return null;
                }

                @Override
                public Position getPosition() {
                    return null;
                }

                @Override
                public int getTreasury() {
                    return 0;
                }
            };
        }
        return null;
    }

    // the rest is unused test stub methods...
    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void endOfTurn() {
    }

    public Player getPlayerInTurn() {
        return null;
    }

    public Player getWinner() {
        return null;
    }

    public int getAge() {
        return 0;
    }

    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    public void performUnitActionAt(Position p) {
    }
}
