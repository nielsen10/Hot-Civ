package hotciv;

import hotciv.framework.Game;
import org.junit.Before;
import AbstractFactory.AlphaCivFactory;
import hotciv.framework.*;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by csdev on 10/28/17.
 */
public class GameDecoratorTest {

    private Game game;
    private Game decoratorGame;
    private PrintStream oldOut;
    private ByteArrayOutputStream baos;

    @Before
    public void setUp(){
        game = new GameImpl(new AlphaCivFactory());
        decoratorGame = game;

        oldOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

    }

    public void toggleDecorator() {
        if ( game == decoratorGame ) { // enable the logging by decorating the component
            decoratorGame = game;     // but remember the component
            game = new GameDecorator(game);
            System.out.println("decorator on");
        } else {
            // remove logging by making payStation point to
            // the component object once again
            game = decoratorGame ;
            System.out.println("decorator off");
        }
    }

    @Test
    public void shouldPrintStuff(){
        System.out.println("test game 1");
        toggleDecorator();

        game.moveUnit(new Position(2,0), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);

        System.setOut(oldOut);
        String output = new String(baos.toByteArray());

        assertTrue(output.contains("RED moves archer from [2,0] to [3,1]"));
        assertTrue(output.contains("RED changes production in city at [1,1] to archer"));
        assertTrue(output.contains("RED changes workforce focus in city at [1,1] to apple"));
        assertTrue(output.contains("RED ends turn"));
        assertTrue(output.contains("BLUE moves legion from [3,2] to [3,1]"));
        assertTrue(output.contains("BLUE changes production in city at [1,1] to archer"));
        assertTrue(output.contains("BLUE changes workforce focus in city at [1,1] to apple"));

        System.out.println(output);
    }

    @Test
    public void shouldPrintUntilTurnedOff(){
        System.out.println("test game 2");
        toggleDecorator();

        game.moveUnit(new Position(2,0), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);
        game.endOfTurn();

        toggleDecorator();

        game.moveUnit(new Position(3,2), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);

        System.setOut(oldOut);
        String output = new String(baos.toByteArray());

        assertTrue(output.contains("RED moves archer from [2,0] to [3,1]"));
        assertTrue(output.contains("RED changes production in city at [1,1] to archer"));
        assertTrue(output.contains("RED changes workforce focus in city at [1,1] to apple"));
        assertTrue(output.contains("RED ends turn"));
        assertFalse(output.contains("BLUE moves legion from [3,2] to [3,1]"));
        assertFalse(output.contains("BLUE changes production in city at [1,1] to archer"));
        assertFalse(output.contains("BLUE changes workforce focus in city at [1,1] to apple"));

        System.out.println(output);
    }

    @Test
    public void shouldTurnOffThenOnAgain(){
        System.out.println("test game 3");
        toggleDecorator();

        game.moveUnit(new Position(2,0), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);
        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);
        game.endOfTurn();

        toggleDecorator();

        game.moveUnit(new Position(3,2), new Position(3,1));
        game.changeProductionInCityAt(new Position(1,1), GameConstants.ARCHER);

        toggleDecorator();

        //game.performUnitActionAt(new Position(3,1)); //works but nullpointerexception because of AlphaGame
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);

        System.setOut(oldOut);
        String output = new String(baos.toByteArray());

        assertTrue(output.contains("RED moves archer from [2,0] to [3,1]"));
        assertTrue(output.contains("RED changes production in city at [1,1] to archer"));
        assertTrue(output.contains("RED changes workforce focus in city at [1,1] to apple"));
        assertTrue(output.contains("RED ends turn"));
        assertFalse(output.contains("BLUE moves legion from [3,2] to [3,1]"));
        assertFalse(output.contains("BLUE changes production in city at [1,1] to archer"));
        assertTrue(output.contains("BLUE changes workforce focus in city at [1,1] to apple"));

        System.out.println(output);
    }
}
