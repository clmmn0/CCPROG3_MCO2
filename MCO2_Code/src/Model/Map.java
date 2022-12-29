package Model;


import java.util.Random;

/**
 * Implements a Map object. A map consists of obstructions, healing totems, and encounters.
 */
public class Map{
    private int positionX = 0; // row position of the player on the map
    private int positionY = 0; // column position of the player on the map
    private char[][] grid = {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},	// initializes grid
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}};

    /**
     *  Creates a Map object
     */
    public Map(){
        Random rand = new Random();
        int n = rand.nextInt(5);	 // generates a number from 0 to 4

        switch (n){		// generates random map with obstacles
            case 0: {
                grid = new char[][] {{' ', ' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X', ' '},
                        {' ', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', ' '},
                        {' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'},
                        {'X', ' ', ' ', 'X', 'X', ' ', ' ', ' ', 'X', ' '},
                        {' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X', ' ', ' '},
                        {' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X', ' ', ' '},
                        {' ', 'X', 'X', 'X', 'X', ' ', ' ', ' ', 'X', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}};
                break;
            }

            case 1: {
                grid = new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X'},
                        {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' '},
                        {'X', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '},
                        {' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X', ' ', ' '},
                        {' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                        {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'},
                        {'X', 'X', 'X', 'X', 'X', ' ', ' ', 'X', 'X', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', ' ', ' '},
                        {' ', ' ', 'X', 'X', 'X', 'X', 'X', ' ', ' ', ' '},
                        {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}};
                break;
            }
            case 2: {
                grid = new char[][] {{' ', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X'},
                        {'X', ' ', 'X', ' ', ' ', ' ', 'X', ' ', 'X', 'X'},
                        {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', 'X', ' '},
                        {'X', 'X', ' ', ' ', ' ', ' ', 'X', 'X', ' ', ' '},
                        {' ', ' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' '},
                        {' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', ' ', ' '},
                        {'X', ' ', ' ', 'X', ' ', 'X', ' ', ' ', 'X', ' '},
                        {'X', ' ', 'X', ' ', ' ', 'X', ' ', ' ', ' ', ' '}};
                break;
            }

            case 3: {
                grid = new char[][] {{' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' '},
                        {'X', 'X', ' ', ' ', ' ', ' ', 'X', ' ', 'X', ' '},
                        {' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X'},
                        {' ', 'X', 'X', 'X', 'X', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X', 'X'},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {'X', ' ', 'X', 'X', ' ', 'X', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' '},
                        {'X', 'X', ' ', 'X', ' ', ' ', ' ', 'X', ' ', ' '}};
            }

            case 4: {
                grid = new char[][] {{' ', ' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '},
                        {' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', 'X', ' '},
                        {' ', ' ', ' ', ' ', 'X', 'X', ' ', 'X', ' ', ' '},
                        {'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                        {' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X', 'X', ' '},
                        {'X', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', 'X', 'X', ' ', ' ', 'X', ' ', 'X', ' '},
                        {' ', 'X', ' ', ' ', ' ', 'X', 'X', ' ', ' ', 'X'},
                        {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', ' ', ' '},
                        {'X', ' ', 'X', 'X', ' ', ' ', ' ', 'X', ' ', ' '}};
            }
        }

        setHealing(); // sets healing totems on the map
        setEncounters(); // sets encounters on the map
    }

    /**
     *  getter is for getting the generated map
     * @return grid - refers to the map
     */
    public char[][] getMap(){
        return grid;
    }

    /**
     * sets the position of the player
     * @param x - new x position
     * @param y - new y position
     */
    public void setPosition(int x, int y){
        positionX = x;
        positionY = y;
    }

    /**
     * returns x position of the player
     * @return positionX - x position of player
     */
    public int getPositionX(){
        return positionX;
    }

    /**
     * returns y position of the player
     * @return positionY - y position of player
     */
    public int getPositionY(){
        return positionY;
    }

    /**
     * getter returns value located in specified indices
     * @param x - row
     * @param y - column
     * @return grid[x][y] - value in specified indices
     */
    public char getValueOfGrid(int x, int y)
    {
        return grid[x][y];
    }

    /**
     * sets value of grid element
     * @param x - row position
     * @param y - column position
     * @param c - value to change with
     */
    public void setValueOfGrid(int x, int y, char c)
    {
        grid[x][y] = c;
    }

    /**
     *  sets healing totems randomly on the map
     */
    public void setHealing(){
        Random rand = new Random();
        int i, r, c;
        for(i = 0; i <= 4; i++){ // 5 healing totems are needed for the map
            do{
                r = rand.nextInt(10);
                c = rand.nextInt(10);
            }while((grid[r][c] != ' ') || (r == 9 && c == 9) || (r == 0 && c == 0)); // conditioon checks if the array is available. If not, it will continue to generate another random array until it finds an available array to put the healing totem.

            grid[r][c] = 'H';
        }
    }

    /**
     *  sets encounters randomly on the map
     */
    public void setEncounters(){
        Random rand = new Random();
        int i, r, c;
        for(i = 0; i <= 2; i++){ // 3 encounters per companion (Kirin, Yuki, Yume, Same). A total of 12 encounters on the map.
            do{
                r = rand.nextInt(10);
                c = rand.nextInt(10);
            }while(grid[r][c] != ' ' || (r == 9 && c == 9) || (r == 0 && c == 0));
            grid[r][c] = 'K'; // sets Kirin's encounter

            do{
                r = rand.nextInt(10);
                c = rand.nextInt(10);
            }while(grid[r][c] != ' ' || (r == 9 && c == 9) || (r == 0 && c == 0));
            grid[r][c] = 'M'; // sets Yume's encounter

            do{
                r = rand.nextInt(10);
                c = rand.nextInt(10);
            }while(grid[r][c] != ' ' || (r == 9 && c == 9) || (r == 0 && c == 0));
            grid[r][c] = 'U'; // sets Yuki's encounter

            do{
                r = rand.nextInt(10);
                c = rand.nextInt(10);
            }while(grid[r][c] != ' ' || (r == 9 && c == 9) || (r == 0 && c == 0));
            grid[r][c] = 'A'; // sets Same's encounter
        }
    }

    /**
     * checks if the movement of the player on the map is valid
     * @param pastX - previous x position
     * @param pastY - previous y position
     * @param newX - new x position
     * @param newY - new y position
     * @return - true if movement is valid, false otherwise
     */
    public boolean isValidMove(int pastX, int pastY, int newX, int newY)
    {
        if(pastX == newX){
            if(pastY > newY && (pastY - newY == 1)){
                return true;
            }
            else if(newY > pastY && (newY - pastY == 1)){
                return true;
            }
        }

        else if(pastY == newY){
            if(pastX > newX && (pastX - newX == 1)){
                return true;
            }
            else if(newX > pastX && (newX - pastX == 1)){
                return true;
            }
        }

        return false;
    }

    /**
     * checks if position lands on an obstacle
     * @param x - row position
     * @param y - column position
     * @return returns true if position lands an obstacle, false otherwise
     */
    public boolean isObstacle(int x, int y)
    {
        if(grid[x][y] == 'X' || grid[x][y] == 'x')
            return true;

        return false;
    }

    /**
     * checks if the position lands on a healing totem
     * @param x - row position
     * @param y - column position
     * @return - returns true if position lands on a healing totem, false otherwise
     */
    public boolean isHealingTotem(int x, int y)
    {
        if(grid[x][y] == 'H')
            return true;

        return false;
    }

    /**
     * checks if the position lands on an encounter
     * @param x - row
     * @param y - column
     * @return - returns true if position lands on an encounter, false otherwise
     */
    public boolean isEncounter(int x, int y)
    {
        if(grid[x][y] == 'K')
            return true;

        if(grid[x][y] == 'U')
            return true;

        if(grid[x][y] == 'M')
            return true;

        if(grid[x][y] == 'A')
            return true;

        return false;
    }

}

