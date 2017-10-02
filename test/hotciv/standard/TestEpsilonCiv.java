package hotciv.standard;

import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.AttackingStrategies.EpsilonAttackingStrategy;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import com.sun.prism.es2.ES2Graphics;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by csdev on 9/25/17.
 */
public class TestEpsilonCiv {

    private Game EpsilonGame;
    private City rCity;
    private City bCity;
    private Iterator<Position> iter;
    private List<Position> neighborhood;
    private Position center;

    private List<Position> convertIteration2List(Iterator<Position> iter) {
        List<Position> list = new ArrayList<>();
        iter.forEachRemaining(list::add); // Doing it the Java8 way
        return list;
    }

    @Before
    public void setUp(){
        EpsilonGame = new GameStubForBattleTesting();
        rCity = EpsilonGame.getCityAt(new Position(1, 1));
        bCity = EpsilonGame.getCityAt(new Position(4, 1));

    }

    @Test
    public void shouldGive8PositionsForCenterAt8_8() {
        // Simple learning test, showing typical use of
        // for loop
        List<Position> list = new ArrayList<>();
        for (Position p : EpsilonAttackingStrategy.get8Neighborhood(new Position(8,8))) {
            list.add(p);
        }

        // Spot testing, not exhaustive
        assertThat(list.size(), is(8));
        assertThat(list, hasItems( new Position[] {
                new Position(7,7),
                new Position(7,8),
                new Position(9,9) }));
        assertThat(list, not(hasItem(new Position(8,8))));
        assertThat(list, not(hasItem(new Position(6,8))));
    }

    @Test public void shouldGive3PositionsForP0_0() {
        center = new Position(0,0);
        iter = EpsilonAttackingStrategy.get8NeighborhoodIterator(center);
        neighborhood = convertIteration2List( iter );

        assertThat(neighborhood.size(), is(3));
        assertThat(neighborhood, hasItems( new Position[] {
                new Position(0,1),
                new Position(1,1),
                new Position(0,1) }));
    }

    @Test public void shouldGive3PositionsForP15_15() {
        center = new Position(15,15);
        iter = EpsilonAttackingStrategy.get8NeighborhoodIterator(center);
        neighborhood = convertIteration2List( iter );

        assertThat(neighborhood.size(), is(3));
        assertThat(neighborhood, hasItems( new Position[] {
                new Position(14,15),
                new Position(14,14),
                new Position(15,14) }));
    }

    @Test public void shouldGiveCorrectTerrainFactors() {
        // plains have multiplier 1
        assertThat(EpsilonAttackingStrategy.getTerrainFactor(EpsilonGame, new Position(0,1)), is(1));
        // hills have multiplier 2
        assertThat(EpsilonAttackingStrategy.getTerrainFactor(EpsilonGame, new Position(1,0)), is(2));
        // forest have multiplier 2
        assertThat(EpsilonAttackingStrategy.getTerrainFactor(EpsilonGame, new Position(0,0)), is(2));
        // cities have multiplier 3
        assertThat(EpsilonAttackingStrategy.getTerrainFactor(EpsilonGame, new Position(1,1)), is(3));
    }

    @Test public void shouldGiveSum1ForBlueAtP5_5() {
        assertThat("Blue imaginary unit at (5,5) should get +1 support",
                EpsilonAttackingStrategy.getFriendlySupport( EpsilonGame, new Position(5,5), Player.BLUE), is(+1));
    }

    @Test public void shouldGiveSum0ForBlueAtP2_4() {
        assertThat("Blue unit at (2,4) should get +0 support",
                EpsilonAttackingStrategy.getFriendlySupport( EpsilonGame, new Position(2,4), Player.BLUE), is(+0));
    }

    @Test public void shouldGiveSum2ForRedAtP2_4() {
        EpsilonGame.moveUnit(new Position(2,0 ), new Position(3,1));
        assertThat("Red unit at (3,2) should get +2 support",
                EpsilonAttackingStrategy.getFriendlySupport( EpsilonGame, new Position(3,2), Player.RED), is(+2));
    }


    @Test
    public void shouldBe6StrengthOnRedUnitAt1_1() { //3 defensive streng + 2 support * 0 for beeing on plain
        assertThat(EpsilonAttackingStrategy.getTotalAttackingStrength(EpsilonGame, new Position(3,3)), is(5));
    }


}
