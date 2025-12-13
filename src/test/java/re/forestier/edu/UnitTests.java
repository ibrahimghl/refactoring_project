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
        Player Player = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(Player.getPlayerName(), is("Florian"));
        
    }

    @Test
    @DisplayName("Constructor Player")
    void testPlayerConstructor()
    {
        Player p = new Player("Florian", "Grognak le barbare", "jgn", 100, new ArrayList<>());
        assertThat(p.getPlayerName(), not("Florian"));
        p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
        p = new Player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
        p = new Player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
    }

    @Test
    @DisplayName("removeMoney")
    void testRemoveMoney() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> p.removeMoney(200));
        p.removeMoney(50);
        assertThat(p.getMoney(), is(50));
        assertThat(p.getMoney(), not(150));
        p.removeMoney(50);
        assertThat(p.getMoney(), is(0));
    }

    @Test
    @DisplayName("addMoney")
    void testAddMoney() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(50);
        assertThat(p.getMoney(), is(150));
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
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        boolean b;
        p.addXp(5);
        assertThat(p.getXp(),is(5));
        assertThat(p.getLevel(),is(1));
        p.addXp(6);
        assertThat(p.getLevel(),is(2));
        p.addXp(22);
        assertThat(p.getLevel(),is(3));
        p.addXp(30);
        assertThat(p.getLevel(),is(4));
        p.addXp(100);
        assertThat(p.getLevel(),is(5));

        p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        int tailleinv = p.inventory.size();
        p.addXp(10);
        int atk = p.abilities.get("ATK");
        assertNotEquals(p.inventory.size(),tailleinv);
        assertThat(p.getLevel(),is(2));
        p.addXp(17);
        assertThat(p.getLevel(),is(3));
        p.addXp(30);
        assertThat(p.getLevel(),is(4));
        p.addXp(54);
        assertThat(p.getLevel(),is(5));
        p.addXp(1);
        assertNotEquals(p.abilities.get("ATK"),atk);
    }

    @Test
    @DisplayName("majDeFinDeTour")
    void testMajFinTour() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.setCurrentHealthPoints(0);
        UpdatePlayer.majFinDeTour(p);
        assertEquals("Le joueur est KO !", outContent.toString().trim());
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));
        p.addXp(100);
        UpdatePlayer.majFinDeTour(p);
        p.setCurrentHealthPoints(9);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(9));
        p.setCurrentHealthPoints(11);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(p.getHealthPoints()));
        
        p = new Player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));

        p = new Player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        p.setCurrentHealthPoints(5);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(5));

        ArrayList<String> inv = new ArrayList<String>();
        inv.add("Holy Elixir");
        p = new Player("Florian", "Grognak le barbare", "DWARF", 100, inv);
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(3));

        p = new Player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));

        p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        p.addXp(28);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(3));

        inv.add("Magic Bow");
        p = new Player("Florian", "Grognak le barbare", "ARCHER", 100, inv);
        p.setCurrentHealthPoints(1);
        p.setHealthPoints(10);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(1));
        p.setCurrentHealthPoints(16);
        p.setHealthPoints(40);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(17 + 17/8-1));

        p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.setCurrentHealthPoints(20);
        p.setHealthPoints(40);
        UpdatePlayer.majFinDeTour(p);
        assertEquals(p.getCurrentHealthPoints(),20);
    }

    @Test
    @DisplayName("Affichage")
    void testAfficage() {
        Affichage a = new Affichage();
        assertNotNull(a);

        ArrayList<String> inv = new ArrayList<String>();
        inv.add("Holy Elixir");
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, inv);
        assertEquals("Joueur Grognak le barbare joué par Florian\nNiveau : 1 (XP totale : 0)\n\nCapacités :\n   DEF : 1\n   ATK : 3\n   CHA : 3\n   INT : 2\n\nInventaire :\n   Holy Elixir",  Affichage.afficherJoueur(p));
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
