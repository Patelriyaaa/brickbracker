package brickbracker;

import javax.swing.JFrame;

/**
 *
 * @author riyapatel
 */
public class Main {
    
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame();
        Gameplay gamePlay = new Gameplay();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Breakout Ball");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePlay);
               
    }
    
}
