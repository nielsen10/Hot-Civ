package hotciv.standard;

import AbstractFactory.SemiCivFactory;
import AbstractFactory.ThetaCivFactory;
import hotciv.framework.Game;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by csdev on 10/11/17.
 */
public class TestThetaCiv {

    private Game thetaGame;

    @Before
    public void setUp(){
        thetaGame = new GameImpl(new ThetaCivFactory());
    }


}
