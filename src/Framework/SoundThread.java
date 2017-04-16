package Framework;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import Application.GameApplication;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 
 * @author Marcus Kärrman & Albin Olsson
 * 
 */

public class SoundThread implements Runnable 
{

	String path;

	public SoundThread(String songPath)
	{
		path = songPath;

	}

	public void playMp3() throws JavaLayerException
	{
		
		try
		{
			System.out.println("playing sound");
			File file = new File(path);
			FileInputStream inputS = new FileInputStream(file);

			BufferedInputStream bis = new BufferedInputStream(inputS);

			Player player = new Player(bis);
			player.play();
			player.play(1);


		} catch(IOException e){}
	}
	
	public void run()
	{
		try 
		{
			playMp3();
		} 
		catch (JavaLayerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}



