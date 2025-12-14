package re.forestier.edu;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.Manager;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.Jobs;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player firstPlayer = new Player("Florian", "Ruzberg de Rivehaute", Jobs.DWARF, 200, new ArrayList<>(),10);
        firstPlayer.addMoney(400);

        firstPlayer.addXp(15);
        Affichage.afficherJoueur(firstPlayer);
        System.out.println("------------------");
        firstPlayer.addXp(20);
        Affichage.afficherJoueur(firstPlayer);
    }
}