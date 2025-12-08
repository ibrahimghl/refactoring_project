package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.*;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;


import java.util.ArrayList;

public class UnitTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("ibrahim"));
        
    }

    @Test
    @DisplayName("Constructor player")
    void testPlayerConstructor()
    {
        player p = new player("ibrahim", "Ghoual", "jgn", 100, new ArrayList<>());
        assertThat(p.playerName, not("ibrahim"));
        p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p.playerName, is("ibrahim"));
        p = new player("ibrahim", "Ghoual", "DWARF", 100, new ArrayList<>());
        assertThat(p.playerName, is("ibrahim"));
        p = new player("ibrahim", "Ghoual", "ARCHER", 100, new ArrayList<>());
        assertThat(p.playerName, is("ibrahim"));
    }

    @Test
    @DisplayName("removeMoney")
    void testRemoveMoney() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> p.removeMoney(200));
        p.removeMoney(50);
        assertThat(p.money, is(50));
        assertThat(p.money, not(150));
        p.removeMoney(50);
        assertThat(p.money, is(0));
    }

    @Test
    @DisplayName("addMoney")
    void testAddMoney() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(50);
        assertThat(p.money, is(150));
    }

    @Test
    @DisplayName("Construcor UpdatePlayer")
    void testUpdatePlayer() {
        UpdatePlayer p = new UpdatePlayer();
        assertNotNull(p);
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

        p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        int tailleinv = p.inventory.size();
        b = UpdatePlayer.addXp(p,10);
        int atk = p.abilities.get("ATK");
        assertNotEquals(p.inventory.size(),tailleinv);
        assertThat(p.retrieveLevel(),is(2));
        assertThat(b,is(true));
        b = UpdatePlayer.addXp(p,17);
        assertThat(p.retrieveLevel(),is(3));
        b = UpdatePlayer.addXp(p,30);
        assertThat(p.retrieveLevel(),is(4));
        b = UpdatePlayer.addXp(p,54);
        assertThat(p.retrieveLevel(),is(5));
        b = UpdatePlayer.addXp(p,1);
        assertThat(b,is(false));
        assertNotEquals(p.abilities.get("ATK"),atk);
    }

    @Test
    @DisplayName("majDeFinDeTour")
    void testMajFinTour() {
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        p.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(p);
        assertEquals("Le joueur est KO !", outContent.toString().trim());
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(2));
        boolean b = UpdatePlayer.addXp(p,100);
        UpdatePlayer.majFinDeTour(p);
        p.currenthealthpoints = 9;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(9));
        p.currenthealthpoints = 11;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(p.healthpoints));
        
        p = new player("ibrahim", "Ghoual", "DWARF", 100, new ArrayList<>());
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(2));

        p = new player("ibrahim", "Ghoual", "DWARF", 100, new ArrayList<>());
        p.currenthealthpoints = 5;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(5));

        ArrayList<String> inv = new ArrayList<String>();
        inv.add("Holy Elixir");
        p = new player("ibrahim", "Ghoual", "DWARF", 100, inv);
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(3));

        p = new player("ibrahim", "Ghoual", "ARCHER", 100, new ArrayList<>());
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(2));

        p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.addXp(p,28);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(3));

        inv.add("Magic Bow");
        p = new player("ibrahim", "Ghoual", "ARCHER", 100, inv);
        p.currenthealthpoints = 1;
        p.healthpoints = 10;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(1));

        p.currenthealthpoints = 16;
        p.healthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints,is(17 + 17/8-1));

        p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, new ArrayList<>());
        p.currenthealthpoints = 20;
        p.healthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(p.currenthealthpoints,20);
    }

    @Test
    @DisplayName("Affichage")
    void testAfficage() {
        Affichage a = new Affichage();
        assertNotNull(a);

        ArrayList<String> inv = new ArrayList<String>();
        inv.add("Holy Elixir");
        player p = new player("ibrahim", "Ghoual", "ADVENTURER", 100, inv);
        assertEquals("Joueur Ghoual joué par ibrahim\nNiveau : 1 (XP totale : 0)\n\nCapacités :\n   DEF : 1\n   ATK : 3\n   CHA : 3\n   INT : 2\n\nInventaire :\n   Holy Elixir",  Affichage.afficherJoueur(p));
    }

    @Test
    @DisplayName("Main")
    void testMain() {
        Main m = new Main();
        assertNotNull(m);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
