package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.*;
import java.util.HashMap;
import java.io.PrintStream;
import re.forestier.edu.lib.*;
import java.io.ByteArrayOutputStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        Player Player = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
        assertThat(Player.getPlayerName(), is("Florian"));
        
    }

    @Test
    @DisplayName("Constructor Player")
    void testPlayerConstructor()
    {
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
        p = new Player("Florian", "Grognak le barbare", Jobs.DWARF, 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
        p = new Player("Florian", "Grognak le barbare", Jobs.ARCHER, 100, new ArrayList<>());
        assertThat(p.getPlayerName(), is("Florian"));
        assertThat(p.getAvatarName(), is("Grognak le barbare"));
    }

    @Test
    @DisplayName("removeMoney")
    void testRemoveMoney() {
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> p.removeMoney(200));
        p.removeMoney(50);
        assertThat(p.getMoney(), is(50));
        assertThat(p.getMoney(), not(150));
        p.removeMoney(50);
        assertThat(p.getMoney(), is(0));
    }

    @Test
    @DisplayName("Heal and hurt")
    void testHealAndHurt()
    {
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> p.heal(-1));
        assertThrows(IllegalArgumentException.class, () -> p.hurt(-1));

        
    }

    @Test
    @DisplayName("addMoney")
    void testAddMoney() {
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
        p.addMoney(50);
        assertThat(p.getMoney(), is(150));
    }

    @Test
    @DisplayName("Construcor Manager")
    void testManager() {
        Manager p = new Manager();
        assertNotNull(p);
    }

    @Test
    @DisplayName("ex & lvl")
    void testRetrieveLevel() {
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
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

        p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>());
        int tailleinv = p.inventory.size();
        p.addXp(10);
        /*fail(p.avatarClass.getAbilityPerLevel().toString());
        fail(p.abilities.toString());*/
        int atk = p.abilities.get(Ability.ATK);
        assertNotEquals(p.inventory.size(),tailleinv);
        assertThat(p.getLevel(),is(2));
        p.addXp(17);
        assertThat(p.getLevel(),is(3));
        p.addXp(30);
        assertThat(p.getLevel(),is(4));
        p.addXp(54);
        assertThat(p.getLevel(),is(5));
        p.addXp(1);
        assertNotEquals(p.abilities.get(Ability.ATK),atk);
    }

    @Test
    @DisplayName("majDeFinDeTour")
    void testMajFinTour() {
        assertEquals(Ability.ATK.toString(),"ATK");
        Player p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>(),10);
        p.hurt(10);
        Manager.majFinDeTour(p);
        assertEquals("Le joueur est KO !", outContent.toString().trim());
        p.heal(1);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));
        p.addXp(100);
        Manager.majFinDeTour(p);
        p.heal(5);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(9));
        p.heal(11);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(p.getMaxHealthPoints()));
        
        p = new Player("Florian", "Grognak le barbare", Jobs.DWARF, 100, new ArrayList<>(),10);
        p.hurt(9);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));

        p = new Player("Florian", "Grognak le barbare", Jobs.DWARF, 100, new ArrayList<>(),10);
        p.hurt(5);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(5));

        ArrayList<String> inv = new ArrayList<String>();
        inv.add("Holy Elixir");
        p = new Player("Florian", "Grognak le barbare", Jobs.DWARF, 100, inv,10);
        p.hurt(9);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(3));

        p = new Player("Florian", "Grognak le barbare", Jobs.ARCHER, 100, new ArrayList<>(),10);
        p.hurt(9);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2));

        p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>(),10);
        p.hurt(9);
        p.addXp(28);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(3));

        inv.add("Magic Bow");
        p = new Player("Florian", "Grognak le barbare", Jobs.ARCHER, 100, inv,10);
        p.hurt(9);;
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(2)); //Suite a discussion avec le client, le "Magic bow" ne peut que soigner
        p = new Player("Florian", "Grognak le barbare", Jobs.ARCHER, 100, inv,40);
        p.hurt(24);
        Manager.majFinDeTour(p);
        assertThat(p.getCurrentHealthPoints(),is(17 + 17/8-1));

        p = new Player("Florian", "Grognak le barbare", Jobs.ADVENTURER, 100, new ArrayList<>(),40);
        p.hurt(20);
        Manager.majFinDeTour(p);
        assertEquals(p.getCurrentHealthPoints(),20);
    }

    @Test
    @DisplayName("Main")
    void testMain() {
        Main m = new Main();
        assertNotNull(m);
    }

    @Test
    @DisplayName("Natural Tests")
    void testNatural()
    {
        Natural n = new Natural();
        assertNotNull(n);
        assertNotNull(Natural.valueOf(2));
        assertEquals(n.toInt(),0);

        n = Natural.valueOf(2);
        assertEquals(n.toInt(),2);
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(-2));
        assertTrue(Integer.valueOf(2).equals(n.toInteger()));
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf((Integer)null));

        n = Natural.valueOf(Integer.valueOf(2));
        assertEquals(n.toInt(),2);
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(Integer.valueOf(-2)));
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(null));

        n.add(Natural.valueOf(2));
        assertEquals(n,Natural.valueOf(4));
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(0).add(null));
        n.substract(Natural.valueOf(4));
        assertEquals(n,Natural.valueOf(0));
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(0).substract(Natural.valueOf(4)));

        assertEquals(n.toString(),"0");
        assertTrue(n.equals(Natural.valueOf(0)));
        assertTrue(n.equals(n));
        assertFalse(n.equals(Natural.valueOf(3)));
        assertFalse(n.equals(Integer.valueOf(4)));
        assertFalse(n.equals(null));

        n.add(Natural.valueOf(2));
        assertEquals(n.compareTo(Natural.valueOf(2)),0);
        assertEquals(n.compareTo(Natural.valueOf(3)),1);
        assertEquals(n.compareTo(Natural.valueOf(1)),-1);
        assertThrows(IllegalArgumentException.class, () -> Natural.valueOf(0).compareTo(null));


    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
