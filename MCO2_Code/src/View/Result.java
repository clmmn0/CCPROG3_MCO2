package View;


import Model.Map;
import Model.MyPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI for the game result screen
 */
public class Result extends JFrame implements ActionListener
{
    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel resultText = new JLabel();
    private JButton btnP = new JButton();
    private JButton btnE = new JButton();

    private MyPlayer player;
    private Map map;

    /**
     * Initializes game result screen
     * @param rPlayer - MyPlayer object
     * @param rMap - Map object
     */
    public Result(MyPlayer rPlayer, Map rMap)
    {
        super ("Result Screen");
        player = rPlayer;
        map = rMap;
        frame.setDefaultCloseOperation (EXIT_ON_CLOSE);
        frame.setLayout (new FlowLayout ());
        initScreen ();
        frame.setSize (400, 200); // sets the dimension of the window
        frame.setLocationRelativeTo(null); // centers window
        frame.setVisible (true);
    }

    /**
     *  responsible for drawing/putting components into the frame 
     */
    public void initScreen ()
    {
        if(player.isWinner(map.getPositionX(), map.getPositionY()))
            resultText.setText("Congrats! You've won the game!");
        else if(player.isLost())
            resultText.setText("You've lost! All your companions have died :(");
        else
            resultText.setText("Sad to see you go :( Hope to see you again!");

        btnP.setText("Play Again");
        btnP.addActionListener(this);
        btnE.setText("Exit Game");
        btnE.addActionListener(this);
        panel1.add(resultText, BorderLayout.CENTER);
        panel2.add(btnP, BorderLayout.WEST);
        panel2.add(btnE, BorderLayout.EAST);

        frame.getContentPane().add(panel1, BorderLayout.NORTH);
        frame.getContentPane().add(panel2, BorderLayout.SOUTH);
    }

    /**
     * performs actions based on button clicked
     * @param e - action performed or button clicked
     */
    public void actionPerformed (ActionEvent e)
    {
        // if play again button is clicked
        if (e.getActionCommand ().equals ("Play Again")) {
            frame.dispose();
            PlayerInitialization app = new PlayerInitialization();	// starts the game again
        }

        // if exit game button is clicked
        else if (e.getActionCommand ().equals ("Exit Game"))
            System.exit(0);
    }
}

