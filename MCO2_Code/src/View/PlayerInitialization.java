package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlayerInitialization implements ActionListener
{
	// Frames
    private JFrame startMenu;	
    private JFrame init;
    private JFrame details;
    
    // Panels
    private JPanel panelName;	
    private JPanel panelGender;
    private JPanel panelComp;
    private JPanel panelPlayer;
    private JTextField tfName;

    // Text Field
    private JTextArea pData;	

    // Labels
    private JLabel lblMsg;
    private JLabel lblName;		
    private JLabel lblGender;
    private JLabel lblComp;

    // Buttons
    private JButton btn;		
    private JButton btnOk;
    private JButton btnM, btnF;
    private JButton btnK, btnMe, btnU, btnA;
    private JButton btnStart;

    // Objects
    private MyPlayer player = new MyPlayer();
    private Map map = new Map();

    
    /**
     * initializes start menu
     */
    public PlayerInitialization()
    {
        startMenu = new JFrame("LEGEND: The Game");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startMenu.setLayout(new GridLayout(4,1));	// changes to Grid Layout
        startMenu.setSize(300, 300);				// sets size
        startMenu.setResizable(false);				// makes window non-resizable
        startMenu.setLocationRelativeTo(null);		// centers window
        lblMsg = new JLabel("Welcome to LEGEND: The Game!");
        startMenu.add(lblMsg);

        btn = new JButton("Start Game");	// start game button
        btn.addActionListener(this);
        startMenu.add(btn);

        btn = new JButton("Load");			// load button
        btn.addActionListener(this);
        startMenu.add(btn);

        btn = new JButton("Exit");			// exit button
        btn.addActionListener(this);
        startMenu.add(btn);

        startMenu.setVisible(true);
    }
    
    /**
     * initializes player creation
     */
    public void initScreen()
    {
        init = new JFrame("LEGEND: The Game");
        init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init.setLayout(new BorderLayout());	// changes to Border Layout
        init.setSize(300, 180);				// sets the width and height of the component
        init.setVisible(true);				// makes the window visible
        init.setResizable(false);			// makes window non-resizable
        init.setLocationRelativeTo(null);	// centers window

        panelName = new JPanel(new BorderLayout());
        panelGender = new JPanel(new BorderLayout());
        panelComp = new JPanel(new BorderLayout());

        // NAME
        lblName = new JLabel("Enter your name: ");
        panelName.add(lblName, BorderLayout.WEST);

        tfName = new JTextField();		// text field for name
        tfName.setColumns(7);			// sets the size for the text field
        tfName.setHorizontalAlignment(JTextField.LEFT);	// sets horizontal alignment for the text field
        panelName.add(tfName, BorderLayout.CENTER);

        btnOk = new JButton("OK");		// OK button for NAME
        btnOk.addActionListener(this);
        panelName.add(btnOk, BorderLayout.EAST);

        // GENDER
        lblGender = new JLabel("Choose a gender: ");
        panelGender.add(lblGender, BorderLayout.WEST);
        btnM = new JButton("Male");
        btnM.addActionListener(this);
        panelGender.add(btnM, BorderLayout.CENTER);
        btnF = new JButton("Female");
        btnF.addActionListener(this);
        panelGender.add(btnF, BorderLayout.EAST);

        // COMPANION
        lblComp = new JLabel("Choose which companion to remove:");
        panelComp.add(lblComp, BorderLayout.NORTH);
        btnK = new JButton("Kirin");
        btnMe = new JButton("Yume");
        btnU = new JButton("Yuki");
        btnA = new JButton("Same");
        btnK.addActionListener(this);
        panelComp.add(btnK, BorderLayout.WEST);
        btnMe.addActionListener(this);
        panelComp.add(btnMe, BorderLayout.CENTER);
        btnU.addActionListener(this);
        panelComp.add(btnU, BorderLayout.EAST);
        btnA.addActionListener(this);
        panelComp.add(btnA, BorderLayout.SOUTH);

        // positions panels inside the window
        init.getContentPane().add(panelName, BorderLayout.NORTH);
        init.getContentPane().add(panelGender, BorderLayout.CENTER);
        init.getContentPane().add(panelComp, BorderLayout.SOUTH);

        panelGender.setVisible(false);
        panelComp.setVisible(false);
    }

    /**
     * initializes window for displaying player details
     */
    public void playerDetails()
    {
        details = new JFrame("Player Details");
        details.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        details.setVisible(true);

        pData = new JTextArea();
        pData.setEditable(false);	// makes text area non-editable
        
        btnStart = new JButton("Start");	// start button
        btnStart.addActionListener(this);
        panelPlayer = new JPanel(new BorderLayout());
        
        details.getContentPane().add(panelPlayer, BorderLayout.CENTER);
        details.setLocationRelativeTo(null); 	// centers window
        details.setSize(300, 150);				// sets size
        
        pData.setText("Name: " + player.getName());
        pData.append("\nGender: " + player.getGender());
        pData.append("\nCompanions:\n" + player.getCompanion().get(0).getName() + "\n" + player.getCompanion().get(1).getName() + "\n" + player.getCompanion().get(2).getName());
        
        // positions panels inside the window
        panelPlayer.add(pData, BorderLayout.CENTER);
        panelPlayer.add(btnStart, BorderLayout.EAST);
        
        panelPlayer.setVisible(true);	// makes details frame visible
    }

    /**
     * performs actions based on button clicks
     * @param e - button clicked
     */
    public void actionPerformed(ActionEvent e)
    {
    	String input;
    	
    	// startMenu: moves to player creation
        if(e.getActionCommand().equals("Start Game")) {
            startMenu.dispose();
            initScreen();
        }

        // start Menu: loads previous save file
        else if(e.getActionCommand().equals("Load")) {
        	BufferedReader br;
    		try {
	    		br = new BufferedReader (new FileReader ("Save.txt"));
	    			
	    		// reads name and gender
				player.setName(br.readLine());
				player.setGender(br.readLine());
				
				// reads companions
				for(int i = 0; i < 3; i++) {
					input = br.readLine();
					if (input.equals("Kirin"))
						player.getCompanion().add(i, new Kirin());
					else if (input.equals("Yuki"))
						player.getCompanion().add(i, new Yuki());
					else if (input.equals("Yume"))
						player.getCompanion().add(i, new Yume()); 
					else if (input.equals("Same")) 
						player.getCompanion().add(i, new Same()); 

					player.getCompanion().get(i).setHP(Integer.parseInt(br.readLine()));
					player.getCompanion().get(i).setSkillUsed(Boolean.parseBoolean(br.readLine()));
				}
				
				// reads player position and dice value
				map.setPosition(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
				player.setDiceValue(Integer.parseInt(br.readLine()));
				
				// reads map 
				for (int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						map.getMap()[i][j] = br.readLine().charAt(0);
					}
				}
				startMenu.dispose();
				br.close ();
				playerDetails();	// displays details
			
    		} catch (IOException e1) {
		        lblMsg.setText("There is no previous saved file");
		    }
    	}

        // startMenu: exits window
        else if(e.getActionCommand().equals("Exit")) {
            System.exit(1);
        }
        
        // init: submits player name
        if (e.getActionCommand().equals("OK")) {
            if(tfName.getText().equals(""))		// will not continue if text field is blank
                return;

            else {
                player.setName(tfName.getText());
                panelName.setVisible(false);
                panelGender.setVisible(true);
            }
        }

        // init: chooses male gender
        if (e.getActionCommand().equals("Male")) {
            player.setGender("Male");
            panelGender.setVisible(false);
            panelComp.setVisible(true);
        }

        // init: chooses female gender
        else if (e.getActionCommand().equals("Female")) {
            player.setGender("Female");
            panelGender.setVisible(false);
            panelComp.setVisible(true);
        }

        // init: removes Kirin
        if(e.getActionCommand().equals("Kirin"))
        {
            player.getCompanion().add(new Yume());
            player.getCompanion().add(new Yuki());
            player.getCompanion().add(new Same());
            panelComp.setVisible(false);
            playerDetails();
            init.dispose();
        }

        // init: removes Yume
        else if(e.getActionCommand().equals("Yume"))
        {
            player.getCompanion().add(new Kirin());
            player.getCompanion().add(new Yuki());
            player.getCompanion().add(new Same());
            panelComp.setVisible(false);
            playerDetails();
            init.dispose();
        }
        
        // init: removes Yuki
        else if(e.getActionCommand().equals("Yuki"))
        {
            player.getCompanion().add(new Kirin());
            player.getCompanion().add(new Yume());
            player.getCompanion().add(new Same());
            panelComp.setVisible(false);
            playerDetails();
            init.dispose();
        }

        // init: removes Same
        else if(e.getActionCommand().equals("Same"))
        {
            player.getCompanion().add(new Kirin());
            player.getCompanion().add(new Yuki());
            player.getCompanion().add(new Yume());
            panelComp.setVisible(false);
            playerDetails();
            init.dispose();
        }

        // details: moves to actual game
        if(e.getActionCommand().equals("Start"))
        {
            details.dispose();
            MapMovement mapMovement = new MapMovement(player, map);
            mapMovement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}



