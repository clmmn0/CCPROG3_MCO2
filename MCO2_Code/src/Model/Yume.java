package Model;

/**
 * Implements a Yume object. A Yume has a special skill and inherits attributes and methods from a Companion object.
 */
public class Yume extends Companion {

    /**
     * creates a Yume object
      */
    public Yume(){
        name = "Yume";
    }
    
    /**
     * Yume's special skill,
     * move one unobstructed square diagonally
     * @param x - new x position
     * @param y - new y position
     * @param m - map
     */
    public void floatSkill(int x, int y, Map m){
        m.setPosition(x, y);
        setSkillUsed(true); // updates skillUsed to true to disable repeated use
    }

    /**
     * method checks if the move upon the usage of Yume's skill is valid
     * @param x - new x position
     * @param y - new y position
     * @param map - map
     * @return - true if movement is valid, false otherwise
     */
    public boolean isFloatValid(int x, int y, Map map){

        if(x > map.getPositionX()){
            if((x - map.getPositionX() == 1) && (y - map.getPositionY() == 1))
                return true;
            if((x - map.getPositionX() == 1) && (y - map.getPositionY() == -1))
                return true;
        }

        else if(x < map.getPositionX()){
            if((x - map.getPositionX() == -1) && (y - map.getPositionY() == 1))
                return true;
            if((x - map.getPositionX() == -1) && (y - map.getPositionY() == -1))
                return true;
        }

        return false;
    }
    
    /**
     * checks if Yume is strong against enemy
     * @param NPC - enemy's companion
     * @return isStrength - true if Yume is strong against enemy, false otherwise
     */
    public boolean isStrength(Companion NPC){
        this.isStrength = false;

        if(NPC.getName().equals("Yuki"))
            this.isStrength = true;

        return isStrength;
    }

    /**
     * checks if Yume is weak against enemy
     * @param NPC - enemy's companion
     * @return isWeakness - true if Yume is weak against enemy, false otherwise
     */
    public boolean isWeakness(Companion NPC){
        this.isWeakness = false;

        if(NPC.getName().equals("Kirin"))
            this.isWeakness = true;

        return isWeakness;
    }

}