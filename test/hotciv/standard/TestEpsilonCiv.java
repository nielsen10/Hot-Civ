package hotciv.standard;

import AbstractFactory.EpsilonCivFactory;
import Strategies.AttackingStrategies.EpsilonAttackingStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 9/25/17.
 */
public class TestEpsilonCiv {

    private Game stubGame;
    private City rCity;
    private City bCity;
    private Iterator<Position> iter;
    private List<Position> neighborhood;
    private Position center;
    private EpsilonAttackingStrategy epsilonAttackingStrategy;
    private Game epsilonGame;

    private List<Position> convertIteration2List(Iterator<Position> iter) {
        List<Position> list = new ArrayList<>();
        iter.forEachRemaining(list::add); // Doing it the Java8 way
        return list;
    }

    @Before
    public void setUp(){
        epsilonGame = new GameImpl(new EpsilonCivFactory());
        stubGame = new GameStubForBattleTesting();
        this.epsilonAttackingStrategy = new EpsilonAttackingStrategy(new FixedDiceStrategy());
        rCity = stubGame.getCityAt(new Position(1, 1));
        bCity = stubGame.getCityAt(new Position(4, 1));
    }

    @Test
    public void shouldGive8PositionsForCenterAt8_8() {
        // Simple learning test, showing typical use of
        // for loop
        List<Position> list = new ArrayList<>();
        for (Position p : epsilonAttackingStrategy.get8Neighborhood(new Position(8,8))) {
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
        iter = epsilonAttackingStrategy.get8NeighborhoodIterator(center);
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
        assertThat(epsilonAttackingStrategy.getTerrainFactor(stubGame, new Position(0,1)), is(1));
        // hills have multiplier 2
        assertThat(epsilonAttackingStrategy.getTerrainFactor(stubGame, new Position(1,0)), is(2));
        // forest have multiplier 2
        assertThat(epsilonAttackingStrategy.getTerrainFactor(stubGame, new Position(0,0)), is(2));
        // cities have multiplier 3
        assertThat(epsilonAttackingStrategy.getTerrainFactor(stubGame, new Position(1,1)), is(3));
    }

    @Test public void shouldGiveSum1ForBlueAtP5_5() {
        assertThat("Blue imaginary unit at (5,5) should get +1 support",
                epsilonAttackingStrategy.getFriendlySupport(stubGame, new Position(5,5), Player.BLUE), is(+1));
    }

    @Test public void shouldGiveSum0ForBlueAtP2_4() {
        assertThat("Blue unit at (2,4) should get +0 support",
                epsilonAttackingStrategy.getFriendlySupport(stubGame, new Position(2,4), Player.BLUE), is(+0));
    }

    @Test public void shouldGiveSum2ForRedAtP2_4() {
        stubGame.moveUnit(new Position(2,0 ), new Position(3,1));
        assertThat("Red unit at (3,2) should get +2 support",
                epsilonAttackingStrategy.getFriendlySupport(stubGame, new Position(3,2), Player.RED), is(+2));
    }


    @Test
    public void shouldBe4StrengthOnRedUnitAt3_3() { //2 attacking strength + 2 support * 0 for beeing on plain * 3 for fixed dice
        assertThat(epsilonAttackingStrategy.getTotalStrength(stubGame, new Position(3,3), 2), is(12));
    }

    @Test
    public void shouldBe5DefensiveStrenghtAt3_2(){ // 3 archer defensive strength + 2 support * 0 for beeing on plain * 3 for fixed dice
        assertThat(epsilonAttackingStrategy.getTotalStrength(stubGame, new Position(3,2), 3), is(15));
    }

    @Test
    public void shouldBe9DefensiveStrenghtAt1_1(){ // 3 archer defensive strength + 0 support * 3 for beeing on plain * 3 for fixed dice
        assertThat(epsilonAttackingStrategy.getTotalStrength(stubGame, new Position(1,1), 3), is(27));
    }

    @Test
    public void shouldBeRedWhoWins(){
        assertThat(epsilonAttackingStrategy.attack(stubGame, new Position(3,2), new Position(4,4)), is(true));
    }

    //integration testing

    @Test
    public void shouldRemoveUnitIfBattleIsLost(){
        assertThat(epsilonGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(epsilonGame.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));
        epsilonGame.moveUnit(new Position(2,0), new Position(3,1));
        epsilonGame.endOfTurn();
        epsilonGame.changeProductionInCityAt(new Position(4,1), GameConstants.ARCHER);
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        assertThat(epsilonGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        epsilonGame.moveUnit(new Position(3,1), new Position( 3,2));
        assertThat(epsilonGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(epsilonGame.getUnitAt(new Position(3, 1)), nullValue());
    }


    @Test
    public void shouldBeBlueWinnerAfter3SuccessfulAttacks(){
        epsilonGame.changeProductionInCityAt(new Position(1,1), GameConstants.SETTLER);
        epsilonGame.endOfTurn();
        assertThat(epsilonGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(epsilonGame.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
        epsilonGame.moveUnit(new Position(3,2), new Position(4,3)); //kill settler
        assertThat(epsilonGame.getWinner(), nullValue());
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        assertThat(epsilonGame.getUnitAt(new Position(4, 3)).getTypeString(), is(GameConstants.LEGION));
        epsilonGame.moveUnit(new Position(4,3), new Position( 3,2));
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.moveUnit(new Position(3,2), new Position( 2,1));
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.moveUnit(new Position(2,1), new Position( 2,0)); //kill archer
        assertThat(epsilonGame.getWinner(), nullValue());
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.endOfTurn();
        epsilonGame.moveUnit(new Position(1,1), new Position(2,1)); //move newly produced settler out of city
        epsilonGame.endOfTurn();
        epsilonGame.moveUnit(new Position(2,0), new Position(2,1)); //kill settler
        assertThat(epsilonGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(epsilonGame.getWinner(), is(Player.BLUE));
    }



}
