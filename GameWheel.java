package koller.practice.wheeloffortune;

import java.util.Random;

public class GameWheel 
{
	private final int[] GAME_WHEEL = {0, 800, 500, 650, 500, 900, 0, 2500, 1000, 600, 700, 600, 650, 500, 700, 850, 600,
			550, 500, 600, 0, 650, 750, 700};
	private int wheelSpin = 0;
	private Random randomNG = new Random();
	
	public void setWheelSpin()
	{
		wheelSpin = GAME_WHEEL[getRandomNG()];
	}
	
	public int getRandomNG()
	{
		return randomNG.nextInt(GAME_WHEEL.length);
	}
	
	public int getWheelSpin()
	{
		return wheelSpin;
	}

	
}	//end of GameWheel class
