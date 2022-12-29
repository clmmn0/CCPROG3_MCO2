package Model;

/**
 * Implements a Same object. A Same has a special skill and inherits attributes and methods from a Companion object.
 */
public class Same extends Companion {

    /**
     * creates a Same object
     */
    public Same(){
        name = "Same";
    }

    /**
     * Same's special skill,
     * destroys an entire row of obstruction from where the player is standing.
     * @param m - map
     */
    public void Splash(Map m){
        int x = m.getPositionX();

        for(int i = 0; i < 10; i++) {	// erase entire row intersecting player
            if (m.isObstacle(x, i))
                m.setValueOfGrid(x, i, ' ');
        }

        setSkillUsed(true); // updates skillUsed to true to disable repeated use
    }
    
    /**
     * checks if Same is strong against enemy
     * @param NPC - enemy's companion
     * @return isStrength - true if Same is strong against enemy, false otherwise
     */
    public boolean isStrength(Companion NPC){
        this.isStrength = false;

        if(NPC.getName().equals("Kirin"))
            this.isStrength = true;

        return isStrength;
    }

    /**
     * checks if Same is weak against enemy
     * @param NPC - enemy's companion
     * @return isWeakness - true if Same is weak against enemy, false otherwise
     */
    public boolean isWeakness(Companion NPC){
        this.isWeakness = false;

        if(NPC.getName().equals("Yuki"))
            this.isWeakness = true;

        return isWeakness;
    }

}
