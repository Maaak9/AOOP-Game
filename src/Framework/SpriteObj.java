package Framework;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Marcus Kärrman & Albin Olsson
 * @info Sprite and Player extends this class. It conatins the most important parts for the 
 * framework to work as it should. 
 */

public abstract class SpriteObj 
{
	protected JLabel label;
	protected JPanel panel;
	protected JPanel shootPanel;
	protected JLabel shootLabel;
	protected ImageIcon shootIcon;
	protected ImageIcon icon;
	
	protected int jumpEnable;
	protected int locationX;
	protected int locationY;
	protected int width;
	protected int height;
	protected int hp;
	protected int shoots;
	
	
	public abstract JPanel getSprite();
	public abstract void setPos(int x, int y);
	public abstract Rectangle getBounds();
	public abstract void setImageIcon(ImageIcon imgIcon);

}
