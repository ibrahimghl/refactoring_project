package re.forestier.edu.rpg;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import re.forestier.edu.lib.Natural;

public class Player {
    private static final Natural defaultMaxHp = Natural.valueOf(20);
    private static final Integer[] xpForlevel = {0,10,27,57,111}; //Level = i+1 
                                                                  //TODO : add level

    private String playerName;
    private String avatarName;
    private String avatarClass;

    private Natural money;

    private Natural level;
    private Natural maxHealthPoint;
    private Natural currentHealthPoints;
    private Natural xp;

    public HashMap<String, Integer> abilities; //Ability = stat
    public ArrayList<String> inventory;

    public Player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory) {
        this(playerName,avatar_name,avatarClass,money,inventory,Player.defaultMaxHp.toInt());
    }

    private Player()
    {
        //Here to prevent the compilator to create default constructor
    }

    public Player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory, int maxHp)
    {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF")) 
        {
            return;
        }

        this.playerName = playerName;
        this.avatarName = avatar_name;
        this.avatarClass = avatarClass;
        this.money = Natural.valueOf(money);
        this.inventory = inventory;
        this.level = Natural.valueOf(1);
        this.xp = Natural.valueOf(0);
        this.abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(this.avatarClass).get(1);
        this.maxHealthPoint = Natural.valueOf(maxHp);
        this.currentHealthPoints = Natural.valueOf(maxHp);
    }

    public String getPlayerName()
    {
        return this.playerName;
    }
            
    public String getAvatarName()
    {
        return this.avatarName;
    }
            
    public Integer getMoney()
    {
        return this.money.toInt();
    }

    public int getLevel()
    {
        return this.level.toInt();
    }

    public int getMaxHealthPoints()
    {
        return this.maxHealthPoint.toInt();
    }

    public int getCurrentHealthPoints()
    {
        return this.currentHealthPoints.toInt();
    }

    public void heal(int hp)
    {
        if(hp < 0 )
        {
            throw new IllegalArgumentException();
        }
        this.currentHealthPoints.add(Natural.valueOf(hp));
        if(this.currentHealthPoints.compareTo(this.maxHealthPoint) == -1)
        {
            this.currentHealthPoints = (Natural)this.maxHealthPoint.clone();
        }
    }

    public void hurt(int damage)
    {
        if(damage < 0 )
        {
            throw new IllegalArgumentException();
        }
        this.currentHealthPoints.substract(Natural.valueOf(damage));
    }


    public int getXp()
    {
        return this.xp.toInt();
    }

    public String getAvatarClass () {
        return this.avatarClass;
    }

    public void addXp(int xp) {
        Natural ancientLevel = (Natural)this.level.clone();
        this.xp.add(Natural.valueOf(xp));
        int i = 0;
        while(i < xpForlevel.length && this.xp.toInt() >= xpForlevel[i])
        {
            i++;
        }

        this.level = Natural.valueOf(i);

        if (!ancientLevel.equals(this.level)) {
            // Player leveled-up!
            // Give a random object
            ;
            Random random = new Random();
            this.inventory.add(UpdatePlayer.objectList[random.nextInt(UpdatePlayer.objectList.length)]);

            // Add/upgrade abilities to player
            HashMap<String, Integer> abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(this.getAvatarClass()).get(this.level.toInt());
            abilities.forEach((ability, level) -> {
                this.abilities.put(ability, abilities.get(ability));
            });
        }
    }

    public void removeMoney(int amount)
    {
        Natural toRemove = Natural.valueOf(amount);
        if (this.money.compareTo(toRemove) == 1) 
        {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        this.money.substract(toRemove);
    }
    public void addMoney(int amount) 
    { 
        Natural toAdd = Natural.valueOf(amount);
        this.money.add(toAdd);
    }

    @Override 
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Joueur ");
        sb.append(this.avatarName);
        sb.append(" joué par ");
        sb.append(this.playerName);
        sb.append("\nNiveau : ");
        sb.append(this.level.toInt());
        sb.append(" (XP totale : ");
        sb.append(this.xp);
        sb.append(")\n\nCapacités :");
        this.abilities.forEach((name, level) -> {
            sb.append("\n   " + name + " : " + level);
        });
        sb.append("\n\nInventaire :");
        this.inventory.forEach(item -> {
            sb.append("\n   " + item);
        });
        return sb.toString();
    }
}