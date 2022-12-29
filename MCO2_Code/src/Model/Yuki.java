package Model;

/**
 * Implements a Yuki object. A Yuki has a special skill and inherits attributes and methods from a Companion object.
 */
public class Yuki extends Companion {

    private int skillTotalUsage = 0; 	// counts the number of times a player has moved upon the usage of Yuki's skill

    /**
     * creates a Yuki object
     */
    public Yuki(){
        name = "Yuki";
    }

    /**
     * Yuki's special skill,
     * move two (2) unobstructed steps for a single cost
     * @param x - new x position
     * @param y - new y position
     * @param m - map
     */
    public void giantSteps(int x, int y, Map m){
        m.setPosition(x, y);
        skillTotalUsage++;
        setSkillUsed(true); // updates skillUsed to true to disable repeated use
    }
    
    /**
     * method returns the number of times a player has moved upon the usage of Yuki's skill
     * @return skillTotalUsage - number of moves used in Yuki's skill
     */
    public int getSkillTotalUsage(){ return skillTotalUsage; }

    /**
     * checks if Yuki is strong against enemy
     * @param NPC - enemy's companion
     * @return isStrength - true if Yuki is strong against enemy, false otherwise
     */
    public boolean isStrength(Companion NPC){
        this.isStrength = false;

        if(NPC.getName().equals("Same"))
            this.isStrength = true;

        return isStrength;
    }

    /**
     * checks if Yuki is weak against enemy
     * @param NPC - enemy's companion
     * @return isWeakness - true if Yuki is weak against enemy, false otherwise
     */
    public boolean isWeakness(Companion NPC){
        this.isWeakness = false;

        if(NPC.getName().equals("Yume"))
            this.isWeakness = true;

        return isWeakness;
    }



}
