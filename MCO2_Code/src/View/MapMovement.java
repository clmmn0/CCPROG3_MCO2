package View;


import Model.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

/**
 * GUI for the map exploration, including encounters
 */
public class MapMovement extends JFrame implements ActionListener
{
    private Container contents;								// map
    private JButton[][] squares = new JButton[10][10];		// buttons for map
    private Color colorGreen = Color.GREEN;					// colors
    private Color colorWhite = Color.WHITE;
    private Color colorRed = Color.RED;
    private Color colorYellow = Color.YELLOW;

    private int row = 0;		// x position of player
    private int col = 0; 		// y position of player
    private int n;				// dice value

    Random rand = new Random();

    private JPanel whole = new JPanel();						// entire window
    private JPanel buttons = new JPanel(); 						// for skills, save, and exit
    private JTextArea stats = new JTextArea();					// player stats
    private JTextArea instructions = new JTextArea(8, 50);		// instructions

    private JButton btnDice, btn1, btn2, btn3, btn4, btn5, btn6;	// buttons for game window

    // companions indices
    // -1 = companion does not exist
    private int kirin = -1;
    private int yuki = -1;
    private int yume = -1;
    private int same = -1;

    // Objects
    private MyPlayer player;
    private Map map;

    /*
     * declarations below are for the encounter display
     */
    private Companion NPCCompanion; // stores the enemy's companion during the encounter
    private Companion PlCompanion; // stores the player's companion who who participated in the encounter
    private int pInt; // stores the arraylist of the chosen companion of the player who will fight

    private JFrame mainFrame;

    private JPanel panelTop;
    private JPanel panelCenter;
    private JPanel panelBottom;
    private JPanel panelTopChoose;
    private JPanel panelBottomChoose;
    private JPanel panelTopResult;

    private JLabel PName = new JLabel();
    private JLabel NName = new JLabel();
    private JLabel PImage = new JLabel();
    private JLabel NImage = new JLabel();
    private JLabel midVS = new JLabel();
    private JLabel attackText = new JLabel();
    private JLabel chooseTopText = new JLabel();
    private JLabel chooseBottomText = new JLabel();
    private JLabel resultText = new JLabel();
    private JLabel resultHPText = new JLabel();

    private JButton attackBtn;
    private JButton choice1;
    private JButton choice2;
    private JButton choice3;
    private JButton resultBtn;

    /**
     *  initializes game windows for map exploration
     * @param p - player
     * @param m - map
     */
    public MapMovement(MyPlayer p, Map m)
    {
        super("LEGEND: The Game");
        this.player = p;
        this.map = m;
        this.n = p.getDiceValue();

        contents = getContentPane();
        contents.setLayout(new GridLayout(10, 10)); 	// sets size of grid for window

        buttons.setLayout(new GridLayout(6,1));			// sets size of grid for buttons

        initScreen(); // displays map screen

        //size and display
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(whole);
    }

    /**
     *  initializes game window elements for the map screen
     */
    public void initScreen()
    {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                squares[i][j] = new JButton();
                contents.add(squares[i][j]);
                squares[i][j].setBackground(colorWhite);
                squares[i][j].addActionListener(this);

                // marks obstacles previously encountered
                if(map.getMap()[i][j] == 'x') {
                    squares[i][j].setText("X");
                    squares[i][j].setBackground(colorRed);
                }
            }
        }

        squares[map.getPositionX()][map.getPositionY()].setBackground(colorGreen);	// sets color to player's current position

        // stats and instructions
        whole.setLayout(new BorderLayout());
        stats.setText("Name: " + player.getName() + "\nGender: " + player.getGender());
        stats.append("\nCompanions:\n");
        for (int k = 0; k < 3; k++) {
        	stats.append(player.getCompanion().get(k).getName() + " " + player.getCompanion().get(k).getHP() + "\t| ");
        	switch(player.getCompanion().get(k).getName()) {
        	case "Kirin":
        		stats.append("burn an entire column of obstruction from where the player is standing\n"); break;
        	case "Yuki":
        		stats.append("move two (2) unobstructed steps for a single cost\n"); break;
        	case "Yume":
        		stats.append("move one unobstructed square diagonally\n"); break;
        	case "Same":
        		stats.append("destroy an entire row of obstruction from where you are standing\n"); break;
        	}
        }
        	
        stats.setEditable(false);
        instructions.setText("Instructions:\r\n" + 
        		"Select any tile from the grid above to indicate your move. You can only move horiontally and vertically.\r\n" + 
        		"\r\n" + 
        		"Press any buttons with your companion's name found at the right to activate your companion's skill. \r\n" + 
        		"\r\n" + 
        		"Good luck!");
        instructions.setEditable(false);

        // Roll dice button
        btnDice = new JButton("Roll Dice");
        btnDice.addActionListener(this);
        buttons.add(btnDice);
        btnDice.setBackground(colorYellow);

        // LOADING FROM SAVE
        if (player.getDiceValue() > 0) {
            btnDice.setEnabled(false);
            instructions.append("\n\nYou have " + n + " moves left!");
        }

        // gets x and y position of player
        row = map.getPositionX();
        col = map.getPositionY();

        // adds buttons for companion skills
        for(int i = 0; i < 3; i++) {
            if(player.getCompanion().get(i).getName().equals("Kirin")) {
                btn1 = new JButton("Kirin - Blaze");
                btn1.addActionListener(this);
                buttons.add(btn1);
                btn1.setEnabled(false);
                kirin = i;	// saves index of Kirin

                // enables button if skill is active and dice value is not 0
                if (player.getCompanion().get(kirin).getSkillUsed() == false && n > 0)
                    btn1.setEnabled(true);
            }

            else if(player.getCompanion().get(i).getName().equals("Yuki")) {
                btn2 = new JButton("Yuki - Giant Steps");
                btn2.addActionListener(this);
                buttons.add(btn2);
                btn2.setEnabled(false);
                yuki = i;	// saves index of Yuki

                // enables button if skill is active and dice value is not 0
                if (player.getCompanion().get(yuki).getSkillUsed() == false && n > 0)
                    btn2.setEnabled(true);
            }

            else if(player.getCompanion().get(i).getName().equals("Yume")) {
                btn3 = new JButton("Yume - Float");
                btn3.addActionListener(this);
                buttons.add(btn3);
                btn3.setEnabled(false);
                yume = i;	// saves index of Yume

                // enables button if skill is active and dice value is not 0
                if (player.getCompanion().get(yume).getSkillUsed() == false && n > 0)
                    btn3.setEnabled(true);
            }

            else if(player.getCompanion().get(i).getName().equals("Same")) {
                btn4 = new JButton("Same - Splash");
                btn4.addActionListener(this);
                buttons.add(btn4);
                btn4.setEnabled(false);
                same = i;	// saves index of Same

                // enables button if skill is active and dice value is not 0
                if (player.getCompanion().get(same).getSkillUsed() == false && n > 0)
                    btn4.setEnabled(true);
            }
        }

        btn5 = new JButton("Save");		// save button
        btn5.addActionListener(this);
        buttons.add(btn5);
        btn6 = new JButton("Exit");		// exit button
        btn6.addActionListener(this);
        buttons.add(btn6);


        // positions panels in the window
        whole.add(contents, BorderLayout.CENTER);
        whole.add(stats, BorderLayout.NORTH);
        whole.add(instructions, BorderLayout.SOUTH);
        whole.add(buttons, BorderLayout.EAST);
    }

    /**
     * processes the action made by the player. If valid, it will update the position of the player. Else, it will disregard the action.
     * @param i - row position
     * @param j - column position
     */
    private void processClick(int i, int j)
    {
        // checks if the player has run out of moves
        if(n <= 0) {
            instructions.setText("No more moves! You must roll dice!");
            btnDice.setEnabled(true);
            return;
        }

        // checks if move is valid
        else if(!map.isValidMove(row, col, i, j)) {
            instructions.setText("Invalid move! Choose another position.");
            instructions.append("\n\nYou have " + n + " moves left!");
            return;
        }

        // checks if the new position of the player is an obstacle. If yes, then it asks for another input
        else if(map.isObstacle(i, j)){
            squares[i][j].setText("X");
            map.getMap()[i][j] = 'x';
            squares[i][j].setBackground(colorRed);
            instructions.setText("Oops, can't go there...");
            instructions.append("\n\nYou have " + n + " moves left!");
            return;
        }

        // checks if the new position of the player is a healing totem. If yes, all companions regain full health
        else if(map.isHealingTotem(i, j)) {
            squares[i][j].setText("H");
            instructions.setText("You obtained a HEALING TOTEM! Your COMPANIONS are magically healed!");
            player.getCompanion().get(0).setHP(50); // sets the HP of all the companions back to full health
            player.getCompanion().get(1).setHP(50);
            player.getCompanion().get(2).setHP(50);
            stats.setText("Name: " + player.getName() + "\nGender: " + player.getGender());
            stats.append("\nCompanions:\n");
            for (int k = 0; k < 3; k++) {
            	stats.append(player.getCompanion().get(k).getName() + " " + player.getCompanion().get(k).getHP() + "\t| ");
            	switch(player.getCompanion().get(k).getName()) {
            	case "Kirin":
            		stats.append("burn an entire column of obstruction from where the player is standing\n"); break;
            	case "Yuki":
            		stats.append("move two (2) unobstructed steps for a single cost\n"); break;
            	case "Yume":
            		stats.append("move one unobstructed square diagonally\n"); break;
            	case "Same":
            		stats.append("destroy an entire row of obstruction from where you are standing\n"); break;
            	}
            }
            
            map.setValueOfGrid(i, j, ' ');
            n--;
            instructions.append("\n\nYou have " + n + " moves left!");
        }

        // if valid move
        else {
            instructions.setText("You moved one space!");
            n--; // updates the dice
            instructions.append("\n\nYou have " + n + " moves left!");
        }

        // statements below updates the position of the player and updates the display
        squares[i][j].setBackground(colorGreen); // updates the current position of the player
        squares[row][col].setBackground(colorWhite); // deletes the past position of the player
        squares[row][col].setText("");
        row = i;
        col = j;
        map.setPosition(row, col); // updates the position of the player on the map

        // checks if the new position results to an encounter. If yes, then the encounter screen begins
        if(map.isEncounter(i, j)){
            squares[i][j].setText("" + map.getValueOfGrid(i, j));
            instructions.setText("NPC wants to fight!");
            encounterGUI(); // starts the encounter
            map.setValueOfGrid(i, j, ' ');
            this.setVisible(false); // hides the map screen
        }

        // if dice value is 0, disables skill buttons when moves are 0
        if(n <= 0) {
            btnDice.setEnabled(true);
            if (kirin > -1)
                btn1.setEnabled(false);
            if (yuki > -1)
                btn2.setEnabled(false);
            if(yume > -1)
                btn3.setEnabled(false);
            if(same > -1)
                btn4.setEnabled(false);
        }
    }

    /**
     * executes if the skill of either Yuki or Yume is activated
     * @param x - row position
     * @param y - column position
     * @param name - name of companion
     */
    public void processSkill(int x, int y, String name){

        // checks if there are still moves left for the player
        if(n <= 0) {
            instructions.setText("No more moves! You must roll dice!");
            btnDice.setEnabled(true);
            return;
        }

        // executes if the new position upon using Yuki's skill is invalid
        else if(name.equals("Yuki") && !map.isValidMove(row, col, x, y)){
            instructions.setText("INVALID! Choose another position to use Yuki's skill.");
            return;
        }

        // executes if the new position upon using Yume's skill is invalid
        else if(name.equals("Yume") && !((Yume)player.getCompanion().get(yume)).isFloatValid(x, y, map)){
            instructions.setText("INVALID! Choose another position to use Yume's skill." );
            return;
        }

        // executes if the new position upon usage of Yume's or Yuki's skill falls on an obstruction
        else if(map.isObstacle(x, y)){
            squares[x][y].setText("X");
            squares[x][y].setBackground(colorRed);
            instructions.setText("INVALID! Choose another position to use " + name + "'s skill.");
            return;
        }

        // executes if the new position upon using Yuki's skill is valid
        else if(name.equals("Yuki") && map.isValidMove(row, col, x, y)) {
            ((Yuki) player.getCompanion().get(yuki)).giantSteps(x, y, map);
            if(((Yuki) player.getCompanion().get(yuki)).getSkillTotalUsage() == 2) { // checks if player has already moved two unobstructed steps
                instructions.setText("Yuki used his skill! You moved two unobstructed steps.");
                n--; // updates dice
                ((Yuki) player.getCompanion().get(yuki)).setSkillActive(false);		// sets skill availability to false
            }
            else
                instructions.setText("You are currently using Yuki's skill. You can move one more unobstructed step.");

            instructions.append("\n\nYou have " + n + " moves left!");

        }

        // executes if the new position upon using Yume's skill is valid
        else if(name.equals("Yume") && ((Yume)player.getCompanion().get(yume)).isFloatValid(x, y, map)){
            ((Yume)player.getCompanion().get(yume)).floatSkill(x, y, map);
            instructions.setText("Yume used his skill! You moved one unobstructed step.");
            n--; // updates dice
            instructions.append("\n\nYou have " + n + " moves left!");
            ((Yume)player.getCompanion().get(yume)).setSkillActive(false);		// sets skill availability to false
        }

        squares[x][y].setBackground(colorGreen); // updates the current position of the player
        squares[row][col].setBackground(colorWhite); // deletes the past position of the player
        squares[row][col].setText("");
        row = x;
        col = y;
        map.setPosition(row, col); // updates the position of the player on the map

    }



    /**
     * method is for displaying the whole encounter screen
      */
    public void encounterGUI(){
        setNPCCompanion(map.getValueOfGrid(map.getPositionX(), map.getPositionY())); // creates the enemy's companion who will participate in the battle
        mainFrame = new JFrame("Encounter");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // makes sure that the player cannot exit the battle
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(650, 400);
        chooseScreen(); // encounter screen starts with the player choosing a companion
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
    }

    /**
     * setter sets the NPC's companion who will fight in the encounter
     * @param symbol - refers to the symbol that was activated on the map; K = Kirin, U = Yuki, M = Yume, A = Same
      */
    public void setNPCCompanion(char symbol)
    {
        switch(symbol){ // sets NPCCompanion
            case 'K': NPCCompanion = new Kirin(); NImage.setIcon(new ImageIcon(getClass().getResource("Kirin.png")));break;
            case 'U': NPCCompanion = new Yuki(); NImage.setIcon(new ImageIcon(getClass().getResource("Yuki.png")));break;
            case 'M': NPCCompanion = new Yume(); NImage.setIcon(new ImageIcon(getClass().getResource("Yume.png")));break;
            case 'A': NPCCompanion = new Same(); NImage.setIcon(new ImageIcon(getClass().getResource("Same.png")));break;
        }
    }

    /**
     * this setter sets the player's companion who will fight in the encounter
     * @param name - refers to the name of the chosen companion
     */
    public void setPlCompanion(String name)
    {
        switch(name){ // sets PlCompanion
            case "Kirin" : PlCompanion = new Kirin(); PImage.setIcon(new ImageIcon(getClass().getResource("Kirin.png")));break;
            case "Yuki" : PlCompanion = new Yuki(); PImage.setIcon(new ImageIcon(getClass().getResource("Yuki.png")));break;
            case "Yume" : PlCompanion = new Yume(); PImage.setIcon(new ImageIcon(getClass().getResource("Yume.png")));break;
            case "Same" : PlCompanion = new Same(); PImage.setIcon(new ImageIcon(getClass().getResource("Same.png")));break;
        }
    }


    /**
     * method displays the choosing screen wherein the player chooses his companion who will fight
     */
    public void chooseScreen()
    {
        panelTopChoose = new JPanel(new BorderLayout());
        panelBottomChoose = new JPanel(new BorderLayout());
        chooseTopText.setText("You will fight the enemy's " + NPCCompanion.getName() + ". Choose which of your companions will participate in the encounter.");
        panelTopChoose.add(chooseTopText, BorderLayout.WEST);

        choice1 = new JButton(player.getCompanion().get(0).getName()); // choices for the companions
        choice1.addActionListener(this);
        choice2 = new JButton(player.getCompanion().get(1).getName());
        choice2.addActionListener(this);
        choice3 = new JButton(player.getCompanion().get(2).getName());
        choice3.addActionListener(this);
        panelBottomChoose.add(choice1, BorderLayout.WEST);
        panelBottomChoose.add(choice2, BorderLayout.CENTER);
        panelBottomChoose.add(choice3, BorderLayout.EAST);

        chooseBottomText.setText("      HP " + player.getCompanion().get(0).getHP() + "            HP " + player.getCompanion().get(1).getHP() + "         HP " + player.getCompanion().get(2).getHP());
        panelBottomChoose.add(chooseBottomText, BorderLayout.SOUTH);

        mainFrame.getContentPane().add(panelTopChoose, BorderLayout.NORTH);
        mainFrame.getContentPane().add(panelBottomChoose, BorderLayout.SOUTH);
    }

    /**
     * displays the battle sequence
     */
    public void battleScreen()
    {
        panelTop = new JPanel(new BorderLayout());
        panelCenter = new JPanel(new BorderLayout());
        panelBottom = new JPanel(new BorderLayout());

        attackText.setText("Please click the button below to start the battle.");
        panelTop.add(attackText, BorderLayout.CENTER); // attackText shows who attacked and total damage points inflicted

        panelCenter.add(PImage, BorderLayout.WEST); // sets player's companion's image

        midVS.setText("VS"); // text for display only
        panelCenter.add(midVS, BorderLayout.CENTER);

        panelCenter.add(NImage, BorderLayout.EAST); // sets NPC's companion's image

        attackBtn = new JButton("Click to attack"); // button for attack
        attackBtn.addActionListener(this);
        attackBtn.setBackground(Color.GREEN);
        panelBottom.add(attackBtn, BorderLayout.CENTER);

        PName.setText(" Your " + PlCompanion.getName() + " - HP " + PlCompanion.getHP() + " "); // Player's companion name and HP
        panelBottom.add(PName, BorderLayout.WEST);

        NName.setText("   Enemy's " + NPCCompanion.getName() + " - HP " + NPCCompanion.getHP()); // Enemy's companion name and HP
        panelBottom.add(NName, BorderLayout.EAST);

        mainFrame.getContentPane().add(panelTop, BorderLayout.NORTH);
        mainFrame.getContentPane().add(panelCenter, BorderLayout.CENTER);
        mainFrame.getContentPane().add(panelBottom, BorderLayout.SOUTH);

    }

    /**
     * displays the result of the battle
     */
    public void resultScreen(){
        panelTopResult = new JPanel(new BorderLayout());

        if(NPCCompanion.getHP() == 0) // displayed text is different depending on the result
            resultText.setText("You won! Your " + PlCompanion.getName() + " survived with " + PlCompanion.getHP() + " HP. ");
        else if(PlCompanion.getHP() == 0)
            resultText.setText("Your " + PlCompanion.getName() + " died :( ");

        panelTopResult.add(resultText, BorderLayout.WEST);
        resultHPText.setText("Click OK to go back to the map.");
        panelTopResult.add(resultHPText, BorderLayout.SOUTH);

        resultBtn = new JButton ("OK");
        resultBtn.addActionListener(this);
        panelTopResult.add(resultBtn, BorderLayout.EAST);

        mainFrame.getContentPane().add(panelTopResult, BorderLayout.CENTER);
    }

    /**
     * checks if the encounter has ended
     * @return - true is encounter has ended, false otherwise
     */
    public boolean isEncounterEnd()
    {
        // if NPC lost
        if(NPCCompanion.getHP() <= 0){
            NPCCompanion.setHP(0);
            return true;
        }

        // if player's companion lost
        else if(PlCompanion.getHP() <= 0){
            PlCompanion.setHP(0);
            return true;
        }

        else
            return false;
    }

    /**
     * checks if the chosen companion to fight in the battle is valid or is still alive
     * @param name - name of companion
     * @return returns true if chosen companion is valid, false otherwise
     */
    public boolean isChoiceValid(String name)
    {
        for(int i = 0; i < 3; i++){
            if(player.getCompanion().get(i).getName().equals(name)){
                pInt = i;
                if(player.getCompanion().get(i).getHP() == 0)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    /**
     * performs actions based on button clicks
     * @param e - action made or button clicked
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        int r, c;

        // map screen: if roll dice button is clicked
        if(e.getActionCommand().equals("Roll Dice")) {
            n = player.rollDice();
            instructions.setText("You rolled " + n + "!");
            btnDice.setEnabled(false);

            // enables buttons as moves replenish
            if ((kirin > -1) && (player.getCompanion().get(kirin).getSkillUsed() == false))
                btn1.setEnabled(true);
            if ((yuki > -1) && (player.getCompanion().get(yuki).getSkillUsed() == false))
                btn2.setEnabled(true);
            if((yume > -1) && (player.getCompanion().get(yume).getSkillUsed() == false))
                btn3.setEnabled(true);
            if((same > -1) && (player.getCompanion().get(same).getSkillUsed() == false))
                btn4.setEnabled(true);
        }

        // map screen: if Kirin skill button is clicked
        else if (e.getActionCommand().equals("Kirin - Blaze")) {
            if(((Kirin)player.getCompanion().get(kirin)).getHP() == 0)	// if companion is dead
                instructions.setText("Kirin is dead! You can't activate Kirin's skill.");
            else {
                for(r = 0; r < 10; r++) { // clears the display of obstructions
                    if(map.isObstacle(r, map.getPositionY())) {
                        squares[r][map.getPositionY()].setText("");
                        squares[r][map.getPositionY()].setBackground(colorWhite);
                    }
                }
                instructions.setText("You activated Kirin's skill! The column of your current position has been cleared of obstructions.");
                ((Kirin) player.getCompanion().get(kirin)).Blaze(map); // activates Kirin's skill
                btn1.setEnabled(false);                
            }
        }

        // map screen: if Yuki skill button is clicked
        else if (e.getActionCommand().equals("Yuki - Giant Steps")) {
            if(((Yuki)player.getCompanion().get(yuki)).getHP() == 0)	// if companion is dead
                instructions.setText("Yuki is dead! You can't activate Yuki's skill.");
            else {
                ((Yuki) player.getCompanion().get(yuki)).setSkillActive(true); // makes sure that the next action will be about using of Yuki's skill
                instructions.setText("You activated Yuki's skill! Choose where you want to move two unobstructed steps.");
                btn2.setEnabled(false); // disables the button for Yume's skill
            }
        }

        // map screen: if Yume skill button is clicked
        else if (e.getActionCommand().equals("Yume - Float")) {
            if(((Yume)player.getCompanion().get(yume)).getHP() == 0)	// if companion is dead
                instructions.setText("Yume is dead! You can't activate Yume's skill.");
            else {
                ((Yume) player.getCompanion().get(yume)).setSkillActive(true); // makes sure that the next action will be about Yume's skill
                instructions.setText("You activated Yume's skill! Choose where you want to move one unobstructed square diagonally.");
                btn3.setEnabled(false);
            }
        }

        // map screen: if Same skill button is clicked
        else if (e.getActionCommand().equals("Same - Splash")) {
            if(((Same)player.getCompanion().get(same)).getHP() == 0)	// if companion is dead
                instructions.setText("Same is dead! You can't activate Same's skill.");
            else {
                for(c = 0; c < 10; c++) { // clears the display of obstructions
                    if(map.isObstacle(map.getPositionX(), c)) {
                        squares[map.getPositionX()][c].setText("");
                        squares[map.getPositionX()][c].setBackground(colorWhite);
                    }
                }
                ((Same) player.getCompanion().get(same)).Splash(map);	// activates Same's skill
                instructions.setText("You activated Same's skill! The row of your current position has been cleared of obstructions.");
                btn4.setEnabled(false);
            }
        }

        // map screen: if Save button is clicked
        // writes player and map attributes per line to text file
        else if (e.getActionCommand().equals("Save")) {
            instructions.setText("Your current game has been saved to a text file.");
            BufferedWriter bw;
            try {
                bw = new BufferedWriter (new FileWriter ("Save.txt"));
                bw.write (player.getName() + "\n");
                bw.write(player.getGender() + "\n");

                for(int i = 0; i < 3; i++) {
                    bw.write(player.getCompanion().get(i).getName() + "\n");
                    bw.write(player.getCompanion().get(i).getHP() + "\n");
                    bw.write(player.getCompanion().get(i).getSkillUsed() + "\n");
                }

                bw.write(map.getPositionX() + "\n" + map.getPositionY() + "\n");
                bw.write(n + "\n");

                for (int i = 0; i < 10; i++) {
                    for(int j = 0; j < 10; j++) {
                        bw.write(map.getMap()[i][j] + "\n");
                    }
                }

                bw.close ();

            } catch (IOException e1) {
                instructions.setText("Error has occurred while saving the file.");
            }
        }

        // map screen: if exit button in map screen is clicked
        else if (e.getActionCommand().equals("Exit")) {
            this.dispose();	// closes window
            Result result = new Result(player, map);	// opens result screen
        }

        // map screen: if a button on the map is clicked
        else{
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (source == squares[i][j]) {
                        if(player.getCompanion().get(0).getSkillActive() && !(player.getCompanion().get(0).getName().equals("Kirin") || player.getCompanion().get(0).getName().equals("Same"))) { // executes if Yuki's skill is being used
                            processSkill(i, j, player.getCompanion().get(0).getName());
                        }

                        else if(player.getCompanion().get(1).getSkillActive() && !(player.getCompanion().get(1).getName().equals("Kirin") || player.getCompanion().get(1).getName().equals("Same"))) {// executes if Yume's skill is being used
                            processSkill(i, j, player.getCompanion().get(1).getName());
                        }

                        else if(player.getCompanion().get(2).getSkillActive() && !(player.getCompanion().get(2).getName().equals("Kirin") || player.getCompanion().get(2).getName().equals("Same"))) {// executes if Yume's skill is being used
                            processSkill(i, j, player.getCompanion().get(2).getName());
                        }

                        else {
                            processClick(i, j);
                        }
                    }
                }
            }
        }

        // below are the actions for the encounter

        boolean isEnd = false; // tells whether an encounter has ended or not

        // encounter screen: chosen companion to fight in the battle is Kirin
        if (e.getActionCommand().equals("Kirin")) {
            if(isChoiceValid("Kirin")) {
                setPlCompanion("Kirin"); // sets the player's companion who will fight
                PlCompanion.setHP(player.getCompanion().get(pInt).getHP()); // sets the correct HP

                panelTopChoose.setVisible(false); 		// hides the choosing screen
                panelBottomChoose.setVisible(false);
                battleScreen();							// displays battle screen
                panelTop.setVisible(true);
                panelCenter.setVisible(true);
                panelBottom.setVisible(true);
            }
            else
                chooseTopText.setText("INVALID! Your Kirin is dead. Choose another companion to fight the enemy's " +  NPCCompanion.getName() + ".");
        }

        // encounter screen: chosen companion to fight in the battle is Yume
        else if (e.getActionCommand().equals("Yume")) {
            if(isChoiceValid("Yume")) {
                setPlCompanion("Yume");
                PlCompanion.setHP(player.getCompanion().get(pInt).getHP()); // sets the correct HP

                panelTopChoose.setVisible(false);
                panelBottomChoose.setVisible(false);
                battleScreen();
                panelTop.setVisible(true);
                panelCenter.setVisible(true);
                panelBottom.setVisible(true);
            }
            else
                chooseTopText.setText("INVALID! Your Yume is dead. Choose another companion to fight the enemy's " +  NPCCompanion.getName() + ".");
        }

        // encounter screen: chosen companion to fight in the battle is Same
        else if (e.getActionCommand().equals("Same")) {
            if(isChoiceValid("Same")) {
                setPlCompanion("Same");
                PlCompanion.setHP(player.getCompanion().get(pInt).getHP()); // sets the correct HP

                panelTopChoose.setVisible(false);
                panelBottomChoose.setVisible(false);
                battleScreen();
                panelTop.setVisible(true);
                panelCenter.setVisible(true);
                panelBottom.setVisible(true);
            }
            else
                chooseTopText.setText("INVALID! Your Same is dead. Choose another companion to fight the enemy's " +  NPCCompanion.getName() + ".");
        }

        // encounter screen: chosen companion to fight in the battle is Yuki
        else if (e.getActionCommand().equals("Yuki")) {
            if(isChoiceValid("Yuki")) {
                setPlCompanion("Yuki");
                PlCompanion.setHP(player.getCompanion().get(pInt).getHP()); // sets the correct HP

                panelTopChoose.setVisible(false);
                panelBottomChoose.setVisible(false);
                battleScreen();
                panelTop.setVisible(true);
                panelCenter.setVisible(true);
                panelBottom.setVisible(true);
            }
            else
                chooseTopText.setText("INVALID! Your Yuki is dead. Choose another companion to fight the enemy's " +  NPCCompanion.getName() + ".");
        }

        // encounter screen: if attack button is clicked
        if (e.getActionCommand().equals("Click to attack")) {
            attackBtn.setBackground(Color.RED);
            attackBtn.setText("Click for enemy to attack");
            attackText.setText("Your " + PlCompanion.getName() + " attacked! Enemy's " + NPCCompanion.getName() + " was damaged by " + PlCompanion.attack(PlCompanion, NPCCompanion) + " points."); // PlCompanion attacks
            isEnd = isEncounterEnd();
            PName.setText(" Your " + PlCompanion.getName() + " - HP " + PlCompanion.getHP() + " "); // Player's companion name and HP
            NName.setText("   Enemy's " + NPCCompanion.getName() + " - HP " + NPCCompanion.getHP()); // Enemy's companion name and HP
        }

        // encounter screen: if button for companion to attack is clicked
        else if (e.getActionCommand().equals("Click for enemy to attack")) {
            attackBtn.setBackground(Color.GREEN);
            attackBtn.setText("Click to attack");
            attackText.setText("Ouch! Your " + PlCompanion.getName() + " was damaged by " + NPCCompanion.attack(NPCCompanion, PlCompanion) + " points.");
            isEnd = isEncounterEnd();
            PName.setText(" Your " + PlCompanion.getName() + " - HP " + PlCompanion.getHP() + " "); // Player's companion name and HP
            NName.setText("   Enemy's " + NPCCompanion.getName() + " - HP " + NPCCompanion.getHP()); // Enemy's companion name and HP
        }

        // encounter screen: checks if the encounter has ended
        if(isEnd){
            panelTop.setVisible(false);
            panelCenter.setVisible(false);
            panelBottom.setVisible(false);
            resultScreen();		// shows result screen

            panelTopResult.setVisible(true);
        }

        // encounter screen: disposes the encounter screen, goes back to the map
        if(e.getActionCommand().equals("OK")) {
            mainFrame.dispose(); // disposes encounter screen
            this.setVisible(true); // shows the map screen again
            squares[map.getPositionX()][map.getPositionY()].setText("");
            player.getCompanion().get(pInt).setHP(PlCompanion.getHP()); // updates the HP of the player's companion who fought in the battle
            stats.setText("Name: " + player.getName() + "\nGender: " + player.getGender() + "\nCompanions:\n" );
            for (int k = 0; k < 3; k++) {
            	stats.append(player.getCompanion().get(k).getName() + " " + player.getCompanion().get(k).getHP() + "\t| ");
            	switch(player.getCompanion().get(k).getName()) {
            	case "Kirin":
            		stats.append("burn an entire column of obstruction from where the player is standing\n"); break;
            	case "Yuki":
            		stats.append("move two (2) unobstructed steps for a single cost\n"); break;
            	case "Yume":
            		stats.append("move one unobstructed square diagonally\n"); break;
            	case "Same":
            		stats.append("destroy an entire row of obstruction from where you are standing\n"); break;
            	}
            }
            instructions.setText("Done with the encounter. Choose your next move");
        }

        // checks if the player has won or lost the game
        if(player.isLost() || player.isWinner(map.getPositionX(), map.getPositionY())){
            this.dispose();
            Result result = new Result(player, map);	// opens result window
        }
    }
}
