package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class Manager {

    public final static String[] objectList = {"Magic bow : Heal by 1/8th of your HP","Lookout Ring : Prevents surprise attacks","Scroll of Stupidity : INT-2 when applied to an enemy", "Draupnir : Increases XP gained by 100%", "Magic Charm : Magic +10 for 5 rounds", "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?", "Combat Edge : Well, that's an edge", "Holy Elixir : Recover your HP"
    };

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
                    if(player.inventory.contains("Holy Elixir")) {
                        player.heal(1);
                    }
                    break;
                case ARCHER :
                    player.heal(1);
                    if(player.inventory.contains("Magic Bow")) {
                        int potentialHeal = player.getCurrentHealthPoints()/8-1;
                        player.heal(potentialHeal < 0 ? 0 : potentialHeal);
                    }
                    break;
            }
        } 
    }


}