package hotciv;

import AbstractFactory.ZetaCivFactory;
import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 10/2/17.
 */
public class TestZetaCiv {

    private Game zetaGame;
    private City zetaRCity;
    private City zetaBCity;

    @Before
    public void setUp(){
        zetaGame = (GameImpl) new GameImpl(new ZetaCivFactory());
        zetaRCity = zetaGame.getCityAt(new Position(1, 1));
        zetaBCity = zetaGame.getCityAt(new Position(4, 1));
    }


    @Test
    public void shouldBeBlueWinnerAfterTakingRedCity(){
        zetaGame.endOfTurn();
        assertThat(zetaRCity.getOwner(), is(Player.RED));
        zetaGame.moveUnit(new Position(3, 2), new Position(2, 1));
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(2, 1), new Position(1, 1));
        assertThat(zetaRCity.getOwner(), is(Player.BLUE));
        assertThat(zetaGame.getWinner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeBlueWinnerAfter20RoundAnd3BattleWins(){
        for(int i = 0; i < 40; i ++){
            zetaGame.endOfTurn();
        }
        assertThat(zetaGame.getWinner(), nullValue());

        zetaGame.changeProductionInCityAt(new Position(1,1), GameConstants.SETTLER);
        zetaGame.endOfTurn();
        assertThat(zetaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(zetaGame.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        zetaGame.moveUnit(new Position(3,2), new Position(4,3)); //kill settler
        assertThat(zetaGame.getWinner(), nullValue());
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        assertThat(zetaGame.getUnitAt(new Position(4, 3)).getTypeString(), is(GameConstants.LEGION));
        zetaGame.moveUnit(new Position(4,3), new Position( 3,2));
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(3,2), new Position( 2,1));
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(2,1), new Position( 2,0)); //kill archer
        assertThat(zetaGame.getWinner(), nullValue());
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(1,1), new Position(2,1)); //move newly produced settler out of city
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(2,0), new Position(2,1)); //kill settler
        assertThat(zetaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(zetaGame.getWinner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeNoWinnerAfter3BattleWinsBefore20Turns(){
        assertThat(zetaGame.getWinner(), nullValue());

        zetaGame.changeProductionInCityAt(new Position(1,1), GameConstants.SETTLER);
        zetaGame.endOfTurn();
        assertThat(zetaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(zetaGame.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        zetaGame.moveUnit(new Position(3,2), new Position(4,3)); //kill settler
        assertThat(zetaGame.getWinner(), nullValue());
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        assertThat(zetaGame.getUnitAt(new Position(4, 3)).getTypeString(), is(GameConstants.LEGION));
        zetaGame.moveUnit(new Position(4,3), new Position( 3,2));
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(3,2), new Position( 2,1));
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(2,1), new Position( 2,0)); //kill archer
        assertThat(zetaGame.getWinner(), nullValue());
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(1,1), new Position(2,1)); //move newly produced settler out of city
        zetaGame.endOfTurn();
        zetaGame.moveUnit(new Position(2,0), new Position(2,1)); //kill settler
        assertThat(zetaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.LEGION));

        for(int i = 0; i < 40; i ++){
            zetaGame.endOfTurn();
        }

        assertThat(zetaGame.getWinner(), nullValue());
    }
}
