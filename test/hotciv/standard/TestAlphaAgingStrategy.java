package hotciv.standard;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WinningStrategies.BetaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import Strategies.WorldStrategy.DeltaWorldStrategy;
import Strategies.unitActionStrategies.GammaUnitActionStrategy;
import hotciv.framework.*;

import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AgingStrategies.BetaAgingStrategy;
import org.junit.*;

import java.time.Year;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by csdev on 9/18/17.
 */
public class TestAlphaAgingStrategy {

 private AlphaAgingStrategy alphaAgingStrategy;
 private BetaAgingStrategy betaAgingStrategy;

 @Before
 public void setUp(){
  alphaAgingStrategy = new AlphaAgingStrategy();
  betaAgingStrategy = new BetaAgingStrategy();
 }

 @Test
 public void shouldBe3900BCAfter1Turn(){
  assertEquals(-3900, alphaAgingStrategy.endOfTurn());
 }

 @Test
 public void shouldBe3800CAfter2Turns(){
  assertEquals(-3900, alphaAgingStrategy.endOfTurn());
  assertEquals(-3800, alphaAgingStrategy.endOfTurn());
 }

 @Test
 public void shouldBe(){
  for (int i = 0; i < 39; i++) {
   betaAgingStrategy.endOfTurn();
  }
  assertEquals(-1, betaAgingStrategy.endOfTurn());
  assertEquals(1, betaAgingStrategy.endOfTurn());
 }






}
