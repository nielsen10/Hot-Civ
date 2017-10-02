package hotciv.standard;


import AbstractFactory.BetaCivFactory;
import Strategies.AgingStrategies.BetaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.DiceStrategies.FixedDiceStrategy;
import Strategies.WinningStrategies.BetaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 9/18/17.
 */
public class TestBetaCiv {

    private Game betaGame;
    private City betaRCity;
    private City betaBCity;

    @Before
    public void setUp(){
        betaGame = new GameImpl(new BetaCivFactory());
        betaRCity = betaGame.getCityAt(new Position(1, 1));
        betaBCity = betaGame.getCityAt(new Position(4, 1));
    }

    @Test
    public void shouldPass100years() {
        assertThat(betaGame.getAge(), is(-4000));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(-3900));
    }

    @Test
    public void shouldPassTo1BCAfter100BC() {
        for (int i = 0; i < 78; i++) { //78 because 4000-100 = 3900 / 100, 39 turns, so 39*2 to switch both players turns
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(-100));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(-1));
    }

    @Test
    public void shouldPassTo1ADAfter1BC() {
        for (int i = 0; i < 80; i++) { //80 because previous 78 plus 2 more switches
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(-1));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(1));
    }

    @Test
    public void shouldPassTo50ADAfter1AD() {
        for (int i = 0; i < 82; i++) { //82 because previous 80 plus 2 more switches
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(1));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(50));
    }

    @Test
    public void shouldPass50YearsAfter50AD() {
        for (int i = 0; i < 84; i++) { //84 because previous 82 plus 2 more switches
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(50));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(100));
    }

    @Test
    public void shouldPass25YearsAfter1750AD() {
        for (int i = 0; i < 152; i++) { //152 because previous 84 plus 68 more switches (1750-50 = 1700 /50 = 34 *2 = 68.
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(1750));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(1775));
    }

    @Test
    public void shouldPass5YearsAfter1900AD() {
        for (int i = 0; i < 164; i++) { //64 because previous 152 plus 12 more switches (1900-1750 = 150 /25 = 6 *2 = 12.
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(1900));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(1905));
    }

    @Test
    public void shouldPass1YearsAfter1970AD() {
        for (int i = 0; i < 192; i++) { //192 because previous 164 plus 28 more switches (1970-1900 = 70 /5 = 14 *2 = 28.
            betaGame.endOfTurn();
        }
        assertThat(betaGame.getAge(), is(1970));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        assertThat(betaGame.getAge(), is(1971));
    }

    @Test
    public void redShouldWinGameIfBlueCityIsTaken() {
        assertThat(betaBCity.getOwner(), is(Player.BLUE));
        betaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        betaGame.moveUnit(new Position(3, 1), new Position(4, 1));
        assertThat(betaBCity.getOwner(), is(Player.RED));
        assertThat(betaGame.getWinner(), is(Player.RED));
    }

    @Test
    public void blueShouldWinGameIfRedCityIsTaken() {
        betaGame.endOfTurn();
        assertThat(betaRCity.getOwner(), is(Player.RED));
        betaGame.moveUnit(new Position(3, 2), new Position(2, 1));
        betaGame.endOfTurn();
        betaGame.endOfTurn();
        betaGame.moveUnit(new Position(2, 1), new Position(1, 1));
        assertThat(betaRCity.getOwner(), is(Player.BLUE));
        assertThat(betaGame.getWinner(), is(Player.BLUE));
    }

}
