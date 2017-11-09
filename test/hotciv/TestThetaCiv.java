package hotciv;

import AbstractFactory.ThetaCivFactory;
import Strategies.UnitProductionStrategies.ThetaUnitProductionStrategy;
import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by csdev on 10/11/17.
 */
public class TestThetaCiv {

    private GameImpl thetaGame;
    private Game stubGame;
    private ThetaUnitProductionStrategy thetaUnitProductionStrategy;
    private CityImpl city;
    private HashMap<Position,UnitImpl> unitMap = new HashMap<>();


    @Before
    public void setUp(){
        thetaGame = new GameImpl(new ThetaCivFactory());

        CityImpl redCity = new CityImpl(Player.RED, new Position(15,13));
        CityImpl blueCity = new CityImpl(Player.RED, new Position(10,13));
        thetaGame.addCity(redCity.getPosition(), redCity);
        thetaGame.addCity(blueCity.getPosition(), blueCity);

    }

    @Test
    public void shouldMakeGalleyOnOcean() {
        for(int i=0; i<10;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getCityAt(new Position(15,13)).getTreasury(), is(30));
        thetaGame.changeProductionInCityAt(new Position(15,13), GameConstants.GALLEY);
        thetaGame.endOfTurn();
        thetaGame.endOfTurn();
        assertThat(thetaGame.getUnitAt(new Position(15,13)), nullValue()); //on plains
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getTypeString(), is(GameConstants.GALLEY)); //on ocean!!! \o/

    }

    @Test
    public void shouldSubtract30TreasuryWhenProducingGalley() {
        for(int i=0; i<8;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getCityAt(new Position(15,13)).getTreasury(), is(24));
        thetaGame.changeProductionInCityAt(new Position(15,13), GameConstants.GALLEY);
        thetaGame.endOfTurn();
        thetaGame.endOfTurn();
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getTypeString(), is(GameConstants.GALLEY));
        assertThat(thetaGame.getCityAt(new Position(15,13)).getTreasury(), is(0));

    }

    @Test
    public void shouldBeAbleToMove2WithGalley() {
        thetaGame.changeProductionInCityAt(new Position(15,13), GameConstants.GALLEY);
        for(int i=0; i<10;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getTypeString(), is(GameConstants.GALLEY));

        assertThat(thetaGame.getUnitAt(new Position(14,13)).getMoveCount(), is(2));
        thetaGame.moveUnit(new Position(14,13), new Position(13,13));
        assertThat(thetaGame.getUnitAt(new Position(13,13)).getMoveCount(), is(1));
        thetaGame.moveUnit(new Position(13,13), new Position(12,13)); //moving onto land
        assertThat(thetaGame.getUnitAt(new Position(12,13)), nullValue()); //nothing because Galley cannot move to land
        //therefore Galley cannot attack units on land, because attacking is resolved after checking if the move is possible

        assertThat(thetaGame.getUnitAt(new Position(13,13)).getMoveCount(), is(1));
        thetaGame.moveUnit(new Position(13,13), new Position(12,14));
        assertThat(thetaGame.getUnitAt(new Position(12,14)).getMoveCount(), is(0));

        assertThat(thetaGame.getUnitAt(new Position(13,13)), nullValue());
        assertThat(thetaGame.getUnitAt(new Position(14,13)), nullValue());
        assertThat(thetaGame.getUnitAt(new Position(12,14)).getTypeString(), is(GameConstants.GALLEY));
    }

    @Test
    public void shouldHaveCorrectAttackAndDefensiveValues() {
        thetaGame.changeProductionInCityAt(new Position(15,13), GameConstants.GALLEY);
        for(int i=0; i<10;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getTypeString(), is(GameConstants.GALLEY));
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getAttackingStrength(), is(8));
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getDefensiveStrength(), is(2));
    }

    @Test
    public void galleyShouldColonizeOnLand() {
        thetaGame.changeProductionInCityAt(new Position(15,13), GameConstants.GALLEY);
        for(int i=0; i<10;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getUnitAt(new Position(14,13)).getTypeString(), is(GameConstants.GALLEY));
        thetaGame.performUnitActionAt(new Position(14,13)); //should make city next to other city, through clockwise order
        assertThat(thetaGame.getCityAt(new Position(15,12)).getOwner(), is(Player.RED)); //new city made by galley
        assertThat(thetaGame.getUnitAt(new Position(14,13)), nullValue());        
    }
}
