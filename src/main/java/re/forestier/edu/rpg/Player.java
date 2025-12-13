package re.forestier.edu.rpg;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import re.forestier.edu.lib.Natural;

public class Player {
    private static final Integer[] xpForlevel = {0,10,27,57,111}; //Level = i+1 
                                                                  //TODO : add level

    private String playerName;
    private String Avatar_name;
    private String AvatarClass;

    private Integer money;
    private Float __real_money__;

    private Natural level;
    private int healthpoints;
    private int currenthealthpoints;
    private int xp;

    public HashMap<String, Integer> abilities; //Ability = stat
    public ArrayList<String> inventory;

    public Player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF")) 
        {
            return;
        }

        this.playerName = playerName;
        Avatar_name = avatar_name;
        AvatarClass = avatarClass;
        this.money = Integer.valueOf(money);
        this.inventory = inventory;
        this.level = Natural.valueOf(1);
        this.xp = 0;
        this.abilities = UpdatePlayer.abilitiesPerTypeAndLevel().get(AvatarClass).get(1);
    }

    public String getPlayerName()
    {
        return this.playerName;
    }
            
    public String getAvatarName()
    {
        return this.Avatar_name;
    }
            
    public Integer getMoney()
    {
        return this.money;
    }

    public int getLevel()
    {
        return this.level.toInt();
    }

    public int getHealthPoints()
    {
        return this.healthpoints;
    }

    public void setHealthPoints(int hp)
    {
        this.healthpoints = hp;
    }

    public int getCurrentHealthPoints()
    {
        return this.currenthealthpoints;
    }

    public void setCurrentHealthPoints(int hp)
    {
        this.currenthealthpoints = hp;
    }

    public int getXp()
    {
        return this.xp;
    }

    public void setXp(int xp)
    {
        this.xp = xp;
    }

    public String getAvatarClass () {
        return AvatarClass;
    }

    public void addXp(int xp) {
        Natural ancientLevel = (Natural)this.level.clone();
        this.xp += xp;
        int i = 0;
        while(i < xpForlevel.length && this.xp >= xpForlevel[i])
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

    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }

        money = Integer.parseInt(money.toString()) - amount;
    }
    public void addMoney(int amount) { 
        money = money + amount;
    }
}