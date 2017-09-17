package hotciv.standard;

import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WinningStrategies.BetaWinningStrategy;
import Strategies.unitActionStrategies.GammaUnitActionStrategy;
import hotciv.framework.*;

import Strategies.AgingStrategies.AlphaAgingStrategy;
import Strategies.AgingStrategies.BetaAgingStrategy;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Oct 2015 for using Hamcrest matchers
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestAlphaCiv {
    private Game alphaGame;
    private City rCity;
    private City bCity;
    private Game betaGame;
    private City betaRCity;
    private City betaBCity;
    private GameImpl gammaGame;
    private City gammaRCity;
    private City gammaBCity;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {

        alphaGame = new GameImpl(new AlphaAgingStrategy(), new AlphaWinningStrategy(),null);
        betaGame = new GameImpl(new BetaAgingStrategy(), new BetaWinningStrategy(), null);
        gammaGame = new GameImpl(new AlphaAgingStrategy(),  new AlphaWinningStrategy(), new GammaUnitActionStrategy() );
        rCity = alphaGame.getCityAt(new Position(1,1));
        bCity = alphaGame.getCityAt(new Position(4,1));
        betaRCity = betaGame.getCityAt(new Position(1,1));
        betaBCity = betaGame.getCityAt(new Position(4,1));
        gammaRCity = betaGame.getCityAt(new Position(1,1));
        gammaBCity = betaGame.getCityAt(new Position(4,1));
    }

    // FRS p. 455 states that 'Red is the first player to take a turn'.
    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(alphaGame, is(notNullValue()));
        // TODO: reenable the assert below to get started...
        assertThat(alphaGame.getPlayerInTurn(), is(Player.RED));

    }

    @Test
    public void shouldBeBlueAfterRed() {
        alphaGame.endOfTurn();
        assertThat(alphaGame.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedAgain() {
        assertThat(alphaGame.getPlayerInTurn(), is(Player.RED));
        alphaGame.endOfTurn();
        assertThat(alphaGame.getPlayerInTurn(), is(Player.BLUE));
        alphaGame.endOfTurn();
        assertThat(alphaGame.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void shouldStartAtYear4000bc() {
        assertThat(alphaGame.getAge(), is(-4000));
    }

    @Test
    public void shouldAdd100YearsAfterLastPlayer() {
        assertThat(alphaGame.getPlayerInTurn(), is(Player.RED));
        assertThat(alphaGame.getAge(), is(-4000));
        alphaGame.endOfTurn();
        assertThat(alphaGame.getPlayerInTurn(), is(Player.BLUE));
        alphaGame.endOfTurn();
        assertThat(alphaGame.getPlayerInTurn(), is(Player.RED));
        assertThat(alphaGame.getAge(), is(-3900));
    }

    @Test
    public void shouldBeRedCityStartingPos() {
        assertThat(rCity, is(notNullValue()));
        assertThat(rCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldBeBlueCityStartingPos() {
        alphaGame.endOfTurn();
        assertThat(bCity, is(notNullValue()));
        assertThat(bCity.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedCityPopulationAlways1() {
        assertThat(rCity.getSize(), is(1));
    }

    @Test
    public void shouldBeBlueCityPopulationAlways1() {
        assertThat(bCity.getSize(), is(1));
    }

    @Test
    public void shouldBeOcean() {
        assertThat(alphaGame.getTileAt(new Position(1, 0)).getTypeString(), is("ocean"));
    }

    @Test
    public void shouldBeMountain() {
        assertThat(alphaGame.getTileAt(new Position(2, 2)).getTypeString(), is("mountain"));
    }


    @Test
    public void shouldBeHills() {
        assertThat(alphaGame.getTileAt(new Position(0, 1)).getTypeString(), is("hills"));
    }

    @Test
    public void shouldBePlain() {
        assertThat(alphaGame.getTileAt(new Position(1, 4)).getTypeString(), is("plain"));
    }

    @Test
    public void shouldBeArcher() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeRedArcher() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getOwner(), not(Player.BLUE));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));

    }

    @Test
    public void shouldBeBlueLegion() {
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedSettler() {
        assertThat(alphaGame.getUnitAt(new Position(4,3)).getTypeString(), is("settler"));
        assertThat(alphaGame.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldMoveArcher1Field() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldMoveArcher1FieldAndNotDuplicate() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldMoveLegion1Field() {
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        alphaGame.moveUnit(new Position(3, 2), new Position(3, 3));
        assertThat(alphaGame.getUnitAt(new Position(3, 3)).getTypeString(), is("legion"));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)), nullValue());
    }

    @Test
    public void shouldNotWalkOnOcean() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldNotWalkOnMountain() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 2));
        assertThat(alphaGame.getUnitAt(new Position(2, 2)), nullValue());
    }

    @Test
    public void shouldRemoveOnEnemyAttack() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 1), new Position(3, 2));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is("archer"));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), not("legion"));

    }

    @Test
    public void shouldBeRemovedWhenDefeatet() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        alphaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 1), new Position(3, 2));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is("archer"));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), not("legion"));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 2), new Position(3, 3));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(3, 3)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeRedCityEvenWhenItIsBluesTurn() {
        assertThat(rCity.getOwner(), is(Player.RED));
        alphaGame.endOfTurn();
        assertThat(rCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeBlueCity() {
        assertThat(bCity.getOwner(), is(Player.BLUE));
        alphaGame.moveUnit(new Position(2, 0), new Position(4, 1));
        assertThat(bCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeRedCity() {
        alphaGame.endOfTurn();
        assertThat(rCity.getOwner(), is(Player.RED));
        alphaGame.moveUnit(new Position(3, 2), new Position(1, 1));
        assertThat(rCity.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldOnlyBeAbleToMoveOwnUnits() {
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        alphaGame.moveUnit(new Position(3, 2), new Position(3, 5));
        assertThat(alphaGame.getUnitAt(new Position(3, 5)), nullValue());
    }

    @Test
    public void shouldBeRedWinnerIfYearGetsTo3000() {
        assertThat(alphaGame.getWinner(), nullValue());
        for (int i = 0; i < 21; i++) {
            alphaGame.endOfTurn();
        }
        assertThat(alphaGame.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldBePlainsEverywhere() {
        assertThat(alphaGame.getTileAt(new Position(0, 0)).getTypeString(), is("plain"));
        assertThat(alphaGame.getTileAt(new Position(5, 7)).getTypeString(), is("plain"));
        assertThat(alphaGame.getTileAt(new Position(15, 15)).getTypeString(), is("plain"));
        assertThat(alphaGame.getTileAt(new Position(15, 0)).getTypeString(), is("plain"));
    }
    @Test
    public void redCityShouldProduce6Food() {
        assertThat(rCity.getTreasury(), is (0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(rCity.getTreasury(), is (6));

    }
    @Test
    public void blueCityShouldProduce6Food() {
        assertThat(bCity.getTreasury(), is (0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(bCity.getTreasury(), is (6));
    }
    @Test
    public void redCityShouldHave12FoodAfter2Rounds() {
        assertThat(rCity.getTreasury(), is (0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(rCity.getTreasury(), is (12));
    }

    @Test
    public void shouldProduce1ArcherForRed(){
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"archer");
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldLoose10FoodWhenRedArcherIsProduced(){
        assertThat(rCity.getTreasury(), is (0));
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"archer");
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
        assertThat(rCity.getTreasury(), is (2));
    }

    @Test
    public void shouldProduceBlueArcherAfter10Production(){
        alphaGame.changeProductionInCityAt(bCity.getPosition(),"archer");
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(4,1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToArcher(){
        assertThat(rCity.getProduction(), nullValue());
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"archer");
        assertThat(rCity.getProduction(), is("archer"));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToLegion(){
        assertThat(rCity.getProduction(), nullValue());
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"legion");
        assertThat(rCity.getProduction(), is("legion"));
    }

    @Test
    public void shouldProduce1LegionForRed(){
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"legion");
        assertThat(alphaGame.getUnitAt(new Position(1,1)), nullValue());
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1,1)).getTypeString(), is("legion"));
    }

    @Test
    public void shouldProduce1LegionForBlue(){
        alphaGame.changeProductionInCityAt(bCity.getPosition(),"legion");
        assertThat(alphaGame.getUnitAt(new Position(4,1)), nullValue());
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(4,1)).getTypeString(), is("legion"));
    }

    @Test
    public void shouldProduce2ArcherForRed(){
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"archer");
        assertThat(alphaGame.getUnitAt(new Position(1,1)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(0,1)), nullValue());
        assertThat(rCity.getTreasury(), is (0));
        for (int i = 0; i < 8; i++) {
            alphaGame.endOfTurn();
        }
        assertThat(alphaGame.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
        assertThat(alphaGame.getUnitAt(new Position(0,1)).getTypeString(), is("archer"));
        assertThat(rCity.getTreasury(), is (4)); //6 Production pr round, for four rounds 6*4=24 -2*10 = 4
    }

    @Test
    public void shouldProduce9ArcherForRed(){
        alphaGame.changeProductionInCityAt(rCity.getPosition(),"archer");
        assertThat(alphaGame.getUnitAt(new Position(1,1)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(0,0)), nullValue());
        assertThat(rCity.getTreasury(), is (0));
        for (int i = 0; i < 30; i++) {
            alphaGame.endOfTurn();
        }
        assertThat(alphaGame.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
        assertThat(alphaGame.getUnitAt(new Position(0,0)).getTypeString(), is("archer"));
        assertThat(rCity.getTreasury(), is (0)); //6 Production pr round, for 15 rounds 6*15=90 -9*10 = 0
    }

    @Test
    public void shouldPass100years(){
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
    public void redShouldWinGameIfBlueCityIsTaken(){
        assertThat(betaBCity.getOwner(), is(Player.BLUE));
        betaGame.moveUnit(new Position(2, 0), new Position(4, 1));
        assertThat(betaBCity.getOwner(), is(Player.RED));
        assertThat(betaGame.getWinner(), is (Player.RED));
    }
    @Test
    public void blueShouldWinGameIfRedCityIsTaken(){
        betaGame.endOfTurn();
        assertThat(betaRCity.getOwner(), is(Player.RED));
        betaGame.moveUnit(new Position(3, 2), new Position(1, 1));
        assertThat(betaRCity.getOwner(), is(Player.BLUE));
        assertThat(betaGame.getWinner(), is (Player.BLUE));
    }
    @Test
    public void redSettlerActionShouldMakeCity(){
        gammaGame.performUnitActionAt(new Position(4,3));
        assertThat(gammaGame.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(gammaGame.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }
    @Test
    public void blueCityCanProduceSettler(){
        gammaGame.changeProductionInCityAt(gammaBCity.getPosition(), "settler");
        assertThat(gammaGame.getUnitAt(new Position(4,1)), nullValue());
        for(int i = 0; i <101; i ++ ){ gammaGame.endOfTurn();}
        assertThat(gammaGame.getUnitAt(new Position(4,1)).getTypeString(), is("settler"));
    }
    @Test
    public void blueCityCanProduceSettlerAndItCanMoveAndMakeNewCity(){
        gammaGame.changeProductionInCityAt(bCity.getPosition(), "settler");
        assertThat(gammaGame.getUnitAt(new Position(4,1)), nullValue());
        for(int i = 0; i < 40; i++){gammaGame.endOfTurn();}
        assertThat(gammaGame.getUnitAt(new Position(4,1)).getTypeString(), is("settler"));
        gammaGame.moveUnit(new Position(4,1), new Position(4,2));
        gammaGame.performUnitActionAt(new Position(4,2));
    }
    @Test
    public void redSettlerActionShouldMakeCityAndPopulation1(){
        gammaGame.performUnitActionAt(new Position(4,3));
        assertThat(gammaGame.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(gammaGame.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
        assertThat(gammaGame.getCityAt(new Position(4,3)).getSize(), is(1));
    }

    @Test
    public void shouldDoubleArcherDefenceStrengthWhenFortify(){
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2,0));
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
    }
    @Test
    public void shouldOnlyBeAbleToMove1Field(){
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        gammaGame.moveUnit(new Position(2,0),new Position(2,1));
        assertThat(gammaGame.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
        gammaGame.moveUnit(new Position(2,1),new Position(2,8));
        assertThat(gammaGame.getUnitAt(new Position(2, 8)), is(nullValue()));
    }
    @Test
    public void shouldOnlyBeAbleToMove1TimePrRound(){
        assertThat(gammaGame.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        gammaGame.moveUnit(new Position(2,0),new Position(2,1));
        assertThat(gammaGame.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
        gammaGame.moveUnit(new Position(2,1),new Position(3,1));
        assertThat(gammaGame.getUnitAt(new Position(3, 1)), is(nullValue()));
        gammaGame.endOfTurn();
        gammaGame.endOfTurn();
        gammaGame.moveUnit(new Position(2,1),new Position(3,1));
        assertThat(gammaGame.getUnitAt(new Position(3, 1)).getTypeString(), is("archer"));
    }
    @Test
    public void shouldNotBeAbleToMoveFortifiedArcher(){
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2,0));
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
        gammaGame.moveUnit(new Position(2,0 ),new Position(2,1));
        assertThat(gammaGame.getUnitAt(new Position(2,1)), is(nullValue()));
    }
    @Test
    public void shouldBeAbleToMoveArcherWhenFortifyIsRevoked(){
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2,0));
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
        gammaGame.moveUnit(new Position(2,0 ),new Position(2,1));
        assertThat(gammaGame.getUnitAt(new Position(2,1)), is(nullValue()));
        gammaGame.performUnitActionAt(new Position(2,0));
        gammaGame.endOfTurn();
        gammaGame.endOfTurn();
        gammaGame.moveUnit(new Position(2,0), new Position(2,1));
        assertThat(gammaGame.getUnitAt(new Position(2,1)).getTypeString(), is("archer"));
    }
    @Test
    public void shouldReduceDefensiveStrengthWhenForifyIsRevoked(){
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        gammaGame.performUnitActionAt(new Position(2,0));
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
        gammaGame.endOfTurn();
        gammaGame.endOfTurn();
        gammaGame.performUnitActionAt(new Position(2,0));
        assertThat(gammaGame.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
    }
}
