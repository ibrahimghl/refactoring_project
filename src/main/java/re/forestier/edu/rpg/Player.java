package re.forestier.edu.rpg;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import re.forestier.edu.lib.Natural;

public class Player {
    private static final Natural defaultMaxHp = Natural.valueOf(20);
    private static final Natural[] xpForlevel = {
        Natural.valueOf(0),
        Natural.valueOf(10),
        Natural.valueOf(27),
        Natural.valueOf(57),
        Natural.valueOf(111)
    }; //Level = i+1 
       //We consider that level is a player thing, not a job one.

    private String playerName;
    private String avatarName;
    public Jobs avatarClass;

    private Natural money;

    private Natural level;
    private Natural maxHealthPoint;
    private Natural currentHealthPoints;
    private Natural xp;
    private Natural maxWeight;
    private Natural weight;

    private final HashMap<Ability, Integer> abilities; //Ability = stat
                                                       //SHOULDDO : have another hashmap to have contextual modifier (items,...)
    private ArrayList<Item> inventory;

    public Player(String playerName, String avatar_name, Jobs avatarClass, int money, ArrayList<Item> inventory, int maxWeight) {
        this(playerName,avatar_name,avatarClass,money,inventory,Player.defaultMaxHp.toInt(),maxWeight);
    }

    public Player(String playerName, String avatar_name, Jobs avatarClass, int money, ArrayList<Item> inventory, int maxHp, int maxWeight)
    {
        assert playerName != null : "Player name can't be null";
        assert avatar_name != null : "Avatar name can't be null";
        assert avatarClass != null : "Avatar class can't be null";
        assert inventory != null : "Inventory can't be null";

        this.playerName = playerName;
        this.avatarName = avatar_name;
        this.avatarClass = avatarClass;
        this.money = Natural.valueOf(money);
        this.inventory = inventory;
        this.level = Natural.valueOf(1);
        this.xp = Natural.valueOf(0);
        this.abilities = new HashMap<Ability, Integer>();
        avatarClass.getAbilityPerLevel().forEach((ability,value)->
            {this.abilities.put(ability,value[0]);}
        );
        this.maxWeight = Natural.valueOf(maxWeight);
        this.weight = Natural.valueOf(0);
        for(Item i : inventory)
        {
            this.weight.add(i.getWeight());
        }
        if(this.weight.compareTo(this.maxWeight) == -1)
        {
            throw  new UnsupportedOperationException("The player have too much to carry in theyr inventory");
        }
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

    public HashMap<Ability,Integer> getAbilities()
    {
        HashMap<Ability,Integer> copy = new HashMap<Ability,Integer>(); //In doubt, deep copy
        this.abilities.forEach((key,value) -> {
            copy.put(key,value);}
        );
        return copy;
    }

    public ArrayList<Item> getInventory()
    {
        return new ArrayList<Item>(this.inventory);
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

    public void addItem(Item i)
    {
        assert i != null : "Argument must be non null";
        Natural w = (Natural)this.weight.clone();
        w.add(i.getWeight());
        if(w.compareTo(this.maxWeight) == -1)
        {
            throw new UnsupportedOperationException("Player can't carry more.");

        }

        this.weight.add(i.getWeight());
        this.inventory.add(i);
    }

    public void removeItem(Item i)
    {
        assert i != null : "Argument must be non null";
        if(!this.inventory.contains(i))
        {
            throw new UnsupportedOperationException("Can't remove what player don't have");
        }
        this.weight.substract(i.getWeight());
        this.inventory.remove(i);
    }

    public void buy(Item i)
    {
        assert i != null : "Argument must be non null";
        if(this.money.compareTo(i.getValue()) == 1)
        {
            throw new UnsupportedOperationException("Player doesn't have enough money.");
        }

        Natural w = (Natural)this.weight.clone();
        w.add(i.getWeight());
        if(w.compareTo(this.maxWeight) == -1)
        {
            throw new UnsupportedOperationException("Player can't carry more.");

        }

        this.removeMoney(i.getValue().toInt());
        this.weight.add(i.getWeight());
        this.inventory.add(i);
    }

    public void sell(Item i)
    {
        assert i != null : "Argument must be non null";
        if(!this.inventory.contains(i))
        {
            throw new UnsupportedOperationException("Player can't sell what they don't own.");
        }

        this.inventory.remove(i);
        this.weight.substract(i.getWeight());
        this.addMoney(i.getValue().toInt());
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

    public Jobs getAvatarClass () {
        return this.avatarClass;
    }

    public void addXp(int xp) {
        Natural ancientLevel = (Natural)this.level.clone();
        this.xp.add(Natural.valueOf(xp));
        int i = 0;
        while(i < xpForlevel.length && this.xp.compareTo(xpForlevel[i]) <= 0)
        {
            i++;
        }

        this.level = Natural.valueOf(i);

        if (!ancientLevel.equals(this.level)) {
            // Player leveled-up!
            // Give a random object
            Random random = new Random();
            this.inventory.add(Manager.objectList[random.nextInt(Manager.objectList.length)]);

            // Add/upgrade abilities to player
            avatarClass.getAbilityPerLevel().forEach((ability,value)->
                {this.abilities.put(ability,value[this.level.toInt()-1]);}
            );
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

        ArrayList<Ability> abilitiesKey = new ArrayList<Ability>(this.abilities.keySet());
        Collections.sort(abilitiesKey);
        for(Ability a : abilitiesKey)
        {
            if(this.abilities.get(a) != 0)
            {
                sb.append("\n   " + a.toString() + " : " + this.abilities.get(a));
            } 
        } 

        sb.append("\n\nInventaire :");
        this.inventory.forEach(item -> {
            sb.append("\n   " + item);
        });
        return sb.toString();
    }

    public String toMarkDown()
    {
        StringBuilder sb = new StringBuilder("#Joueur ");
        sb.append(this.avatarName);
        sb.append("#\n**Joué par ");
        sb.append(this.playerName);
        sb.append("**\n**Niveau : ");
        sb.append(this.level.toInt());
        sb.append("** (XP totale : ");
        sb.append(this.xp);
        sb.append(")\n\n##Capacités :##");

        ArrayList<Ability> abilitiesKey = new ArrayList<Ability>(this.abilities.keySet());
        Collections.sort(abilitiesKey);
        for(Ability a : abilitiesKey)
        {
            if(this.abilities.get(a) != 0)
            {
                sb.append("\n*" + a.toString() + " : " + this.abilities.get(a));
            } 
        } 

        sb.append("\n\n##Inventaire :##");
        this.inventory.forEach(item -> {
            sb.append("\n*" + item);
        });
        return sb.toString();
    }
}