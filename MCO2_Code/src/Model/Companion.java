package Model;

import java.util.Random;
/**
 * Implements a Companion object. A companion can be one of the four types namely,
 * Kirin, Yume, Yuki, and Same.
 */
public class Companion{
    protected String name;					// name of companion
    protected int hitPoints = 50;			// initializes HP of companions
    protected boolean isStrength = false;	// checks if companion is stronger
    protected boolean isWeakness = false;	// checks if companion is weaker
    protected boolean skillActive = false;	// checks if the skill is currently in use
    protected boolean skillUsed = false; 	// checks if the skill has been used

    /** 
     * returns the name of the companion
     * @return name - name of companion
     */
    public String getName() {
        return name;
    }

    /** 
     * returns the HP of the companion
     * @return hitPoints - HP of the companions
     */
    public int getHP(){
        return hitPoints;
    }

    /** 
     * returns true if skill is currently active
     * @return skillActive - true if skill is currently in use, false otherwise
     */
    public boolean getSkillActive() { return skillActive; }

    /** 
     * returns true if skill has already been used
     * @return skillUsed - true if skill was already used, false otherwise
     */
    public boolean getSkillUsed(){ return skillUsed; }

    /** 
     * sets the hit points for the companion
     * @param healthPoints - hit points 
     */
    public void setHP(int healthPoints){
        this.hitPoints = healthPoints;
    }
    
    /** 
     * sets the state of the skill whether currently active or not
     * @param c - true if skill is currently active, false otherwise
     */
    public void setSkillActive(boolean c) { skillActive = c; }

    /** 
     * sets the availability of companion's skill
     * @param c - true if skill has already been used, false otherwise
     */
    public void setSkillUsed(boolean c){ skillUsed = c; }

    /** 
     * checks for the condition of the companion
     * @return - true if companion has 0 hit points, false otherwise
     */
    public boolean isDefeated(){
        if(hitPoints == 0)
            return true;
        else
            return false;
    }

    /** 
     * checks if companion is strong against enemy
     * @return isStrength - true if companion is strong against enemy, false otherwise
     */
    public boolean isStrength(Companion NPC){
        return isStrength;
    }

    /** 
     * checks if companion is weak against enemy
     * @return isWeakness - true if companion is weak against enemy, false otherwise
     */
    public boolean isWeakness(Companion NPC){
        return isWeakness;
    }

    /** 
     * simulates an attack to another companion with a random amount of damage ranging from 0 to 10
     * @param a - attacker's companion
     * @param e - enemy's companion
     * @return d - remaining health
     */
    public int attack(Companion a, Companion e){
        Random rand = new Random();
        int d = rand.nextInt(11);	// generates a number from 0 to 10

        if(a.isStrength(e)){ // checks if companion is strong against enemy. If yes, the damage will be increased by 20%.
            d *= 1.2;
        }

        if(a.isWeakness(e)){ // checks if companion is weak against enemy. If yes, the damage will be reduced by 20%.
            d *= 0.8;
        }

        e.setHP(e.getHP() - d);

        return d;
    }

}
