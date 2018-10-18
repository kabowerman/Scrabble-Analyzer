package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static Scanner reader;
	public static File file;
	public static Player player1;
	public static Player player2;
	public static String[] plays; // All of the plays in the game
	public static int currPlay; // Number of the current play
	public static Scanner enter;
	private static int[] bag;
	public static Tourney tourney;

	public static void main(String[] args) {
		enter = new Scanner(System.in);
		tourney = new Tourney();
		addNames(); // Enter names for the player
		System.out.println("How to analyze?\nEnter 1 for games from a folder\nEnter 2 for games from a player's cross-tables page");
		int option = Integer.parseInt(enter.nextLine());
		if (option == 1) {
			gamesFromFolder(); //TODO
		}
		else if (option == 2) {
			gamesFromPage(); //TODO
		}
	}
	
	// Analyzes games from a folder
	public static void gamesFromFolder() {
		System.out.println("Enter the path length to the folder");
		String path = enter.nextLine();
		System.out.println(path);
		if (path.charAt(0) == '"') {
			path = path.substring(1);
			if (path.indexOf("\"") != -1) {
				path = path.substring(0, path.indexOf("\""));
			}
		}
		System.out.println(path);
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			Game g = createGame(file);
			System.out.println(file.getName());
			tourney.addGame(g);
		}
		tourney.fillStats();
		tourney.printAverages();
	}
	
	//TODO Will do games from a cross-tables page
	public static void gamesFromPage() {
		tourney = new Tourney();
		addNames(); // Enter names for the player
	}

	// Keeps asking for names to add to tourney object until 'N' is entered
	public static void addNames() {
		boolean end = false;
		while (!end) {
			System.out.println("Enter name of player you want to analyze. Replace spaces in name with '_' (Example: Andrew Apple would be 'Andrew_Apple')");
			tourney.addName(enter.nextLine());
			System.out.println("Want to add another name for the same player? Y/N");
			String str = enter.nextLine();
			if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")) {
				end = true;
			}
		}
	}
	
	// Given the path length to a file, returns a game object
	public static Game createGame(File file) {
		try {
			reader = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: File not Found");
			System.exit(0);
		}
		player1 = new Player();
		player2 = new Player();
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		String name1 = reader.nextLine();
		String name2 = reader.nextLine();
		namePlayers(name1, name2);
		populatePlays();
		fillBag();
		currPlay = 0;
		while (currPlay < plays.length) {
			String string = plays[currPlay];
			Player player = player1; // By default assume player1
			if (string.substring(1, string.indexOf(":")).equals(player2.getName())) { // If the name matches player2
				player = player2; // Set player to player2
			}
			Move move = new Move(plays, currPlay, player, bag); // Make new move Object
			if (move.parseMove()) {
				System.out.println(currPlay);
				currPlay++;
				bag = move.getBag();
				if (move.getType() == 2) {
					currPlay++;
				}
			} 
			else { // at end of game
				String s1 = plays[move.getPlayNumber()]; // String of last play made by a player
				String s2 = plays[plays.length - 1]; // String of last play made by the other player
				int score1 = findScore(s1);
				int score2 = findScore(s2);
				if (s1.contains(player1.getName())) {
					player1.setScore(score1);
					player2.setScore(score2);
				} else if (s1.contains(player2.getName())) {
					player1.setScore(score2);
					player2.setScore(score1);
				}

				if (s2.contains(player1.getName())) { // If last play made by player1, add the bag to the tiles drawn by
														// player2
					player2.addBag(bag);
					//TODO this doesn't work if game ends before bag is empty
				}
				else if (s2.contains(player2.getName())) {
					player1.addBag(bag);
					//TODO this doesn't work if game ends before bag is empty
				}
//				player1.calculateOpp(player2); // Calculates opponent score and turnover
//				player2.calculateOpp(player1); // Calculates opponent score and turnover
				player1.setOpponent(player2);
				player2.setOpponent(player1);
				currPlay += 100;
			}
		}
//		printStatements();
		Game game = new Game(player1, player2);
		return game;
	}
	
	// Prints out the player's private variables for testing purpose
	public static void printStatements() {
		System.out.println("Player1 turnover " + player1.getTurnover());
		System.out.println("Player2 turnover " + player2.getTurnover());
		System.out.println("Player1 Opponent turnover " + player1.getOpponent().getTurnover());
		System.out.println("Player2 Opponent turnover " + player2.getOpponent().getTurnover());
		System.out.println("Player1 Score " + player1.getScore());
		System.out.println("Player2 Score " + player2.getScore());
		System.out.println("Player1 Opponent Score " + player1.getOpponent().getScore());
		System.out.println("Player2 Opponent Score " + player2.getOpponent().getScore());
		System.out.println("Player1 numTurns " + player1.getNumTurns());
		System.out.println("Player2 numTurns " + player2.getNumTurns());
		System.out.println("Player1 numBingos " + player1.getNumBingos());
		System.out.println("Player2 numBingos " + player2.getNumBingos());
		System.out.println("Player1 numSevens " + player1.getNumSevens());
		System.out.println("Player2 numSevens " + player2.getNumSevens());
		System.out.println("Player1 numEights " + player1.getNumEights());
		System.out.println("Player2 numEights " + player2.getNumEights());
		System.out.println("Player1 numLongBingos " + player1.getNumLongBingos());
		System.out.println("Player2 numLongBingos " + player2.getNumLongBingos());
		System.out.println("Player1 numZeroScore " + player1.getZeroScore());
		System.out.println("Player2 numZeroScore " + player2.getZeroScore());
		System.out.println("Player1 numExchanges " + player1.getNumExchanges());
		System.out.println("Player2 numExchanges " + player2.getNumExchanges());
		System.out.println("Player1 win " + player1.isWon());
		System.out.println("Player2 win " + player2.isWon());
	}

	// Given a string, returns the player's score after the move
	public static int findScore(String string) {
		string = string.substring(string.indexOf("+"));
		string = string.substring(string.indexOf(" ") + 1);
		return Integer.parseInt(string);
	}

	// Adds all lines after the first 2 that are not notes
	public static void populatePlays() {
		String[] temp = new String[50];
		int i = 0;
		while (reader.hasNextLine()) {
			String string = reader.nextLine();
			if (string.length() != 0 && string.charAt(0) == '>') {
				temp[i] = string;
				i++;
			}
		}
		plays = new String[i];
		for (int x = 0; x < i; x++) {
			plays[x] = temp[x];
		}
	}

	// Given the first 2 lines of the file, names the players
	public static void namePlayers(String p1, String p2) {
		String string = p1;
		string = string.substring(9); // Gets rid of the "#player1 " in the line
		string = string.substring(0, string.indexOf(" ")); // Gets rid of everything after the name with underscores
		player1.setName(string); // Sets the name of player1 equal to the string WITH underscores
		string = p2;
		string = string.substring(9); // Gets rid of the "#player2 " in the line
		string = string.substring(0, string.indexOf(" ")); // Gets rid of everything after the name with underscores
		player2.setName(string); // Sets the name of player1 equal to the string WITH underscores
	}

	// Given the new rack of the player
	public static String findNewLeave(String rack, String tilesPlayed) {
		String newLeave = rack;
		for (int i = 0; i < rack.length(); i++) {
			for (int j = 0; j < tilesPlayed.length(); j++) {
				if (rack.indexOf(tilesPlayed.charAt(j)) != -1) {

				}
			}
		}
		return newLeave;
	}

	// Given a string and an array of tiles, adds the tiles in the string to tiles[]
	public static int[] addToTiles(String string, int[] tiles) {
		for (int i = 0; i < string.length(); i++) {
			char temp = string.charAt(i);
			if (temp != '.') {
				if (temp < 95) { // if not a blank
					tiles[temp - 65]++;
				} else { // if a blank
					tiles[26]++;
				}
			}
		}
		return tiles;
	}

	// Given an array of tiles, prints the tiles
	public static void printTiles(int[] tiles) {
		char currLetter = 65;
		for (int i = 0; i < tiles.length; i++) {
			if (currLetter == 91) {
				System.out.println("?: " + tiles[i]);
				return;
			}
			System.out.println(currLetter + ": " + tiles[i]);
			currLetter++;
		}
	}

	// Fills the bag variable with the proper values for the distribution of each
	// tile
	public static void fillBag() {
		bag = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2 };
	}
	
	public static int numTilesLeft(int[] bag) {
		int retVal = 0;
		for (int i = 0; i < bag.length; i++) {
			retVal += bag[i];
		}
		return retVal;
	}
}
