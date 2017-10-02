package hotciv.standard;

import Strategies.AttackingStrategies.AlphaAttackingStrategy;
import Strategies.WinningStrategies.AlphaWinningStrategy;
import Strategies.WorldStrategy.AlphaWorldStrategy;
import hotciv.framework.*;

import Strategies.AgingStrategies.AlphaAgingStrategy;
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

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        alphaGame = new GameImpl(new AlphaAgingStrategy(), new AlphaWinningStrategy(), null, new AlphaWorldStrategy(), new AlphaAttackingStrategy());
        rCity = alphaGame.getCityAt(new Position(1, 1));
        bCity = alphaGame.getCityAt(new Position(4, 1));
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
        assertThat(alphaGame.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void shouldBeMountain() {
        assertThat(alphaGame.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
    }


    @Test
    public void shouldBeHills() {
        assertThat(alphaGame.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void shouldBePlain() {
        assertThat(alphaGame.getTileAt(new Position(1, 4)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldBeArcher() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBeRedArcher() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getOwner(), not(Player.BLUE));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));

    }

    @Test
    public void shouldBeBlueLegion() {
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedSettler() {
        assertThat(alphaGame.getUnitAt(new Position(4, 3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(alphaGame.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldMoveArcher1Field() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldMoveArcher1FieldAndNotDuplicate() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldMoveLegion1Field() {
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        alphaGame.moveUnit(new Position(3, 2), new Position(3, 3));
        assertThat(alphaGame.getUnitAt(new Position(3, 3)).getTypeString(), is(GameConstants.LEGION));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)), nullValue());
    }

    @Test
    public void shouldNotWalkOnOcean() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 0)), nullValue());
    }

    @Test
    public void shouldNotWalkOnMountain() {
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
        alphaGame.moveUnit(new Position(3, 2), new Position(2, 2));
        assertThat(alphaGame.getUnitAt(new Position(2, 2)), nullValue());
    }

    @Test
    public void shouldRemoveOnEnemyAttack() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 1), new Position(3, 2));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), not(GameConstants.LEGION));

    }

    @Test
    public void shouldBeRemovedWhenDefeated() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 1), new Position(3, 2));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), not(GameConstants.LEGION));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 2), new Position(3, 3));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(3, 3)).getTypeString(), is(GameConstants.ARCHER));
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
        alphaGame.moveUnit(new Position(2, 0), new Position(3, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3, 1), new Position(4, 1));
        assertThat(bCity.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeRedCity() {
        alphaGame.endOfTurn();
        assertThat(rCity.getOwner(), is(Player.RED));
        alphaGame.moveUnit(new Position(3, 2), new Position(2, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(2, 1), new Position(1, 1));
        assertThat(rCity.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldOnlyBeAbleToMoveOwnUnits() {
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
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
        assertThat(alphaGame.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(alphaGame.getTileAt(new Position(5, 7)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(alphaGame.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(alphaGame.getTileAt(new Position(15, 0)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void redCityShouldProduce6Food() {
        assertThat(rCity.getTreasury(), is(0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(rCity.getTreasury(), is(6));

    }

    @Test
    public void blueCityShouldProduce6Food() {
        assertThat(bCity.getTreasury(), is(0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(bCity.getTreasury(), is(6));
    }

    @Test
    public void redCityShouldHave12FoodAfter2Rounds() {
        assertThat(rCity.getTreasury(), is(0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(rCity.getTreasury(), is(12));
    }

    @Test
    public void shouldProduce1ArcherForRed() {
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.ARCHER);
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldLoose10FoodWhenRedArcherIsProduced() {
        assertThat(rCity.getTreasury(), is(0));
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.ARCHER);
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(rCity.getTreasury(), is(2));
    }

    @Test
    public void shouldProduceBlueArcherAfter10Production() {
        alphaGame.changeProductionInCityAt(bCity.getPosition(), GameConstants.ARCHER);
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToArcher() {
        assertThat(rCity.getProduction(), nullValue());
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.ARCHER);
        assertThat(rCity.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBeAbleToChangeTroopProductionToLegion() {
        assertThat(rCity.getProduction(), nullValue());
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.LEGION);
        assertThat(rCity.getProduction(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldProduce1LegionForRed() {
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.LEGION);
        assertThat(alphaGame.getUnitAt(new Position(1, 1)), nullValue());
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldProduce1LegionForBlue() {
        alphaGame.changeProductionInCityAt(bCity.getPosition(), GameConstants.LEGION);
        assertThat(alphaGame.getUnitAt(new Position(4, 1)), nullValue());
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(4, 1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldProduce2ArcherForRed() {
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.ARCHER);
        assertThat(alphaGame.getUnitAt(new Position(1, 1)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(0, 1)), nullValue());
        assertThat(rCity.getTreasury(), is(0));
        for (int i = 0; i < 8; i++) {
            alphaGame.endOfTurn();
        }
        assertThat(alphaGame.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(rCity.getTreasury(), is(4)); //6 Production pr round, for four rounds 6*4=24 -2*10 = 4
    }

    @Test
    public void shouldProduce9ArcherForRed() {
        alphaGame.changeProductionInCityAt(rCity.getPosition(), GameConstants.ARCHER);
        assertThat(alphaGame.getUnitAt(new Position(1, 1)), nullValue());
        assertThat(alphaGame.getUnitAt(new Position(0, 0)), nullValue());
        assertThat(rCity.getTreasury(), is(0));
        for (int i = 0; i < 30; i++) {
            alphaGame.endOfTurn();
        }
        assertThat(alphaGame.getUnitAt(new Position(1, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(0, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(rCity.getTreasury(), is(0)); //6 Production pr round, for 15 rounds 6*15=90 -9*10 = 0
    }

    @Test
    public void shouldOnlyBeAbleToMove1Field() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 1), new Position(2, 8));
        assertThat(alphaGame.getUnitAt(new Position(2, 8)), is(nullValue()));
    }

    @Test
    public void shouldOnlyBeAbleToMove1TimePrRound() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 0), new Position(2, 1));
        assertThat(alphaGame.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2, 1), new Position(3, 1));
        assertThat(alphaGame.getUnitAt(new Position(3, 1)), is(nullValue()));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(2, 1), new Position(3, 1));
        assertThat(alphaGame.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldNotWalk2Steps() {
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2,0), new Position(3,0));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        assertThat(alphaGame.getUnitAt(new Position(3, 0)).getTypeString(), is(GameConstants.ARCHER));


        alphaGame.moveUnit(new Position(3, 0), new Position(3, 2));
        assertThat(alphaGame.getUnitAt(new Position(3, 0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(alphaGame.getUnitAt(new Position(3, 2)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldNotAttackOwnUnit(){
        assertThat(alphaGame.getUnitAt(new Position(2, 0)).getTypeString(), is(GameConstants.ARCHER));
        alphaGame.moveUnit(new Position(2,0), new Position(3,1));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(3,1), new Position(4,2));
        alphaGame.endOfTurn();
        alphaGame.endOfTurn();
        alphaGame.moveUnit(new Position(4,2), new Position(4,3));
        assertThat(alphaGame.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(alphaGame.getUnitAt(new Position (4,2)).getTypeString(), is(GameConstants.ARCHER));
    }
}
