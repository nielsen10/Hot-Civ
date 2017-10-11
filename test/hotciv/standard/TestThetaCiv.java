package hotciv.standard;

import AbstractFactory.SemiCivFactory;
import AbstractFactory.ThetaCivFactory;
import Strategies.UnitProductionStrategies.AlphaUnitProductionStrategy;
import Strategies.UnitProductionStrategies.ThetaUnitProductionStrategy;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
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

    private Game thetaGame;
    private Game stubGame;
    private AlphaUnitProductionStrategy thetaUnitProductionStrategy;
    private CityImpl city;
    private HashMap<Position,UnitImpl> unitMap;


    @Before
    public void setUp(){
        thetaUnitProductionStrategy = new AlphaUnitProductionStrategy();
        thetaGame = new GameImpl(new ThetaCivFactory());
        stubGame = new GameStubForThetaTesting();
        city = new CityImpl(Player.RED, new Position(1,1));

    }

    @Test
    public void something() {
        thetaUnitProductionStrategy.createUnit(city,unitMap, (GameImpl) stubGame);
        
        //AlphaUnitProductionStrategy.createUnit(stubGame.getUnitAt(new Position(0,0)).getTypeString(), is(GameConstants.GALLEY));
        //assertThat(stubGame.getUnitAt(new Position(0,0)).getMoveCount(), is(2));
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
