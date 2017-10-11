package hotciv.standard;

import AbstractFactory.SemiCivFactory;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 10/11/17.
 */
public class TestSemiCiv {

    private Game semiGame;

    @Before
    public void setUp(){
        semiGame = new GameImpl(new SemiCivFactory());
    }

    @Test
    public void shouldShowSemiCivStructure(){
        assertThat(semiGame.getAge(), is(-4000));
        assertThat(semiGame.getTileAt(new Position(0,5)).getTypeString(), is(GameConstants.MOUNTAINS));
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        assertThat(semiGame.getAge(), is(-3900));
        assertThat(semiGame.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
        semiGame.changeProductionInCityAt(new Position(8,12),GameConstants.SETTLER); //change production in red city
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        assertThat(semiGame.getAge(), is(-3800));
        semiGame.changeProductionInCityAt(new Position(4,5), GameConstants.ARCHER); //change production in blue city
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        assertThat(semiGame.getUnitAt(new Position(8,12)).getTypeString(), is(GameConstants.SETTLER)); //red settler
        semiGame.moveUnit(new Position(8,12), new Position(7,11));
        semiGame.performUnitActionAt(new Position(7,11));
        assertThat(semiGame.getCityAt(new Position(7,11)).getOwner(), is(Player.RED)); //new red city
        semiGame.changeProductionInCityAt(new Position(7,11),GameConstants.LEGION); //change production in red city
        assertThat(semiGame.getWinner(), nullValue());
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(4,5), new Position(5,5)); //move blue unit
        assertThat(semiGame.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.ARCHER));
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position( 5,5), new Position( 6,5)); //move blue unit
        semiGame.endOfTurn();
        assertThat(semiGame.getUnitAt(new Position(7,11)).getTypeString(), is(GameConstants.LEGION)); //red unit
        semiGame.moveUnit(new Position(7,11), new Position(7,10)); //move red unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(6,5), new Position(7,5)); //move blue unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(7,10), new Position(7,9)); //move red unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(7,5), new Position(8,5)); //move blue unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(7,9), new Position(7,8)); //move red unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(8,5), new Position(9,6)); //move blue unit
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(7,8), new Position(8,7)); //move red unit
        assertThat(semiGame.getUnitAt(new Position(9,6)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(semiGame.getUnitAt(new Position(8,7)).getTypeString(), is(GameConstants.LEGION));
        semiGame.endOfTurn();
        semiGame.endOfTurn();
        semiGame.moveUnit(new Position(8,7), new Position(9,6)); //move red unit
        assertThat(semiGame.getUnitAt(new Position(9,6)).getTypeString(), is(GameConstants.LEGION));
    }
}
