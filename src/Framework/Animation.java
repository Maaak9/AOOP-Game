package Framework;

import java.util.ArrayList;

import javax.swing.ImageIcon;
/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info Creates an ArrayList of ImageIcons that can be used to create
 * an animation with the method Animate
 */
public class Animation {

	ArrayList<ImageIcon> Animation;
	int animationSpeed = 10;
	int i = 0;
	int delay = 0;
	
	/**
	 * Constructor
	 * Creates an ArrayList of ImageIcons
	 */
	public Animation()
	{
		Animation = new ArrayList<ImageIcon>();
	}
	
	/**
	 * Sets the animation speed
	 * @param speed
	 */
	public void setAnimationSpeed(int speed)
	{
		animationSpeed = speed;
	}
	
	/**
	 * Removes the animation
	 */
	public void removeAnimation()
	{
		Animation.clear();
	}
	
	/**
	 * Animates at the given speed of the object.
	 * @return
	 */
	public ImageIcon Animate()
	{
		int length = Animation.size()-1;
		if(delay == animationSpeed)
		{
			i++;
			if(i > length)
			{
				i = 0;
			}
			delay = 0;
		}
		delay++;
		ImageIcon imgIcon = getAnimation(i);
		return imgIcon;
	}
	
	/**
	 * Creates imageicon of the given imagepath
	 * Adds to the ArrayList
	 * @param picPath
	 */
	public void addToAnimation(String picPath)
	{
		ImageIcon imgIcon = new ImageIcon(picPath);
		Animation.add(imgIcon);
	}
	
	/**
	 * Gets the ImageIcon from the Arraylist of given index
	 * @param x
	 * @return
	 */
	public ImageIcon getAnimation(int x)
	{
		return Animation.get(x);
	}
	
}
