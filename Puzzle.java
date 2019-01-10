package koller.practice.wheeloffortune;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Puzzle 
{
	private final String PUZZLE_FILE = "puzzleOptions.dat";
	private final int RESET_VALUE = 0;
	private final int OPTIONS_SIZE = 100;
	private final int COUNTERS_SIZE = 3;
	private final int VOWEL_COST = 250;
	private String[] puzzleOptions = new String[OPTIONS_SIZE];
	private String[] usedLetters = new String[21];
	private int[] counters = new int[COUNTERS_SIZE];		//[0]number of puzzles loaded from file [1]letters found in search [2]letters already used
	private int[] letterIndexLocations;
	private char[][] currentPuzzleArray = new char[2][];	//[0][x]visible puzzle [1][x]hidden puzzle
	private String solveAttempt = "";
	private Random randomNG = new Random();
	
	public void setLoadPuzzleFile()
	{
		counters[0] = RESET_VALUE;
		
		try
		{
			Scanner puzzleFile = new Scanner(new FileInputStream(PUZZLE_FILE));
			
			for(int localIndex = 0; puzzleFile.hasNext() && localIndex < OPTIONS_SIZE; localIndex++)
			{
				puzzleOptions[localIndex] = puzzleFile.next();
				counters[0]++;
			}
			puzzleFile.close();
		}
		catch(IOException ex)
		{
			counters[0] = -1;
		}
	}
	
	public void setCurrentPuzzle()
	{
		currentPuzzleArray[0] = puzzleOptions[getRandomNG()].replaceAll("_", " ").toCharArray();
		counters[2] = RESET_VALUE;
		setHiddenPuzzle();
	}
	
	public void setHiddenPuzzle()
	{
		currentPuzzleArray[1] = new char[currentPuzzleArray[0].length];
		
		for(int localIndex = 0; localIndex < currentPuzzleArray[0].length; localIndex++)
		{
			if(currentPuzzleArray[0][localIndex] != ' ')
			{
				currentPuzzleArray[1][localIndex] = '*';
			}
			else
			{
				currentPuzzleArray[1][localIndex] = currentPuzzleArray[0][localIndex];
			}
		}
	}
	
	public void setLetterSearch(String borrowedLetter)
	{
		counters[1] = RESET_VALUE;
		letterIndexLocations = new int[currentPuzzleArray[0].length];
		
		for(int localIndex = 0; localIndex < counters[2]; localIndex++)
		{
			if(borrowedLetter.equalsIgnoreCase(usedLetters[localIndex]))
			{
				counters[1] = -1;
				localIndex = counters[2];
			}
		}
		
		if(counters[1] == 0)
		{
			for(int localIndex = 0; localIndex < currentPuzzleArray[0].length; localIndex++)
			{
				if(borrowedLetter.equalsIgnoreCase(String.valueOf(currentPuzzleArray[0][localIndex])))
				{
					letterIndexLocations[counters[1]] = localIndex;
					counters[1]++;	
				}
			}
			
			setRevealPuzzle();
			setAddToUsedLetters(borrowedLetter);
		}
	}
	
	public void setRevealPuzzle()
	{
		for(int localIndex = 0; localIndex < counters[1]; localIndex++)
		{
			currentPuzzleArray[1][letterIndexLocations[localIndex]] = currentPuzzleArray[0][letterIndexLocations[localIndex]];
		}
	}
	
	public void setAddToUsedLetters(String borrowedLetter)
	{
		usedLetters[counters[2]] = borrowedLetter;
		counters[2]++;
	}
	
	public void setSolveAttempt(String borrowedSolveAttempt)
	{
		solveAttempt = borrowedSolveAttempt;
	}

	public boolean getSolveAttemptResult()
	{
		return solveAttempt.equalsIgnoreCase(String.valueOf(currentPuzzleArray[0]));
	}
	
	
	public int getRandomNG()
	{
		return randomNG.nextInt(counters[0]);
	}
	
	public int getLettercount()
	{
		return counters[1];
	}
	
	public char[] getHiddenPuzzle()
	{
		return currentPuzzleArray[1];
	}
	
	public char[] getCurrentPuzzle()
	{
		return currentPuzzleArray[0];
	}
	
	public int getPuzzleCounter()
	{
		return counters[0];
	}
	
	public String getSolveAttempt()
	{
		return solveAttempt;
	}
	
	public int getVowelCost()
	{
		return VOWEL_COST;
	}
}	//end of Puzzle class
