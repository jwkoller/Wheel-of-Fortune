/*TITLE: Wheel of Fortune!
 *AUTHOR: Jon Koller  
 *PURPOSE: A console recreation of one round of the Wheel of Fortune game show.
 *DATE: January 8, 2019
 */
package koller.practice.wheeloffortune;

import java.util.Scanner;

public class MainClass 
{
	public static final int NUMBER_OF_PLAYERS = 3;
	public static final int LINE_LENGTH_CUTOFF = 45;
	public static final String[][] MENU_OPTIONS = {
			{"Player Menu", "Main Menu"},
			{"...........", "........."},
			{"Spin the Wheel", "Start New Game"},
			{"Buy a Vowel", "Quit Program"},
			{"Solve the Puzzle"} 
	};

	public static void main(String[] args) 
	{
		char menuSelect = ' ';
		
		Scanner input = new Scanner(System.in);
		GameWheel gameWheel = new GameWheel();
		Puzzle puzzle = new Puzzle();
		Players[] players = new Players[NUMBER_OF_PLAYERS];
		players[0] = new Players();
		players[1] = new Players();
		players[2] = new Players();
		
		displayWelcomeBanner();
		puzzle.setLoadPuzzleFile();
		
		while(validateMainMenu(input) == 'A')
		{	
			if(puzzle.getPuzzleCounter() < 0)
			{
				displayLoadFileError();
			}
			else
			{
				while(players[NUMBER_OF_PLAYERS - 1].getPlayerName().equals(""))
				{
					players[Players.getPlayerTurn()].setPlayerName(validatePlayerName(input, Players.getPlayerTurn()));
					Players.setPlayerTurn();
				}
				
				puzzle.setCurrentPuzzle();

				while(puzzle.getSolveAttemptResult() == false)
				{
					displayPuzzle(puzzle.getHiddenPuzzle());
					displayPlayerPanel(players[0].getPlayerName(), players[0].getPlayerCash(), players[1].getPlayerName(), players[1].getPlayerCash(),
							players[2].getPlayerName(), players[2].getPlayerCash());
					menuSelect = validatePlayerMenu(input, players[Players.getPlayerTurn()].getPlayerName());

					if(menuSelect == 'A')
					{
						gameWheel.setWheelSpin();
						
						if(gameWheel.getWheelSpin() == 0)
						{
							displayBankrupt();
							players[Players.getPlayerTurn()].setPlayerCash();
						}
						else
						{
							puzzle.setLetterSearch(validateLetterPrompt(input, gameWheel.getWheelSpin()));
							
							if(puzzle.getLettercount() > 0)
							{
								displayLetterSearchResult(puzzle.getLettercount());
								players[Players.getPlayerTurn()].setPlayerCash(gameWheel.getWheelSpin(), puzzle.getLettercount());
							}
							else
							{
								if(puzzle.getLettercount() == 0)
								{
									displayLetterSearchResult();
								}
								else
								{
									displayLetterUsed();
								}
								Players.setPlayerTurn();
							}
						}
					}
					else if(menuSelect == 'B')
					{
						if(players[Players.getPlayerTurn()].getPlayerCash() < puzzle.getVowelCost())
						{
							displayNoFundsForVowel();
						}
						else
						{
							puzzle.setLetterSearch(validateLetterPrompt(input));
							players[Players.getPlayerTurn()].setPlayerCash(puzzle.getVowelCost());
							
							if(puzzle.getLettercount() > 0)
							{
								displayLetterSearchResult(puzzle.getLettercount());
							}
							else
							{
								if(puzzle.getLettercount() == 0)
								{
									displayLetterSearchResult();
								}
								else
								{
									displayLetterUsed();
								}
								Players.setPlayerTurn();
							}
						}
					}
					else
					{
						puzzle.setSolveAttempt(validateSolveAttempt(input));
						
						if(puzzle.getSolveAttemptResult() == false)
						{
							displaySolveAttemptResult(puzzle.getSolveAttempt());
							Players.setPlayerTurn();
						}
						else
						{
							displaySolveAttemptResult();
						}
					}
				}//end of puzzle solved == false
				
				displayPuzzle(puzzle.getCurrentPuzzle());
				displayPlayerPanel(players[0].getPlayerName(), players[0].getPlayerCash(), players[1].getPlayerName(), players[1].getPlayerCash(),
						players[2].getPlayerName(), players[2].getPlayerCash());
				displayGameWinner(players[0].getPlayerName(), players[0].getPlayerCash(), players[1].getPlayerName(), players[1].getPlayerCash(),
						players[2].getPlayerName(), players[2].getPlayerCash());
				Players.setResetPlayerTurn();
				players[0].setResetPlayerCash();
				players[1].setResetPlayerCash();
				players[2].setResetPlayerCash();
			}	
		}	//end of run while not quit
		
		displayFarewellBanner();
		
	}	//end of main method

	public static void displayWelcomeBanner()
	{
		System.out.println("************************************************************");
		System.out.printf("%1s%19s%-39s%1s", "*", "", "WHEEL! OF! FORTUNE!", "*");
		System.out.printf("\n%1s%5s%-53s%1s", "*", "", "This program allows 3 people to play the popular", "*");
		System.out.printf("\n%1s%7s%-51s%1s", "*", "", "game show Wheel of Fortune via the console.", "*");
		System.out.println("\n************************************************************");
	}
	
	public static void displayFarewellBanner()
	{
		System.out.println("\n************************************************************");
		System.out.printf("%1s%9s%-49s%1s", "*", "", "Thank you for playing Wheel of Fortune!", "*");
		System.out.printf("\n%1s%15s%-43s%1s", "*", "", "Enjoy the rest of your day!", "*");
		System.out.println("\n************************************************************");
	}
	
	public static void displayLoadFileError()
	{
		System.out.println("\nThere was an error loading the game. Please exit and contact");
		System.out.println("technical support. Thank you.");
	}
	
	public static void displayInvalidEntry()
	{
		System.out.println("\n............................................................");
		System.out.printf("%13s%-47s", "", "Invalid entry.  Please try again.");
		System.out.println("\n............................................................");
	}
	
	public static void displayMainMenu()
	{
		System.out.printf("\n%1s%-59s", "", "__________________________________________________________");
		System.out.printf("\n%1s%59s", "|", "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[0][1], "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[1][1], "|");
		System.out.printf("\n%1s%18s%-4s%-36s%1s", "|", "", "[A]", MENU_OPTIONS[2][1], "|");
		System.out.printf("\n%1s%18s%-4s%-36s%1s", "|", "", "[Q]", MENU_OPTIONS[3][1], "|");
		System.out.printf("\n%60s","|__________________________________________________________|\n");
		System.out.print("\nPlease enter your selection here: ");
	}
	
	public static char validateMainMenu(Scanner borrowedInput)
	{
		char localMenuSelect = ' ';
		
		displayMainMenu();
		localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		
		while(localMenuSelect != 'A' && localMenuSelect != 'Q')
		{
			displayInvalidEntry();
			displayMainMenu();
			localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		}
		
		return localMenuSelect;
	}
	
	public static void displayPlayerNamePrompt(int borrowedBorrowedPlayerTurn)
	{
		System.out.println("\nEnter a name for Player " + (borrowedBorrowedPlayerTurn + 1) + ".");
		System.out.print("Name: ");
	}
	
	public static String validatePlayerName(Scanner borrowedInput, int borrowedPlayerTurn)
	{
		boolean localValidEntry = false;
		String localPlayerName = "";
		
		displayPlayerNamePrompt(borrowedPlayerTurn);
		localPlayerName = borrowedInput.next().trim();
		
		while(localValidEntry == false)
		{
			if(localPlayerName.length() > 15)
			{
				System.out.println("\nPlayer name must be 15 characters or less. Please try again.");
				displayPlayerNamePrompt(borrowedPlayerTurn);
				localPlayerName = borrowedInput.next().trim();
			}
			else
			{
				localValidEntry = true;
			}
		}
		localPlayerName = localPlayerName.substring(0, 1).toUpperCase() + localPlayerName.substring(1).toLowerCase();
		
		return localPlayerName;
	}
	
	public static void displayPuzzle(char[] borrowedPuzzle)
	{
		int localIndex = 0;
		
		System.out.println("\n____________________________________________________________");
		System.out.printf("%22s%-38s", "", "WHEEL OF FORTUNE");
		System.out.println("\n____________________________________________________________");
		System.out.println();
		System.out.println();
		while(localIndex < borrowedPuzzle.length)
		{
			if(localIndex > LINE_LENGTH_CUTOFF && borrowedPuzzle[localIndex] == ' ' && borrowedPuzzle.length - localIndex > 5)
			{
				System.out.println();
			}
			else
			{
				System.out.print(borrowedPuzzle[localIndex]);
				localIndex++;
			}
		}
		System.out.println("\n");
		System.out.println("____________________________________________________________");
		
	}
	
	public static void displayPlayerMenu(String borrowedBorrowedPlayerName)
	{
		System.out.println("\nIt is player " + borrowedBorrowedPlayerName + "'s turn.");
		System.out.printf("%1s%-59s", "", "__________________________________________________________");
		System.out.printf("\n%1s%59s", "|", "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[0][0], "|");
		System.out.printf("\n%1s%24s%-34s%1s", "|", "", MENU_OPTIONS[1][0], "|");
		System.out.printf("\n%1s%19s%-4s%-35s%1s", "|", "", "[A]", MENU_OPTIONS[2][0], "|");
		System.out.printf("\n%1s%19s%-4s%-35s%1s", "|", "", "[B]", MENU_OPTIONS[3][0], "|");
		System.out.printf("\n%1s%19s%-4s%-35s%1s", "|", "", "[C]", MENU_OPTIONS[4][0], "|");
		System.out.printf("\n%60s","|__________________________________________________________|\n");
		System.out.print("\nPlease enter your selection here: ");
	}
	
	public static char validatePlayerMenu(Scanner borrowedInput, String borrowedPlayerName)
	{
		char localMenuSelect = ' ';
		
		displayPlayerMenu(borrowedPlayerName);
		localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		
		while(localMenuSelect != 'A' && localMenuSelect != 'B' && localMenuSelect != 'C')
		{
			displayInvalidEntry();
			displayPlayerMenu(borrowedPlayerName);
			localMenuSelect = borrowedInput.next().toUpperCase().charAt(0);
		}
		
		return localMenuSelect;
	}
	
	public static void displayLetterPrompt(int borrowedBorrowedWheelAmount)
	{
		System.out.println("\nYour wheel spin landed on $" + borrowedBorrowedWheelAmount +"! Now guess a consonant");
		System.out.println("in the puzzle.");
		System.out.print("\nLetter: ");
	}
	
	public static void displayLetterPrompt()
	{
		System.out.println("\nEnter the vowel you would like to purchase for $250.");
		System.out.print("\nVowel: ");
	}
	
	public static String validateLetterPrompt(Scanner borrowedInput, int borrowedWheelAmount)
	{
		String localInput = "";
		
		displayLetterPrompt(borrowedWheelAmount);
		localInput = borrowedInput.next().trim();
		
		while(!String.valueOf(localInput.charAt(0)).matches("[bBcCdDfFgGhHjJkKlLmMnNpPqQrRsStTvVwWxXyYzZ]"))
		{
			displayInvalidEntry();
			displayLetterPrompt(borrowedWheelAmount);
			localInput = borrowedInput.next().trim();
		}
		
		return String.valueOf(localInput.charAt(0));
	}
	
	public static String validateLetterPrompt(Scanner borrowedInput)
	{
		String localInput = "";
		
		displayLetterPrompt();
		localInput = borrowedInput.next().trim();
		
		while(!String.valueOf(localInput.charAt(0)).matches("[aAeEiIoOuU]"))
		{
			displayInvalidEntry();
			displayLetterPrompt();
			localInput = borrowedInput.next().trim();
		}
		
		return String.valueOf(localInput.charAt(0));
	}
	
	public static void displayBankrupt()
	{
		System.out.println("\n************************************************************");
		System.out.println("Oh no! The wheel landed on Bankrupt. Your money has been");
		System.out.println("reset to zero!");
		System.out.println("************************************************************");
	}
	
	public static void displayLetterSearchResult()
	{
		System.out.println("\n************************************************************");
		System.out.println("There were none of that letter in the puzzle.");
		System.out.println("************************************************************");
	}
	
	public static void displayLetterUsed()
	{
		System.out.println("\n************************************************************");
		System.out.println("That letter has already been used.");
		System.out.println("************************************************************");
	}
	
	public static void displayLetterSearchResult(int borrowedLetterCount)
	{
		System.out.println("\n************************************************************");
		if(borrowedLetterCount == 1)
		{
			System.out.println("There was " + borrowedLetterCount + " of that letter in the puzzle!");
		}
		else
		{
			System.out.println("There were " + borrowedLetterCount + " of that letter in the puzzle!");
		}		
		System.out.println("************************************************************");
	}
	
	public static void displayNoFundsForVowel()
	{
		System.out.println("\n************************************************************");
		System.out.println("You don't have enough cash to purchase a vowel.");
		System.out.println("************************************************************");
	}
	
	public static void displayPlayerPanel(String borrowedPlayer1Name, int borrowedPlayer1Cash, String borrowedPlayer2Name, int borrowedPlayer2Cash,
			String borrowedPlayer3Name, int borrowedPlayer3Cash)
	{
		System.out.println("############################################################");
		System.out.println("____________________________________________________________");
		System.out.println();
		System.out.printf("%-16s%6s%-16s%6s%-16s", borrowedPlayer1Name, "", borrowedPlayer2Name, "", borrowedPlayer3Name);
		System.out.printf("\n%-16s%6s%-16s%6s%-16s", "________________", "", "________________", "", "________________");
		System.out.printf("\n%1s%14s%1s%6s%1s%14s%1s%6s%1s%14s%1s", "|", "", "|", "", "|", "", "|", "", "|", "", "|");
		System.out.printf("\n%1s%2s%11d%2s%6s%1s%2s%11d%2s%6s%1s%2s%11d%2s", "|", "$", borrowedPlayer1Cash, "|", "", "|", "$", borrowedPlayer2Cash, "|", "", "|", "$", borrowedPlayer3Cash, "|");
		System.out.printf("\n%-16s%6s%-16s%6s%-16s", "|______________|", "", "|______________|", "", "|______________|");
		System.out.println("\n############################################################");
	}
	
	public static void displaySolveAttemptPrompt()
	{
		System.out.println("\nEnter your puzzle solution attempt.");
		System.out.print("\nSolution: ");
	}
	
	public static String validateSolveAttempt(Scanner borrowedInput)
	{
		String localInput = "";
		
		displaySolveAttemptPrompt();
		borrowedInput.nextLine();
		localInput = borrowedInput.nextLine().trim();
		
		return localInput;
	}
	
	public static void displaySolveAttemptResult(String borrowedAttempt)
	{
		System.out.println("\n************************************************************");
		System.out.println("Unfortunatly the solution: ");
		System.out.println("\n" + borrowedAttempt);
		System.out.println("\nWas not correct.");
		System.out.println("************************************************************");
	}
	
	public static void displaySolveAttemptResult()
	{
		System.out.println("\n************************************************************");
		System.out.println("Congratulations! You have correctly solved the puzzle!");
		System.out.println("************************************************************");
	}
	
	public static void displayGameWinner(String borrowedPlayer1Name, int borrowedPlayer1Cash, String borrowedPlayer2Name, int borrowedPlayer2Cash,
			String borrowedPlayer3Name, int borrowedPlayer3Cash)
	{
		String localWinner = "";
		int localWinnerCash = 0;
		
		if(borrowedPlayer1Cash > borrowedPlayer2Cash && borrowedPlayer1Cash > borrowedPlayer3Cash)
		{
			localWinner = borrowedPlayer1Name;
			localWinnerCash = borrowedPlayer1Cash;
		}
		else if(borrowedPlayer2Cash > borrowedPlayer1Cash && borrowedPlayer2Cash > borrowedPlayer3Cash)
		{
			localWinner = borrowedPlayer2Name;
			localWinnerCash = borrowedPlayer2Cash;
		}
		else
		{
			localWinner = borrowedPlayer3Name;
			localWinnerCash = borrowedPlayer3Cash;
		}
		
		System.out.println("\n************************************************************");
		System.out.println("Congratulations to " + localWinner + "! They are today's game");
		System.out.println("winner with $" + localWinnerCash +"!");
		System.out.println("************************************************************");
	}
	
}	//end of MainClass


