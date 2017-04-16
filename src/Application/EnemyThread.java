package Application;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import Framework.Animation;
import Framework.Player;
import Framework.Sprite;
import Framework.SpriteObj;

/**
 * @author Marcus Kärrman & Albin Olsson
 * @info Creates an thread for the Enemy. That bounces and travels around until it collides.
 */
public class EnemyThread extends GameApplication implements Runnable 
{
	Animation enemyAnimation;
	ArrayList<Sprite> sprites;
	ArrayList<Player> players;
	double timeMsJumping = 12;
	double timeMsFalling = 12;
	int life = 4;
	Sprite enemy;
	int x;
	int y;
	int i;

    Timer timer;
	int xPos;
	int yPos;
	
	public EnemyThread(Sprite enemy, ArrayList<Sprite> sprites, ArrayList<Player> players, Animation anim)
	{
		
		enemyAnimation = anim;
		x = 1;
		y = -2;
		i = 0;
		this.enemy = enemy;
		this.sprites = sprites;
		this.players = players;
		
		xPos = enemy.getSprite().getX();
		yPos = enemy.getSprite().getY();
		timer = new Timer(500, new MyTimerActionListener());
		
	}

	protected ActionListener ifStuck(int x, int y)
	{
		//System.out.println("in if stuck");
		
		if(xPos == x && yPos == y)
		{
			enemy.setPos(35, 0);
			
			xPos = enemy.getSprite().getX();
			yPos = enemy.getSprite().getY();
		}
		
		xPos = enemy.getSprite().getX();
		yPos = enemy.getSprite().getY();
		
		
		
		return null;
	}

	protected boolean collide(SpriteObj obj, int x, int y)
	{
		boolean collision = false;
		Rectangle rect = obj.getBounds();
		rect.x += x;
		rect.y += y;

		if(rect.getX() < 0 || rect.getX() > 750 || rect.getY() < 0 || rect.getY() > 500)
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

		for(i = 0; i<players.size(); i++)
		{
			if(rect.getBounds().intersects(players.get(i).getBounds()))
			{
				collision = true;
				if(players.get(i).immortalTime == 0)
				{
					players.get(i).immortalTime = 100;
					players.get(i).setHp(-1);
				}
			}
		}

		return collision;
	}

	@Override
	protected void move(SpriteObj obj, int x, int y)
	{
		if(!(collide(obj,x,y))) //If no collision
		{
			obj.setPos(x, y);
			addToFrame(obj.getSprite());
			setVisible(true);
		}
		else
		{
			//System.out.println("could not make move, collision!");
		}
	}




	public void run() 
	{
		
		timer.start();
		
		while(true)  //runs as long as game is running
		{
			
				while(i<100 && !(collide(enemy,0, y)))
				{
					//enemy.setImageIcon(enemyAnimation.Animate());
					//System.out.println("Enemy thread!");
					if(collide(enemy,x,0))
					{
						x *= -1;						
					}

					try
					{
						TimeUnit.MILLISECONDS.sleep((long) timeMsFalling);
						move(enemy,x,y);

					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					i++;
				} 

				i = 0;

				while(!(collide(enemy, 0,-y)))  //runs while no collision in Y
				{
					//enemy.setImageIcon(enemyAnimation.Animate());
					if(collide(enemy,x,0))
					{
						x *= -1;
					}

					try 
					{
						TimeUnit.MILLISECONDS.sleep((long) timeMsFalling);
						move(enemy,x,-y);
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			}
		}
	class MyTimerActionListener implements ActionListener 
	{
		  public void actionPerformed(ActionEvent e) 
		  {

			  ifStuck(enemy.getSprite().getX(),enemy.getSprite().getY());

		  }

	
	}

	
}









