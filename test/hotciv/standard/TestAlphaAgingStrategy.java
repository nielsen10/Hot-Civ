package hotciv.standard;

import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AgingStrategies.BetaAgingStrategy;
import org.junit.*;

import static org.junit.Assert.*;

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
  assertEquals(-3900, alphaAgingStrategy.calculateYear());
 }

 @Test
 public void shouldBe3800CAfter2Turns(){
  assertEquals(-3900, alphaAgingStrategy.calculateYear());
  assertEquals(-3800, alphaAgingStrategy.calculateYear());
 }

 @Test
 public void shouldBe(){
  for (int i = 0; i < 39; i++) {
   betaAgingStrategy.calculateYear();
  }
  assertEquals(-1, betaAgingStrategy.calculateYear());
  assertEquals(1, betaAgingStrategy.calculateYear());
 }






}
