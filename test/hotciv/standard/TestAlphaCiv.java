package hotciv.standard;

import com.sun.istack.internal.NotNull;
import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

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

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {

        game = new GameImpl();
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
    public void shoudlBeRedCityStartingPos() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shoudlBeBlueCityStartingPos() {
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(4, 1)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedCityPopulationAlways1() {
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
    }

    @Test
    public void shouldBeBlueCityPopulationAlways1() {
        assertThat(game.getCityAt(new Position(4, 1)).getSize(), is(1));
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
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeBlueCity() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
        game.moveUnit(new Position(2, 0), new Position(4, 1));
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeRedCity() {
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(3, 2), new Position(1, 1));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
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
        CityImpl city = (CityImpl) game.getCityAt(new Position(1,1));
        assertThat(city.getFood(), is (0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(city.getFood(), is (6));

    }
    @Test
    public void blueCityShouldProduce6Food() {
        CityImpl city = (CityImpl) game.getCityAt(new Position(4,1));
        assertThat(city.getFood(), is (0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(city.getFood(), is (6));

    }


    /**
     * REMOVE ME. Not a test of HotCiv, just an example of what
     * matchers the hamcrest library has...
     */
    @Test
    public void shouldDefinetelyBeRemoved() {
        // Matching null and not null values
        // 'is' require an exact match
        String s = null;
        assertThat(s, is(nullValue()));
        s = "Ok";
        assertThat(s, is(notNullValue()));
        assertThat(s, is("Ok"));

        // If you only validate substrings, use containsString
        assertThat("This is a dummy test", containsString("dummy"));

        // Match contents of Lists
        List<String> l = new ArrayList<String>();
        l.add("Bimse");
        l.add("Bumse");
        // Note - ordering is ignored when matching using hasItems
        assertThat(l, hasItems(new String[]{"Bumse", "Bimse"}));

        // Matchers may be combined, like is-not
        assertThat(l.get(0), is(not("Bumse")));
    }
}
