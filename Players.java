package koller.practice.wheeloffortune;

public class Players 
{
	private static final int RESET_VALUE = 0;
	private static final int LAST_PLAYER = 2;
	private static int playerTurn = 0;
	private String playerName = "";
	private int playerCash = 0;
	
	public Players(String borrowedName)
	{
		playerName = borrowedName;
	}
	
	public void setPlayerCash(int borrowedWheelResult, int borrowedCorrectLetters)
	{
		if(borrowedCorrectLetters == 0)
		{
			setPlayerTurn();
		}
		else
		{
			playerCash += borrowedWheelResult * borrowedCorrectLetters;
		}
	}
	
	public void setPlayerCash()
	{
		playerCash = RESET_VALUE;
		setPlayerTurn();
	}
	
	public void setPlayerCash(int borrowedVowelCost)
	{
		playerCash -= borrowedVowelCost;
	}
	
	public static void setPlayerTurn()
	{
		if(playerTurn == LAST_PLAYER)
		{
			playerTurn = RESET_VALUE;
		}
		else
		{
			playerTurn++;
		}
	}
	
	public void setResetPlayerCash()
	{
		playerCash = RESET_VALUE;
	}
	
	public static void setResetPlayerTurn()
	{
		playerTurn = RESET_VALUE;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getPlayerCash()
	{
		return playerCash;
	}
	
	public static int getPlayerTurn()
	{
		return playerTurn;
	}
	
}	//end of Players class
