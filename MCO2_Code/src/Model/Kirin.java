package Model;

/**
 * Implements a Kirin object. A Kirin has a special skill and inherits attributes and methods from a Companion object.
 */
public class Kirin extends Companion {

    /**
     * Creates a Kirin object
     */
    public Kirin(){
        name = "Kirin";
    }

    /**
     * Kirin's special skill,
     * burn an entire column of obstruction from where the player is standing.
     * @param m - map
     */
    public void Blaze(Map m){
        int y = m.getPositionY();

        for(int j = 0; j < 10; j++) {	//erase entire column intersecting player
            if (m.isObstacle(j, y))
                m.setValueOfGrid(j, y, ' ');
        }
        
        setSkillUsed(true); // sets the skillUsed of Kirin to true to disable repeated use
    }

    /**
     * method checks if Kirin is strong against enemy
     * @param NPC - enemy's companion
     * @return isStrength - true if Kirin is strong against enemy, false otherwise
     */
    public boolean isStrength(Companion NPC){
        this.isStrength = false;

        if(NPC.getName().equals("Yume"))
            this.isStrength = true;

        return isStrength;
    }

    /**
     * method checks if Kirin is weak against enemy
     * @param NPC - enemy's companion
     * @return isWeakness - true if Kirin is weak against enemy, false otherwise
     */
    public boolean isWeakness(Companion NPC){
        this.isWeakness = false;

        if(NPC.getName().equals("Same"))
            this.isWeakness = true;

        return isWeakness;
    }
}

