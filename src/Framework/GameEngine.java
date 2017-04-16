package Framework;

import java.awt.Button;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Application.EnemyThread;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info The GameEngine contains some of the methods to simplify the development for the game developers
 * for example the method for creating buttons, sprites, players to the frame.
 * It also contains some logics in the game like collide and the movement of the sprites and players.
 * When the GameEngine is created it creates the necessary ArrayLists and the most important part of
 * the class, the Timer that game developer creates the game around. For example 100fps
 */
public abstract class GameEngine extends Window
{
	int delay;
	boolean gameOn;
	protected Timer time;
	protected ArrayList<SpriteObj>updateList = new ArrayList<SpriteObj>();
	protected ArrayList<Sprite>sprites;
	protected ArrayList<Player>players;
	protected ArrayList<Thread>tunes;
	
	/**
	 * Constructor
	 * 
	 * Initiates arraylist
	 */
	public GameEngine()
	{
		sprites = new ArrayList<Sprite>();
		players = new ArrayList<Player>();
		tunes = new ArrayList<Thread>();
	}
	
	/**
	 * Starts the Timer (FPS)
	 * Starts the game
	 * @param updateRate
	 */
	protected void startGame(int updateRate)
	{
		gameOn = true;
		delay = updateRate;
		time = new Timer(delay, this);
		time.start();
		frame.requestFocus();
	}
	
	
	/**
	 * Stops the game
	 * Stops the Timer (FPS)
	 * Clear screen
	 * Stop the sound
	 */
	protected void endGame()
	{
		gameOn = false;
		time.stop();
		sprites.clear();
		players.clear();

		for(int i = 0; i<tunes.size(); i++)
		{
			tunes.get(i).stop();
		}
		
		tunes.clear();
		
	}
	
	/**
	 * Returns what state the game is in on/off
	 * @return boolean
	 */
	protected boolean gameOn()
	{
		return gameOn;
	}

	/**
	 * Creates JButton and adds to frame with arguments above
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 * @param transparency
	 * @return JButton
	 */
	protected JButton addButton(int x, int y, int width, int height, String text,boolean transparency)
	{
		JButton button = new JButton(text);
		button.setBounds(x, y, width, height);
		button.addActionListener(this);
		
			if(transparency)
			{
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
			}
			
			frame.add(button);
			frame.repaint();
		
		
			return button;
	}
	
	/**
	 * Adds JPanel to frame
	 * @param addPanel
	 */
	protected void addToFrame(JPanel addPanel)
	{
		frame.add(addPanel);
	}
	
	/**
	 * Adds Background image to the frame
	 * @param picPath
	 */
	protected void addBackgroundImage(String picPath)
	{
		backgroundLabel.setIcon(new ImageIcon(picPath));
		frame.setContentPane(backgroundLabel);
	}
	
	/**
	 * Clears the frame
	 */
	protected void clearAll()
	{
		frame.getContentPane().removeAll();
		
		for(int i = 0; i<sprites.size(); i++)
		{
			sprites.get(i).getSprite().removeAll();
		}
		
		for(int i = 0; i<players.size(); i++)
		{
			players.get(i).getSprite().removeAll();
		}
		
	}
	
	/**
	 * Adds Sprite object to the frame using the arguments
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param picPath
	 * @param visible
	 * @return
	 */
	protected Sprite addSprite(int x, int y, int width, int height, String picPath, boolean visible)
	{
		Sprite sprite = new Sprite(x, y, width, height, picPath,visible);
		addToFrame(sprite.getSprite());
		setVisible(true);
		sprites.add(sprite);
		return sprite;
	}
	
	/**
	 * Adds Player objec to the frame with the given arguments
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param picPath
	 * @return
	 */
	protected Player addPlayer(int x, int y, int width, int height, String picPath)
	{
		Player player = new Player(x, y, width, height, picPath);
		addToFrame(player.getSprite());
		frame.setVisible(true);
		players.add(player);
		return player;
	}
	
	/**
	 * Sets visibility of frame
	 * @param visible
	 */
	protected void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
	
	/**
	 * Checks if there are any collides with the given SpriteObj
	 * @param obj
	 * @param x
	 * @param y
	 * @return boolean
	 */
	protected boolean collide(SpriteObj obj, int x, int y)
	{
		boolean collision = false;
		Rectangle rect = obj.getBounds();
		rect.x += x;
		rect.y += y;
		
		if(rect.getX() < 0 || rect.getX() > 765 || rect.getY() < 0 || rect.getY() > 500)
		{
			collision = true;
		}
		
		for(int i = 0; i<sprites.size(); i++)
		{
			if(rect.getBounds().intersects(sprites.get(i).getBounds()))
			{
				if(!(sprites.get(i) == obj))
				{
					collision = true;
				}
			}
		}
		
		return collision;
	}
	
	/**
	 * Moves the SpriteObj to given cordinates
	 * @param obj
	 * @param x
	 * @param y
	 */
	protected void move(SpriteObj obj, int x, int y)
	{
		if(!(collide(obj,x,y))) //If no collision
		{
			obj.setPos(x, y);
			updateList.add(obj);
			setVisible(true);
		}
		else
		{
			//System.out.println("could not make move, collision!");
		}
	}
	
	/**
	 * Plays the mp3 of the given file
	 * @param songPath
	 */
	protected void playMp3(String songPath)
	{
		Thread thread;
		(thread  = new Thread(new SoundThread((songPath)))).start();
		tunes.add(thread);
	}
	
	/**
	 * Stops the mp3 of given index
	 * @param index
	 */
	protected void stopMp3(int index)
	{
		tunes.get(index).stop();
		
	}
	
	/**
	 * Sets the updateRate of the game (FPS)
	 * @param ratio
	 */
	protected void setUpdateRate(int ratio)
	{
		delay = ratio;
	}
	
	
	
	
	
	
}
