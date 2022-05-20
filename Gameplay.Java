package brickbracker;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.util.Random;


/**
 *
 * @author riyapatel
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 40;
    
    private Timer timer;
    private int delay = 15;
    
    private int playerX = 300;
    
    private int BallPositionX = 290;
    private int BallPositionY = 350;
    private int BallDirectionX = getRandomNumberForX();
    private int BallDirectionY = getRandomNumberForY();
    
    private MapGenerator map;
    
    public Gameplay(){
        map = new MapGenerator(4,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }
    
    public void paint(Graphics g){
        
        g.setColor(Color.white); //background
        g.fillRect(1, 1, 692, 592); //background
        
        
        map.draw((Graphics2D)g);  //drawing map
        
        g.setColor(Color.white); //borders
        g.fillRect(0, 0, 3, 592);//borders
        g.fillRect(0, 0, 692, 3);//borders
        g.fillRect(691, 0, 3, 592);//borders
        
        g.setColor(Color.red); //score
        g.setFont(new Font("serif" , Font.BOLD, 25)); //score
        g.drawString("" + score, 590, 30); //score   
        
        g.setColor(Color.black); //paddle
        g.fillRect(playerX, 550, 100, 8);  //paddle
        
        g.setColor(Color.black); //ball
        g.fillOval(BallPositionX, BallPositionY, 20, 20);  //ball
        
        if(totalBricks <= 0) 
        {
            play = false;
            BallDirectionX = 0;
            BallDirectionY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif" , Font.BOLD, 30)); //score
            g.drawString("You Won", 260, 300);  //score
            
            g.setFont(new Font("serif" , Font.BOLD, 20)); //restart
            g.drawString("Press enter to restart", 230, 350);  //restart

        }
        
        if(BallPositionY > 570) {
            play = false;
            BallDirectionX = 0;
            BallDirectionY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif" , Font.BOLD, 30)); //score / gameover
            g.drawString("Game over " , 190, 300);  
            g.setColor(Color.red); //score
            
            g.setFont(new Font("serif" , Font.BOLD, 20)); //restart
            g.drawString("Press enter to restart", 230, 350);  

        }
        
        g.dispose();
       
    }


    
    
    
    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 600)
            {
                playerX = 600;
            }else
            {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX < 10)
            {
                playerX = 10;
            }else
            {
                moveLeft();
            }
            
        }
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play =true;
                BallPositionX = 120;
                BallPositionY = 350;
                BallDirectionX = -1;
                BallDirectionY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 40;
                map = new MapGenerator(4,10);         
                repaint();
            }
        }
    }
    
    public void moveRight(){
        play = true;
        playerX+=20;
    }
    
     public void moveLeft(){
        play = true;
        playerX-=20;
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
        timer.start();
        if(play)
        {
            if(new Rectangle (BallPositionX, BallPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8 )))
            {
                BallDirectionY = -BallDirectionY;
            }
            
            A: for(int i = 0; i <map.map.length; i++)
            {
                for(int j = 0; j <map.map[0].length; j++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j  * map.brickWidth + 80;
                        int brickY = i  * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                       Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                       Rectangle ballRect = new Rectangle(BallPositionX, BallPositionY, 20, 20);
                       Rectangle brickRect = rect;
                       
                       if(ballRect.intersects(brickRect))
                       {
                           map.setBrickValue(0,i,j);
                           totalBricks --;
                           score += 1;
                           
                           if(BallPositionX + 19 <= brickRect.x || BallPositionX + 1 >= brickRect.x + brickRect.width)
                           {
                               BallDirectionX = -BallDirectionX;
                           }
                           else
                           {
                               BallDirectionY = -BallDirectionY;
                           }
                           break A;
                       }
                    }
                }
            }
            
            BallPositionX += BallDirectionX;
            BallPositionY += BallDirectionY;
            
            if(BallPositionX < 0 )
            {
                BallDirectionX = -BallDirectionX;
            }
            if(BallPositionY < 0 )
            {
                BallDirectionY = -BallDirectionY;
            }
            if(BallPositionX > 670 )
            {
                BallDirectionX = -BallDirectionX;
            }
        }
        repaint();
        
    }
    public int getRandomNumberForY() {
            Random random = new Random();
            int max = -1;
            int min = -5;
            int randomNumber = min + random.nextInt(max - min + 1);
            return randomNumber;
        }
     
 
        public int getRandomNumberForX() {
            Random random = new Random();
            int max = -1;
            int min = -3;
            int randomNumber = min + random.nextInt(max - min + 1);
            return randomNumber;
        }
    }
