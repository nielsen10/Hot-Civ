package hotciv.standard;

import AbstractFactory.SemiCivFactory;
import hotciv.framework.Game;
import org.junit.Before;
import org.junit.Test;

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
    public void should(){
        
    }
}
