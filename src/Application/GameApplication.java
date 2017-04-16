package Application;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Framework.Animation;
import Framework.GameEngine;
import Framework.Player;
import Framework.Sprite;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * @info Our game mario classic battle game 2 player. 
 * Creates the sprites / palyers for the game and uses the actionPerformed to update the game
 * with 100fps 
 */

public class GameApplication extends Framework.GameEngine
{
	int i = 0;
	static Player player1;
	static Player player2;
	static GameApplication game;
	static JButton startButt;
	public Animation rightPlayer1;
	public Animation leftPlayer1;
	public Animation rightPlayer2;
	public Animation leftPlayer2;
	public Animation enemyAnimation;
	public Animation coinAnimation;
	public int player1HpAnim = 5;
	public int player2HpAnim = 5;
	public ArrayList<Sprite>player1Life;
	public ArrayList<Sprite>player2Life;
	public ArrayList<Sprite>enemySprites;
	public static ArrayList<Thread>enemies;
	public int EnemyTimer = 0;
	public int delay = 1000;
	public int player1JumpDelay = 0;
	public int player1Jump = 0;
	public int player2JumpDelay = 0;
	public int player2Jump = 0;
	public GameLogics gameLogic;
	int[] topRight  = {700, 40};
	int[] topLeft = {105, 40};
	int[] bottomLeft = {100, 480};
	int[] bottomRight = {700, 480};
	int[][] cord = {topRight, topRight, bottomLeft, bottomRight};

	/**
	 * initializes the GameApplication
	 * Creates JFrame using runGui from Window
	 * @param args
	 * @throws JavaLayerException
	 */
	public static void main(String[] args) throws JavaLayerException 
	{
		game = new GameApplication();
		game.runGUI(800,600); //Create JFrame 800x600
		newGame(); 
	}

	/**
	 * Creates the start screen
	 */
	public static void newGame()
	{
		startButt = game.addButton(317, 420, 200, 30, "",true); 
		game.addBackgroundImage("StartScreen.jpg");
		enemies = new ArrayList<Thread>();
	}

	@Override
	/**
	 * Contains the Startbutton that starts the game.
	 * Alos the timer thats used for playing the game in 100FPS
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		// Timer FPS set to 100 Frames per second atm
		if(arg0.getSource().equals(time) && game.gameOn())
		{
			gameLogic.immortalCheck(players);
			//Animating Enemys
			ImageIcon enemeyAnim = enemyAnimation.Animate();
			for(int i = 0; i<enemySprites.size(); i++)
			{
				enemySprites.get(i).setImageIcon(enemeyAnim);
			}
			// Checks Hp, jump, collide, etc
			checkGameState(); 
			//sets animation depending on direction
			if(player2.getNextMoveX() > 0)
			{
				player2.setImageIcon(rightPlayer2.Animate());
			}
			if(player2.getNextMoveX() < 0)
			{
				player2.setImageIcon(leftPlayer2.Animate());
			}

			player1.move();
			player2.move();

			//repaints the players on new cordinates
			addToFrame(player1.getSprite());
			addToFrame(player2.getSprite());
		}

		if(arg0.getSource().equals(startButt))
		{		
			//startbutton clicked -> start game
			startGameApplication();
		}
	}
	
	public void checkGameState()
	{
		// Spawns enemy with give interval
		enemySpawner();
		// Check and update HitPoints
		gameLogic.checkHpAnim(player1Life, player2Life);
		i++;
		//Check if player1 or 2 died
		if(gameLogic.checkIfDead(players))
		{
			game.playMp3("PlayerTwoWinsSound.mp3");	
			game.stopMp3(0);
			try 
			{
				TimeUnit.MILLISECONDS.sleep((long) 3000);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.clearAll();
			player1Life.clear();
			player2Life.clear();
			game.endGame();
			player1HpAnim = 5;
			player2HpAnim = 5;
			newGame();
		}
		
		//Spin the coins
		animateTheCoins();
		
		// Gravity
		player1.fall(2);
		player2.fall(2);

		checkJump(); 
		checkCollide();
	}
	
	public void animateTheCoins()
	{
		ImageIcon temp = coinAnimation.Animate();
		for(int i = 0; i<player1Life.size(); i++)
		{
			player1Life.get(i).setImageIcon(temp);
		}
		for(int i = 0; i<player2Life.size(); i++)
		{
			player2Life.get(i).setImageIcon(temp);
		}
	}
	
	public void checkJump()
	{
		if(player1.isJumpEnable() > 0  && player1Jump > 0)
		{
			player1.SetYDirection(-4);
			player1JumpDelay++;
			if(player1JumpDelay > 50)
			{
				player1JumpDelay = 0;
				player1Jump = 0;
				player1.setJumpEnable(1);
			}
		}

		if(player2Jump > 0)
		{
			player2.setJumpEnable(0);
			player2.SetYDirection(-4);
			player2JumpDelay++;
			if(player2JumpDelay == 50)
			{
				player2JumpDelay = 0;
				player2Jump = 0;
				player2.setJumpEnable(1);
			}
		}
	}
	
	
	public void checkCollide()
	{
		if(collide(player1, player1.getNextMoveX(), player1.getNextMoveY()) == true)
		{
			player1.SetYDirection(0);
		}
		if(collide(player1, player1.getNextMoveX(), player1.getNextMoveY()) == true)
		{
			player1.SetXDirection(0);
		}

		if(player1.getNextMoveX() > 0)
		{
			player1.setImageIcon(rightPlayer1.Animate());
		}
		if(player1.getNextMoveX() < 0)
		{
			player1.setImageIcon(leftPlayer1.Animate());
		}
		
		if(collide(player2, player2.getNextMoveX(), player2.getNextMoveY()) == true)
		{
			player2.SetYDirection(0);
		}
		if(collide(player2, player2.getNextMoveX(), player2.getNextMoveY()) == true)
		{
			player2.SetXDirection(0);
		}
	}
	
	public void enemySpawner()
	{
		EnemyTimer++;
		if(EnemyTimer == delay)
		{
			System.out.println("In enemy spawner");
			int rand = (int)(Math.random() * 4);
			int[] temp = cord[rand];
			Sprite sprite = new Sprite(temp[0], temp[1],35,35, "Enemy1_35x35.png",true);
			if(collide(sprite, temp[0], temp[1]) == true)
			{
				Sprite enemy = game.addSprite(temp[0], temp[1],35,35, "Enemy1_35x35.png",true);
				Thread thread;

				(thread = new Thread(new EnemyThread(enemy,sprites,players, enemyAnimation))).start();
				enemySprites.add(enemy);
				enemies.add(thread);
			}
			EnemyTimer = 0;
			delay = delay -10;
		}
	}
	
	public void startGameApplication()
	{
		game.playMp3("SuperMario.mp3");
		game.playMp3("RoundOneSound.mp3");

		game.clearAll();
		game.addBackgroundImage("GameOn.jpg");

		gameLogic = new GameLogics();
		player1 = game.addPlayer(100, 200, 25, 45, "MarioRight25x45.png");
		player2 = game.addPlayer(300, 300, 25, 45, "LuigiLeft25x45.png");
		leftPlayer1 = new Animation();
		rightPlayer1 = new Animation();
		leftPlayer1.addToAnimation("MarioLeft25x45.png");
		leftPlayer1.addToAnimation("Mario1Left25x45.png");
		leftPlayer1.addToAnimation("Mario2Left25x45.png");
		rightPlayer1.addToAnimation("MarioRight25x45.png");
		rightPlayer1.addToAnimation("Mario1Right25x45.png");
		rightPlayer1.addToAnimation("Mario2Right25x45.png");
		leftPlayer2 = new Animation();
		rightPlayer2 = new Animation();
		leftPlayer2.addToAnimation("LuigiLeft25x45.png");
		leftPlayer2.addToAnimation("Luigi1Left25x45.png");
		leftPlayer2.addToAnimation("Luigi2Left25x45.png");
		rightPlayer2.addToAnimation("LuigiRight25x45.png");
		rightPlayer2.addToAnimation("Luigi1Right25x45.png");
		rightPlayer2.addToAnimation("Luigi2Right25x45.png");

		enemyAnimation = new Animation();
		enemyAnimation.addToAnimation("Enemy1_35x35.png");
		enemyAnimation.addToAnimation("Enemy2_35x35.png");
		enemyAnimation.setAnimationSpeed(30);

		coinAnimation = new Animation();
		coinAnimation.addToAnimation("coin1.png");
		coinAnimation.addToAnimation("coin2.png");
		coinAnimation.addToAnimation("coin3.png");
		coinAnimation.addToAnimation("coin4.png");
		coinAnimation.addToAnimation("coin5.png");
		coinAnimation.addToAnimation("coin6.png");
		coinAnimation.addToAnimation("coin7.png");
		coinAnimation.addToAnimation("coin8.png");

		player1Life = new ArrayList<Sprite>();
		player1Life.add(game.addSprite(37, 107,32,32, "coin1.png",true));
		player1Life.add(game.addSprite(82, 107,32,32, "coin1.png",true));
		player1Life.add(game.addSprite(127, 107,32,32, "coin1.png",true));
		player1Life.add(game.addSprite(172, 107,32,32, "coin1.png",true));
		player1Life.add(game.addSprite(217, 107,32,32, "coin1.png",true));
		player2Life = new ArrayList<Sprite>();
		player2Life.add(game.addSprite(715, 107,32,32, "coin1.png",true));
		player2Life.add(game.addSprite(670, 107,32,32, "coin1.png",true));
		player2Life.add(game.addSprite(625, 107,32,32, "coin1.png",true));
		player2Life.add(game.addSprite(580, 107,32,32, "coin1.png",true));
		player2Life.add(game.addSprite(535, 107,32,32, "coin1.png",true));

		game.addSprite(32, 405,265,38, "Obj1.png",false);      //bottom left
		game.addSprite(485, 405,265,38, "Obj1.png",false);     //bottom right
		game.addSprite(32, 105,265,38, "Obj1.png",false);      //upper left
		game.addSprite(487, 105,265,38, "Obj1.png",false);     //upper right
		game.addSprite(212, 230,358,38, "MiddleObj.png",false);     //Middle
		game.addSprite(32, 275,84,38, "Small.png",false);      //left small
		game.addSprite(665, 275,84,38, "Small.png",false);      //left small
		game.addSprite(367, 381,47,42, "POW.png",false);      //POW

		Thread thread;
		Sprite enemy = game.addSprite(105, 40, 35,35,  "Enemy1_35x35.png",true);
		(thread = new Thread(new EnemyThread(enemy,sprites,players, enemyAnimation))).start();
		enemySprites = new ArrayList<Sprite>();
		enemySprites.add(enemy);
		enemies.add(thread);

		game.startGame(10);
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		int keyPressed = arg0.getKeyCode();
		if(gameOn())
		{
			switch(keyPressed) 
			{
			case KeyEvent.VK_LEFT:
				player1.SetXDirection((-1)* player1.getSpeed());
				break;

			case KeyEvent.VK_RIGHT:
				player1.SetXDirection(1 * player1.getSpeed());
				break;

			case KeyEvent.VK_UP:
				player1.SetYDirection((-1)* player1.getSpeed());
				break;

			case KeyEvent.VK_CONTROL:
				if(player1.isJumpEnable() > 0)
				{
					player1Jump = 1;
				}
				break;

			case KeyEvent.VK_DOWN:
				player1.SetYDirection(player1.getSpeed());
				break;

			case KeyEvent.VK_A:
				player2.SetXDirection((-1)* player2.getSpeed());
				break;

			case KeyEvent.VK_D:
				player2.SetXDirection(1 * player2.getSpeed());
				break;

			case KeyEvent.VK_W:
				player2.SetYDirection((-1)* player2.getSpeed());
				break;

			case KeyEvent.VK_G:
				if(player2.isJumpEnable() > 0)
				{
					player2Jump = 1;
				}
				break;

			case KeyEvent.VK_S:
				player2.SetYDirection(player2.getSpeed());
				break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		int key = arg0.getKeyCode();

		if (key == KeyEvent.VK_LEFT) 
		{
			player1.SetXDirection(0);
		}

		if (key == KeyEvent.VK_RIGHT) 
		{
			player1.SetXDirection(0);
		}

		if (key == KeyEvent.VK_UP) 
		{
			player1.SetYDirection(0);
		}

		if (key == KeyEvent.VK_DOWN)
		{
			player1.SetYDirection(0);
		}
		if (key == KeyEvent.VK_A) 
		{
			player2.SetXDirection(0);
		}

		if (key == KeyEvent.VK_D) 
		{
			player2.SetXDirection(0);
		}

		if (key == KeyEvent.VK_W) 
		{
			player2.SetYDirection(0);
		}

		if (key == KeyEvent.VK_S)
		{
			player2.SetYDirection(0);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{


	}

}
