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
    public void shouldStartAtYear2000bc() {
        assertThat(game.getAge(), is(2000));
    }

    @Test
    public void shouldAddYearAfterLastPlayer() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getAge(), is(2000));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getAge(), is(2001));
    }

    @Test
    public void shoudlBeRedCityStartingPos() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shoudlBeBlueCityStartingPos() {
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(10, 1)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(10, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedCityPopulationAlways1() {
        assertThat(game.getCityAt(new Position(1, 1)).getSize(), is(1));
    }

    @Test
    public void shouldBeBlueCityPopulationAlways1() {
        assertThat(game.getCityAt(new Position(10, 1)).getSize(), is(1));
    }

    @Test
    public void shouldBeOcean() {
        assertThat(game.getTileAt(new Position(1, 2)).getTypeString(), is("Ocean"));
    }

    @Test
    public void shoudBeMountain() {
        assertThat(game.getTileAt(new Position(1, 3)).getTypeString(), is("Mountain"));
    }


    @Test
    public void shouldBeHills() {
        assertThat(game.getTileAt(new Position(3, 3)).getTypeString(), is("Hills"));
    }

    @Test
    public void shouldBePlain() {
        assertThat(game.getTileAt(new Position(1, 4)).getTypeString(), is("Plain"));
    }

    @Test
    public void shouldBeArcher() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
    }

    @Test
    public void shouldBeRedArcher() {
        assertThat(game.getUnitAt(new Position(2, 2)).getOwner(), not(Player.BLUE));
        assertThat(game.getUnitAt(new Position(2, 2)).getOwner(), is(Player.RED));

    }

    @Test
    public void shouldBeBlueLegion() {
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), is("Legion"));
        assertThat(game.getUnitAt(new Position(2, 5)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldMoveArcher1Field() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(2, 3));
        assertThat(game.getUnitAt(new Position(2, 3)).getTypeString(), is("Archer"));
    }

    @Test
    public void shouldMoveArcher1FieldAndNotDuplicate() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(2, 3));
        assertThat(game.getUnitAt(new Position(2, 3)).getTypeString(), is("Archer"));
        assertThat(game.getUnitAt(new Position(2, 2)), nullValue());
    }

    @Test
    public void shouldMoveLegion1Field() {
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), is("Legion"));
        game.moveUnit(new Position(2, 5), new Position(3, 5));
        assertThat(game.getUnitAt(new Position(3, 5)).getTypeString(), is("Legion"));
        assertThat(game.getUnitAt(new Position(2, 5)), nullValue());
    }

    @Test
    public void shouldNotWalkOnOcean() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(1, 2));
        assertThat(game.getUnitAt(new Position(1, 2)), nullValue());
    }

    @Test
    public void shouldNotWalkOnMountain() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(1, 3));
        assertThat(game.getUnitAt(new Position(1, 3)), nullValue());
    }

    @Test
    public void shouldRemoveOnEnemyAttack() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(2, 5));
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), is("Archer"));
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), not("Legion"));

    }

    @Test
    public void shouldBeRemovedWhenDefeatet() {
        assertThat(game.getUnitAt(new Position(2, 2)).getTypeString(), is("Archer"));
        game.moveUnit(new Position(2, 2), new Position(2, 5));
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), is("Archer"));
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), not("Legion"));
        game.moveUnit(new Position(2, 5), new Position(2, 6));
        assertThat(game.getUnitAt(new Position(2, 5)), nullValue());
        assertThat(game.getUnitAt(new Position(2, 6)).getTypeString(), is("Archer"));
    }

    @Test
    public void shouldBeRedCityEvenWhenItIsBluesTurn() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeBlueCity() {
        assertThat(game.getCityAt(new Position(10, 1)).getOwner(), is(Player.BLUE));
        game.moveUnit(new Position(2, 2), new Position(10, 1));
        assertThat(game.getCityAt(new Position(10, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldOvertakeRedCity() {
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
        game.moveUnit(new Position(2, 5), new Position(1, 1));
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldOnlyBeAbleToMoveOwnUnits() {
        assertThat(game.getUnitAt(new Position(2, 5)).getTypeString(), is("Legion"));
        game.moveUnit(new Position(2, 5), new Position(3, 5));
        assertThat(game.getUnitAt(new Position(3, 5)), nullValue());
    }

    @Test
    public void shouldBeRedWinnerIfYearGetsTo2010() {
        assertThat(game.getWinner(), nullValue());
        for (int i = 0; i < 21; i++) {
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(Player.RED));
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
