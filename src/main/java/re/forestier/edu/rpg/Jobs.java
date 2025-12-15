package re.forestier.edu.rpg;

import static java.util.Map.entry; 
import java.util.Map;  
import java.util.HashMap; 

public enum Jobs   
{
    ADVENTURER("ADVENTURER")
    { 
        public HashMap<Ability, Integer[]> getAbilityPerLevel(){ 
            return new HashMap(Map.ofEntries(
            entry(Ability.ALC, new Integer[]{0,0,1,1,1}),
            entry(Ability.ATK, new Integer[]{3,3,5,5,5}),
            entry(Ability.CHA, new Integer[]{2,3,3,3,3}),
            entry(Ability.DEF, new Integer[]{1,1,1,3,4}),
            entry(Ability.INT, new Integer[]{1,2,2,2,2}),
            entry(Ability.VIS, new Integer[]{0,0,0,0,1})
        ));
        } 
    },
    ARCHER("ARCHER")
    {
        public HashMap<Ability, Integer[]> getAbilityPerLevel(){
            return new HashMap(Map.ofEntries(
            entry(Ability.ALC, new Integer[]{0,0,0,0,0}),
            entry(Ability.ATK, new Integer[]{3,3,3,3,4}),
            entry(Ability.CHA, new Integer[]{1,2,2,2,2}),
            entry(Ability.DEF, new Integer[]{0,1,1,2,2}),
            entry(Ability.INT, new Integer[]{1,1,1,1,1}),
            entry(Ability.VIS, new Integer[]{3,3,3,3,3})
        ));
        } 
    },
    DWARF("DWARF")
    {
        public HashMap<Ability, Integer[]> getAbilityPerLevel(){
            return new HashMap(Map.ofEntries(
            entry(Ability.ALC, new Integer[]{4,4,5,5,5}),
            entry(Ability.ATK, new Integer[]{3,3,4,4,4}),
            entry(Ability.CHA, new Integer[]{0,0,0,0,1}),
            entry(Ability.DEF, new Integer[]{0,1,1,2,2}),
            entry(Ability.INT, new Integer[]{1,1,1,1,1}),
            entry(Ability.VIS, new Integer[]{0,0,0,0,0})
        ));
        }
    },
    GOBLIN("GOBLIN")
    { 
        public HashMap<Ability, Integer[]> getAbilityPerLevel(){ 
            return new HashMap(Map.ofEntries(
            entry(Ability.ALC, new Integer[]{1,4,4,4,4}),
            entry(Ability.ATK, new Integer[]{2,3,3,3,4}),
            entry(Ability.CHA, new Integer[]{0,0,0,0,0}),
            entry(Ability.DEF, new Integer[]{0,0,0,1,2}),
            entry(Ability.INT, new Integer[]{2,2,2,2,2}),
            entry(Ability.VIS, new Integer[]{0,0,1,1,1})
        ));
        } 
    };

    private final String name;

    private Jobs(String name) {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    public abstract HashMap<Ability, Integer[]> getAbilityPerLevel();
}