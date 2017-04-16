package Framework;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info The Class Player extends the SpriteObj and gets the most necessary parts.
 * Holds the attributes like placement x/y, speed, hp etc
 */

public class Player extends SpriteObj
{

	public int dx;
	public int dy;
	public int playerSpeed;
	public int immortalTime = 0;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param picPath
	 */
	public Player(int x, int y, int width, int height, String picPath) 
	{
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		playerSpeed = 2;
		hp = 5;
		jumpEnable = 1;
		panel = new JPanel();
		label = new JLabel();
		icon = new ImageIcon(picPath);
		panel.setOpaque(false);
		panel.setBounds(x, y, width, height);
		label.setIcon(icon);
		panel.add(label);
	}
	

	@Override
	/**
	 * Gets the JPanel of the Sprite
	 */
	public JPanel getSprite() 
	{
		panel.setBounds(locationX, locationY, width, height);
		return panel;
	}
	
	/**
	 * Sets the ImageIcon of the object
	 */
	public void setImageIcon(ImageIcon imgIcon)
	{
		//System.out.println("Hej");
		label.setIcon(imgIcon); 
	}
	
	/**
	 * Sets the Hp of object
	 * @param hp
	 */
	public void setHp(int hp)
	{
		this.hp += hp;
	}
	
	/**
	 * Gets the hp of object
	 * @return
	 */
	public int getHp()
	{
		return hp;
	}
	
	/**
	 * gets the speed of object
	 * @return
	 */
	public int getSpeed()
	{
		return playerSpeed;
	}
	
	/**
	 * Sets the direction in x, Ex 5 = Right, -5 = Left
	 * @param speedX
	 */
	public void SetXDirection(int speedX)
	{
		dx = speedX;
	}

	/**
	 * Sets the direction in y, Ex 5 = down, -5 = up
	 * @param speedY
	 */
	public void SetYDirection(int speedY)
	{
		dy = speedY;
	}
	
	/**
	 * Returns next move, direction
	 * @return
	 */
	public int getNextMoveX()
	{
		return dx;
	}
	
	/**
	 * Returns next move, direction
	 * @return
	 */
	public int getNextMoveY()
	{
		return dy;
	}
	
	/**
	 * Sets the new cordinates of the object
	 */
	public void move() 
	{
		locationX += dx;
		locationY += dy;
	}
	
	/**
	 * Gravity, sets speed downwards
	 * @param speed
	 */
	public void fall(int speed)
	{
		dy = speed;
	}
	
	@Override
	/**
	 * Set position of the object
	 */
	public void setPos(int x, int y) 
	{
		locationX += x;
		locationY += y;
	}
	
	/**
	 * Beta, works ok but decided to not use.
	 * creates a gun shoot.
	 * @param picPath
	 */
	public void initShot(String picPath)
	{
		shootPanel = new JPanel();
		shootPanel.setBounds(locationX, locationY-5, width, height);
		
		shootLabel = new JLabel();
		shootIcon = new ImageIcon(picPath);
		
		shootLabel.setIcon(shootIcon);
		shootPanel.add(shootLabel);
	}

	@Override
	/**
	 * Get the bound of the object
	 */
	public Rectangle getBounds() 
	{
		 return new Rectangle(locationX, locationY, width, height);	
	}
	
	/**
	 * Sets the state of the player, (ground / jump1 / jump2)
	 * @param enable
	 */
	public void setJumpEnable(int enable)
	{
		jumpEnable = enable;
	}
	
	/**
	 * Returns the state of the player (ground / jump1 / jump2)
	 * @return
	 */
	public int isJumpEnable()
	{
		return jumpEnable;
	}

}
