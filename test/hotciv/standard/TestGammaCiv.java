package hotciv.standard;

import AbstractFactory.GammaCivFactory;
import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import Strategies.UnitActionStrategies.GammaUnitActionStrategy;
import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 9/18/17.
 */
public class TestGammaCiv {

    private Game gammaGame;
    private City gammaRCity;
    private City gammaBCity;
    private City rCity;
    private City bCity;

    @Before
    public void setUp(){
        gammaGame = new GameImpl(new GammaCivFactory());
        gammaRCity = gammaGame.getCityAt(new Position(1, 1));
        gammaBCity = gammaGame.getCityAt(new Position(4, 1));
        rCity = gammaGame.getCityAt(new Position(1, 1));
        bCity = gammaGame.getCityAt(new Position(4, 1));
    }

    @Test
    public void redSettlerActionShouldMakeCity() {
        gammaGame.performUnitActionAt(new Position(4, 3));
        assertThat(gammaGame.getUnitAt(new Position(4, 3)), is(nullValue()));
        assertThat(gammaGame.getCityAt(new Position(4, 3)).getOwner(), is(Player.RED));
    }

    @Test
    public void blueCityCanProduceSettler() {
        gammaGame.changeProductionInCityAt(gammaBCity.getPosition(), GameConstants.SETTLER);
        assertThat(gammaGame.getUnitAt(new Position(4, 1)), nullValue());
        for (int i = 0; i < 101; i++) {
            gammaGame.endOfTurn();
        }
        assertThat(gammaGame.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void blueCityCanProduceSettlerAndItCanMoveAndMakeNewCity() {
        gammaGame.changeProductionInCityAt(bCity.getPosition(), GameConstants.SETTLER);
        assertThat(gammaGame.getUnitAt(new Position(4, 1)), nullValue());
        for (int i = 0; i < 40; i++) {
            gammaGame.endOfTurn();
        }
        assertThat(gammaGame.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.SETTLER));
        gammaGame.moveUnit(new Position(4, 1), new Position(4, 2));
        gammaGame.performUnitActionAt(new Position(4, 2));
    }

    @Test
    public void redSettlerActionShouldMakeCityAndPopulation1() {
        gammaGame.performUnitActionAt(new Position(4, 3));
        assertThat(gammaGame.getUnitAt(new Position(4, 3)), is(nullValue()));
        assertThat(gammaGame.getCityAt(new Position(4, 3)).getOwner(), is(Player.RED));
        assertThat(gammaGame.getCityAt(new Position(4, 3)).getSize(), is(1));
    }

    @Test
    public void shouldDoubleArcherDefenceStrengthWhenFortify() {
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2, 0));
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(6));
    }

    @Test
    public void shouldNotBeAbleToMoveFortifiedArcher() {
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2, 0));
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(6));
        gammaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(gammaGame.getUnitAt(new Position(2, 1)), is(nullValue()));
    }

    @Test
    public void shouldBeAbleToMoveArcherWhenFortifyIsRevoked() {
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2, 0));
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(6));
        gammaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(gammaGame.getUnitAt(new Position(2, 1)), is(nullValue()));
        gammaGame.performUnitActionAt(new Position(2, 0));
        gammaGame.endOfTurn();
        gammaGame.endOfTurn();
        gammaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(gammaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldReduceDefensiveStrengthWhenForifyIsRevoked() {
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2, 0));
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(6));
        gammaGame.endOfTurn();
        gammaGame.endOfTurn();
        gammaGame.performUnitActionAt(new Position(2, 0));
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getDefensiveStrength(), is(3));
    }
}
