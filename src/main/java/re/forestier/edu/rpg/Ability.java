package re.forestier.edu.rpg;

public enum Ability{
    ALC("ALC"),
    ATK("ATK"),
    CHA("CHA"),
    DEF("DEF"),
    INT("INT"),
    VIS("VIS");

    private final String name;
    private Ability(String name) {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }     
};