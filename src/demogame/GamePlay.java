package demogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.awt.Graphics2D;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.AttributeSet.ColorAttribute;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
    private boolean play = false;
    private int totalblock = 30;
    private Timer timer;
    private int delay = 8;

    private int score = 0;

    private int ballposx = 120;
    private int ballposy = 350;
    private int ballXdir = -1;
    private int ballYdir = -4;

    private int playerX = 310;

    private MapGenerator map;

    public GamePlay()
    {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        // black canvas
        g.setColor (Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing
        map.draw((Graphics2D)g);

        // border
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        // g.fillRect(791, 0, 3, 792);

        // scores

        g.setColor(Color.green);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("SCORE: "+ score, 560, 30);

        // PADDLE
        g.setColor(Color.red);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(Color.green);
        g.fillOval(ballposx, ballposy, 20, 20);

        if(totalblock <= 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON !!! ", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart!! ", 230, 350);
        }
        if(ballposy > 570)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over!!, Scores: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart!! ", 230, 350);
        }
        g.dispose();
    }

    
    private void moveLeft()
    {
        play = true;
        playerX -= 20;
    }

    private void moveRight()
    {
        play = true;
        playerX += 20;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX <= 10)
                playerX = 10;
            moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 600)
                playerX = 600;
            moveRight();
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
              play = true;
              ballposx = 120;
              ballposy = 350;
              ballXdir = -1;
              ballYdir = -4;
              playerX = 310;
              totalblock = 21;
              score = 0;
              map = new MapGenerator(3, 7);

              repaint();

            }
        }
        
        repaint();
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play)
        {
            if(ballposx <= 0)
            {
                ballXdir =- ballXdir;
            }
            if(ballposy <= 0)
            {
                ballYdir =- ballYdir;
            }
            if(ballposx >= 670)
            {
                ballXdir =- ballXdir;
            }

            Rectangle ballrect = new Rectangle(ballposx , ballposy, 20, 20);
            Rectangle paddlerect = new Rectangle(playerX, 550, 100, 8);

            if(ballrect.intersects(paddlerect))
            {
                ballYdir = -ballYdir;

            }
            ballposx += ballXdir;
            ballposy += ballYdir;

            A: for(int i = 0; i < map.map.length; i++)
            {
                for(int j = 0; j < map.map[0].length; j++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposx, ballposy, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect))
                        {
                            map.setBrickvalue(0, i, j);
                            totalblock--;
                            score += 5;

                            if(ballposx + 19 <= brickRect.x || ballposx + 1 >= brickRect.x + brickRect.width)
                                ballXdir = -ballXdir;
                            else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
        }

        
        repaint();    
        
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
