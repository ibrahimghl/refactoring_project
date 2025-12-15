package re.forestier.edu.rpg;

import java.io.File;  
import java.io.IOException;  
import java.io.FileWriter; 


public class Affichage {

    public static void afficherJoueur(Player player) 
    {
        assert player != null : "Player must be non null";
        System.out.println(player.toString());
    }

    public static void playerToMarkDown(Player p)
    {
        assert p != null : "Player must be non null";
        try 
        {
            File playerFile = new File("./"+p.getAvatarName()+".md");
            FileWriter myWriter = new FileWriter(playerFile);
            myWriter.write(p.toMarkDown());
            myWriter.close();
        } catch (IOException e) 
        {
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void playerToMarkDown(Player p, String path)
    {
        assert p != null : "Player must be non null";
        assert path != null : "Path must be non null";
        try 
        {
            File playerFile = new File(path+p.getAvatarName()+".md");
            FileWriter myWriter = new FileWriter(playerFile);
            myWriter.write(p.toMarkDown());
            myWriter.close();
        } catch (IOException e) 
        {
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
