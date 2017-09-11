package hotciv.standard;

import hotciv.framework.*;

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
    private Game game;
    private City rCity;
    private City bCity;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {

        game = new GameImpl();
        rCity = game.getCityAt(new Position(1,1));
        bCity = game.getCityAt(new Position(4,1));
    }

    // FRS p. 455 states that 'Red is the first player to take a turn'.
    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        // TODO: reenable the assert below to get started...
        assertThat(game.getPlayerInTurn(), is(Player.RED));




    }

    @Test
    public void shouldBeBlueAfterRed() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedAgain() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void shouldStartAtYear4000bc() {
        assertThat(game.getAge(), is(4000));
    }

    @Test
    public void shouldAdd100YearsAfterLastPlayer() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getAge(), is(4000));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getAge(), is(3900));
    }

    @Test
    public void shouldBeRedCityStartingPos() {
        assertThat(rCity, is(notNullValue()));
        assertThat(rCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldBeBlueCityStartingPos() {
        game.endOfTurn();
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
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is("ocean"));
    }

    @Test
    public void shouldBeMountain() {
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is("mountain"));
    }


    @Test
    public void shouldBeHills() {
        assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is("hills"));
    }

    @Test
    public void shouldBePlain() {
        assertThat(game.getTileAt(new Position(1, 4)).getTypeString(), is("plain"));
    }

    @Test
    public void shouldBeArcher() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeRedArcher() {
        assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), not(Player.BLUE));
        assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));

    }

    @Test
    public void shouldBeBlueLegion() {
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedSettler() {
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is("settler"));
        assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldMoveArcher1Field() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldMoveArcher1FieldAndNotDuplicate() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getTypeString(), is("archer"));
        assertThat(game.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldMoveLegion1Field() {
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        game.moveUnit(new Position(3, 2), new Position(3, 3));
        assertThat(game.getUnitAt(new Position(3, 3)).getTypeString(), is("legion"));
        assertThat(game.getUnitAt(new Position(3, 2)), nullValue());
    }

    @Test
    public void shouldNotWalkOnOcean() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldNotWalkOnMountain() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(2, 2));
        assertThat(game.getUnitAt(new Position(2, 2)), nullValue());
    }

    @Test
    public void shouldRemoveOnEnemyAttack() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(3, 2));
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("archer"));
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), not("legion"));

    }

    @Test
    public void shouldBeRemovedWhenDefeatet() {
        assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
        game.moveUnit(new Position(2, 0), new Position(3, 2));
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("archer"));
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), not("legion"));
        game.moveUnit(new Position(3, 2), new Position(2, 6));
        assertThat(game.getUnitAt(new Position(3, 2)), nullValue());
        assertThat(game.getUnitAt(new Position(2, 6)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeRedCityEvenWhenItIsBluesTurn() {
        assertThat(rCity.getOwner(), is(Player.RED));
        game.endOfTurn();
        assertThat(rCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeBlueCity() {
        assertThat(bCity.getOwner(), is(Player.BLUE));
        game.moveUnit(new Position(2, 0), new Position(4, 1));
        assertThat(bCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeRedCity() {
        game.endOfTurn();
        assertThat(rCity.getOwner(), is(Player.RED));
        game.moveUnit(new Position(3, 2), new Position(1, 1));
        assertThat(rCity.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldOnlyBeAbleToMoveOwnUnits() {
        assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
        game.moveUnit(new Position(3, 2), new Position(3, 5));
        assertThat(game.getUnitAt(new Position(3, 5)), nullValue());
    }

    @Test
    public void shouldBeRedWinnerIfYearGetsTo3000() {
        assertThat(game.getWinner(), nullValue());
        for (int i = 0; i < 21; i++) {
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldBePlainsEverywhere() {
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is("plain"));
        assertThat(game.getTileAt(new Position(5, 7)).getTypeString(), is("plain"));
        assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is("plain"));
        assertThat(game.getTileAt(new Position(15, 0)).getTypeString(), is("plain"));
    }
    @Test
    public void redCityShouldProduce6Food() {
        assertThat(rCity.getTreasury(), is (0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(rCity.getTreasury(), is (6));

    }
    @Test
    public void blueCityShouldProduce6Food() {
        assertThat(bCity.getTreasury(), is (0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(bCity.getTreasury(), is (6));
    }
    @Test
    public void redCityShouldHave12FoodAfter2Rounds() {
        assertThat(rCity.getTreasury(), is (0));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(rCity.getTreasury(), is (12));
    }

    @Test
    public void shouldProduce1ArcherForRed(){
        game.changeProductionInCityAt(rCity.getPosition(),"archer");
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldLoose10FoodWhenRedArcherIsProduced(){
        assertThat(rCity.getTreasury(), is (0));
        game.changeProductionInCityAt(rCity.getPosition(),"archer");
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
        assertThat(rCity.getTreasury(), is (2));
    }

    @Test
    public void shouldProduceBlueArcherAfter10Production(){
        game.changeProductionInCityAt(bCity.getPosition(),"archer");
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4,1)).getTypeString(), is("archer"));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToArcher(){
        assertThat(rCity.getProduction(), nullValue());
        game.changeProductionInCityAt(rCity.getPosition(),"archer");
        assertThat(rCity.getProduction(), is("archer"));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToLegion(){
        assertThat(rCity.getProduction(), nullValue());
        game.changeProductionInCityAt(rCity.getPosition(),"legion");
        assertThat(rCity.getProduction(), is("legion"));
    }

    @Test
    public void shouldProduce1LegionForRed(){
        game.changeProductionInCityAt(rCity.getPosition(),"legion");
        assertThat(game.getUnitAt(new Position(1,1)), nullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is("legion"));
    }

    @Test
    public void shouldProduce1LegionForBlue(){
        game.changeProductionInCityAt(bCity.getPosition(),"legion");
        assertThat(game.getUnitAt(new Position(4,1)), nullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4,1)).getTypeString(), is("legion"));
    }

    @Test
    public void shouldProduce2ArcherForRed(){
        game.changeProductionInCityAt(rCity.getPosition(),"archer");
        assertThat(game.getUnitAt(new Position(1,1)), nullValue());
        assertThat(game.getUnitAt(new Position(0,1)), nullValue());
        assertThat(rCity.getTreasury(), is (0));
        for (int i = 0; i < 8; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is("archer"));
        assertThat(game.getUnitAt(new Position(0,1)).getTypeString(), is("archer"));
        assertThat(rCity.getTreasury(), is (4)); //6 Production pr round, for four rounds 6*4=24 -2*10 = 4
    }

}
