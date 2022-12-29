package Model;


import java.util.*;

/**
 * Implements a Player object. A player has a name and gender.
 * A player also has three companions and is able to move from his/her current position.
 */
public class MyPlayer{

    private String name;					// player's name
    private String gender;					// player's gender
    private ArrayList<Companion> companion;	// list of player's companions
    private boolean winState = false;		// dictates if player has won
    private boolean playerLost = false;		// dictates if player has lost
    private int diceValue;					// player's number of moves

    Random rand = new Random();

    /**
     * creates a MyPlayer object
     */
    public MyPlayer() {
        companion = new ArrayList<Companion>();
    }

    /**
     * creates a MyPlayer object with the specified name and gender
     * @param name - refers to the name of the player
     * @param gender - refers to the gender of the player
     */
    public MyPlayer(String name, String gender) {
        this.name = name;
        this.gender = gender;
        companion = new ArrayList<Companion>();
    }

    /**
     * returns player's name
     * @return name - player's name
     */
    public String getName() {
        return name;
    }

    /**
     * returns player's gender
     * @return gender - player's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * returns the companions who are with the player
     * @return companion - player's companions
     */
    public ArrayList<Companion> getCompanion() {
        return companion;
    }

    /**
     * returns player's current number of moves
     * @return diceValue - current number of moves
     */
    public int getDiceValue() {
        return diceValue;
    }

    /**
     * sets player's number of moves
     * @param n - number of moves
     */
    public void setDiceValue(int n) {
        this.diceValue = n;
    }

    /**
     * sets player's name
     * @param n - name
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * sets player's gender
     * @param g - gender
     */
    public void setGender(String g) {
        this.gender = g;
    }

    /**
     * sets the companions who will join the player
     * @param companion - companions of the player
     */
    public void setCompanion(ArrayList<Companion> companion) {
        this.companion = companion;
    }

    /**
     * returns whether the player has won the game or not
     * @return winState - true if the player has won, false otherwise
     */
    public boolean isWinner(int x, int y){
        if(x == 9 && y == 9)
            winState = true;

        return winState;
    }

    /**
     * returns whether the player has lost or not
     * @return playerLost - true if player has lost, otherwise false
     */
    public boolean isLost(){
        if(getCompanion().get(0).getHP() == 0 && getCompanion().get(1).getHP() == 0 && getCompanion().get(2).getHP() == 0)
            playerLost = true;

        return playerLost;
    }
    
    /**
     * generates a random number ranging from 1 to 6
     * @return diceValue - number of moves
     */
    public int rollDice() {
        diceValue = rand.nextInt(6) + 1;
        return diceValue;
    }
}