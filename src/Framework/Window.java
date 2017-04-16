package Framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * Window implements the ActionListener and KeyListener
 * initializes the frame for the game.
 */

public abstract class Window implements ActionListener,KeyListener
{
	public static int falling = 0;
	public static JFrame frame;
	public int HEIGHT;
	public int WIDTH;
	public ImageIcon backGroundIcon;
	public JLabel backgroundLabel;
	
	
	protected void runGUI(int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		
     		frame = new JFrame();
			frame.setSize(WIDTH, HEIGHT);
			frame.setBackground(Color.CYAN);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			backGroundIcon = new ImageIcon();
			backgroundLabel = new  JLabel(backGroundIcon);
			frame.setContentPane(backgroundLabel);
		    
			frame.addKeyListener(this);
			frame.setVisible(true);
			
	}
}
