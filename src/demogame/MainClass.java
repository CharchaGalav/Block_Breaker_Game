package  demogame;

import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setTitle("BLOCK BREAKER");
        f.setBounds(10,10,700,600);
        f.setLocationRelativeTo(null);          //used to center the gui on the screen.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Closes your jframe by clicking the windows close.
        f.setVisible(true);
        f.setResizable(false);

        GamePlay gamePlay=new GamePlay();
        f.add(gamePlay); 
    }
}
