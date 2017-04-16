package Framework;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info Sprite extends SpriteObj that contains the most important methods and variables for the sprite.
 * Contains of placement on the frame x/y, imageicon, etc
 */
public class Sprite extends SpriteObj
{
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param picPath
	 * @param visibility
	 */
	public Sprite(int x, int y, int width, int height, String picPath, boolean visibility) 
	{
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		
		panel = new JPanel();
		label = new JLabel();
		icon = new ImageIcon(picPath);
		
		panel.setBounds(x, y, width, height);
		label.setIcon(icon);
		panel.add(label);
		panel.setOpaque(false);
		panel.setVisible(visibility);
	}

	/**
	 * Sets visibility of the object
	 * @param x
	 */
	public void setVisibility(boolean x)
	{
		panel.setVisible(x);
	}
	
	@Override
	/**
	 * Gets the JPanel of the object
	 */
	public JPanel getSprite() 
	{
		panel.setBounds(locationX, locationY, width, height);
		return panel;
	}

	@Override
	/**
	 * Sets position x/y 
	 */
	public void setPos(int x, int y) 
	{
		locationX += x;
		locationY += y;
		//panel.setBounds(locationX, locationY, width, height);
	}

	@Override
	/**
	 * Gets the bounds
	 */
	public Rectangle getBounds() 
	{
		Rectangle rect = new Rectangle();
		rect.setBounds(locationX, locationY, width, height);
		
		return rect;
	}

	@Override
	/**
	 * Sets the imageIcons of the object
	 */
	public void setImageIcon(ImageIcon imgIcon) 
	{
		//System.out.println("Hej");
		label.setIcon(imgIcon); 
	}

}
