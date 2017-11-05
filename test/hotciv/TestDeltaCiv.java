package hotciv;

import AbstractFactory.DeltaCivFactory;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 9/18/17.
 */
public class TestDeltaCiv {

    private Game deltaGame;

    @Before
    public void setUp(){
        deltaGame = new GameImpl(new DeltaCivFactory());
    }

    @Test
    public void shouldBuildDeltaWorld() {
        assertThat(deltaGame.getCityAt(new Position(8, 12)).getOwner(), is(Player.RED));
        assertThat(deltaGame.getCityAt(new Position(4, 5)).getOwner(), is(Player.BLUE));

        assertThat(deltaGame.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(deltaGame.getTileAt(new Position(3, 4)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(deltaGame.getTileAt(new Position(1, 11)).getTypeString(), is(GameConstants.FOREST));
        assertThat(deltaGame.getTileAt(new Position(8, 9)).getTypeString(), is(GameConstants.HILLS));
    }
}
