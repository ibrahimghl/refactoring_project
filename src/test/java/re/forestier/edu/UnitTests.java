package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("Florian"));
        
    }

    @Test
    @DisplayName("Constructor player")
    void testPlayerConstructor()
    {
        player p = new player("Florian", "Grognak le barbare", "jgn", 100, new ArrayList<>());
        assertThat(p.playerName, not("Florian"));
        p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p.playerName, is("Florian"));
        p = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertThat(p.playerName, is("Florian"));
        p = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertThat(p.playerName, is("Florian"));
    }

    @Test
    @DisplayName("removeMoney")
    void testRemoveMoney() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> p.removeMoney(200));
        p.removeMoney(50);
        assertThat(p.money, is(50));
    }

    @Test
    @DisplayName("addMoney")
    void testAddMoney() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(50);
        assertThat(p.money, is(150));
    }

    @Test
    @DisplayName("ex & lvl")
    void testRetrieveLevel() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        boolean b;
        b = UpdatePlayer.addXp(p,5);
        assertThat(p.getXp(),is(5));
        assertThat(p.retrieveLevel(),is(1));
        b = UpdatePlayer.addXp(p,6);
        assertThat(p.retrieveLevel(),is(2));
        b = UpdatePlayer.addXp(p,22);
        assertThat(p.retrieveLevel(),is(3));
        b = UpdatePlayer.addXp(p,30);
        assertThat(p.retrieveLevel(),is(4));
        b = UpdatePlayer.addXp(p,100);
        assertThat(p.retrieveLevel(),is(5));
    }

}
