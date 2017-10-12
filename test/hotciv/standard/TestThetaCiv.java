package hotciv.standard;

import AbstractFactory.SemiCivFactory;
import AbstractFactory.ThetaCivFactory;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
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

    /* @Test
    public void shouldMakeGalleyAfter5Round() {
        for(int i=0; i<10;i++) {
            thetaGame.endOfTurn();
        }
        assertThat(thetaGame.getCityAt(new Position(1,1)).getTreasury(), is(30));
        thetaGame.changeProductionInCityAt(new Position(1,1), GameConstants.GALLEY);
        thetaGame.endOfTurn();
        thetaGame.endOfTurn();

    } */
}
