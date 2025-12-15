package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;
import re.forestier.edu.lib.Natural;

public class Manager {

    public final static Item[] objectList = {
        new Item("Magic bow", "Heal by 1/8th of your HP",Natural.valueOf(0),Natural.valueOf(0)),
        new Item("Lookout Ring","Prevents surprise attacks",Natural.valueOf(0),Natural.valueOf(0)),
        new Item("Scroll of Stupidity","INT-2 when applied to an enemy",Natural.valueOf(0),Natural.valueOf(0)), 
        new Item("Draupnir", "Increases XP gained by 100%",Natural.valueOf(0),Natural.valueOf(0)), 
        new Item("Magic Charm","Magic +10 for 5 rounds",Natural.valueOf(0),Natural.valueOf(0)), 
        new Item("Rune Staff of Curse","May burn your ennemies... Or yourself. Who knows?",Natural.valueOf(0),Natural.valueOf(0)),
        new Item("Combat Edge","Well, that's an edge",Natural.valueOf(0),Natural.valueOf(0)), 
        new Item("Holy Elixir","Recover your HP",Natural.valueOf(0),Natural.valueOf(0))
    }; //SHOULDDO : Instantiate from file (xml)
       //Since we don't have value for weight and value, we put neutral

    // majFinDeTour met à jour les points de vie
    public static void majFinDeTour(Player player) {
        if(player.getCurrentHealthPoints() == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }

        if(player.getCurrentHealthPoints() < player.getMaxHealthPoints()/2) {
            switch(player.getAvatarClass())
            {
                case ADVENTURER :
                    player.heal(2);
                    if(player.getLevel() < 3) {
                        player.hurt(1);
                    }
                    break;
                case DWARF :
                    player.heal(1);
                    if(player.getInventory().contains(new Item("Holy Elixir"))) {
                        player.heal(1);
                    }
                    break;
                case ARCHER :
                    player.heal(1);
                    if(player.getInventory().contains(new Item("Magic Bow"))) {
                        int potentialHeal = player.getCurrentHealthPoints()/8-1;
                        player.heal(potentialHeal < 0 ? 0 : potentialHeal);
                    }
                    break;
            }
        } 
    }


}