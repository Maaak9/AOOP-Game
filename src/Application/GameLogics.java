package Application;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Framework.GameEngine;
import Framework.Player;
import Framework.Sprite;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info Handels some of the Gamelogics created by the dev
 */
public class GameLogics extends GameApplication {

	public int EnemyTimer = 0;
	
	/**
	 * Constructor
	 */
	public GameLogics()
	{
		
	}
	
	/**
	 * If player losses a life he is immortal for 1sec. 
	 * when immortalTime is greater than 0 he is immortal.
	 * Called from the Timer (FPS) so counts down 100 counts in 1sec
	 * @param players
	 */
	public void immortalCheck(ArrayList<Player> players)
	{
		for(int i = 0; i<players.size(); i++)
		{
			if(players.get(i).immortalTime > 0)
			{
				players.get(i).immortalTime += -1;
			}
		}
	}
	
	/**
	 * Cehckes the hp of the player and deletes 1 health from player. (Spinning coin)
	 * @param player1Lifes
	 * @param player2Lifes
	 */
	public void checkHpAnim(ArrayList<Sprite>player1Lifes, ArrayList<Sprite>player2Lifes)
	{
		if(player1.getHp() < player1HpAnim && player1.getHp() >=0)
		{
			System.out.println("kommer in här iaf" + player1HpAnim);
			Sprite temp = player1Lifes.remove(player1HpAnim-1);
			temp.setVisibility(false);
			player1HpAnim--;
		}
		if(player2.getHp() < player2HpAnim && player2.getHp() >=0)
		{
			Sprite temp = player2Lifes.remove(player2HpAnim-1);
			temp.setVisibility(false);
			player2HpAnim--;
		}
	}
	
	/**
	 * Checks if one of the player has zero life left.
	 * if true end game.
	 * @param players
	 * @return
	 */
	public boolean checkIfDead(ArrayList<Player> players)
	{
		if(players.get(0).getHp() <= 0)
		{

			clearAll();
			for(int i = 0; i<enemies.size(); i++)
			{
				enemies.get(i).stop();
			}
			return true;
		}

		if(players.size() > 0 && players.get(1).getHp() <= 0)
		{
			clearAll();

			for(int i = 0; i<enemies.size(); i++)
			{
				enemies.get(i).stop();
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}
