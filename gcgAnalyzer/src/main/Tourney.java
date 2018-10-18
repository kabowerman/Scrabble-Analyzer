package main;

import java.util.ArrayList;

// Tourney object is an array of game objects attributed to a player

public class Tourney {

	private ArrayList<Game> games; // The ArrayList of games
	private ArrayList<String> names; // The ArrayList of names that will be accepted for the player
	private ArrayList<Player> playerStats; // The ArrayList of the player's stats
	private ArrayList<Player> opponentStats; // The ArrayList of the opponent's stats
	private double numGames;
	private double numTurns;
	private double oppTurns;
	private double score;
	private double oppScore;
	private double turnover;
	private double oppTurnover;
	private double numBingos;
	private double oppBingos;
	private int[] tiles;
	private int[] oppTiles;
	private double numWins;
	private double numLosses;
	private String name;
	private double bingoFirst; // Number of games player bingoed first
	private double oppBingoFirst; // Number of games opponent bingoed first
	private double noBingos; // Number of games with no bingos

	// Default constructor
	public Tourney() {
		games = new ArrayList<Game>();
		names = new ArrayList<String>();
		playerStats = new ArrayList<Player>();
		opponentStats = new ArrayList<Player>();
		tiles = new int[27];
		oppTiles = new int[27];
	}

	// Given a Game, adds it to the array of games
	public void addGame(Game g) {
		games.add(g);
	}

	// Given a String, adds it to the list of accepted names
	public void addName(String s) {
		names.add(s);
	}

	// Given a Player adds it to the playerStats ArrayList
	public void addPlayer(Player p) {
		playerStats.add(p);
	}

	// Given a Player adds it to the playerStats ArrayList
	public void addOpponent(Player p) {
		opponentStats.add(p);
	}

	// Iterates through the games ArrayList and adds player objects to the proper
	// ArrayList
	public void fillStats() {
		for (Game g : games) { // Iterate through games
			Player p1 = g.getPlayer1();
			Player p2 = g.getPlayer2();
			for (String s : names) { // Iterate through names
				System.out.println(s);
				if (p1.getName().equalsIgnoreCase(s)) { // If name of player1 matches an accepted name
					playerStats.add(p1);
					opponentStats.add(p2);
				} else if (p2.getName().equalsIgnoreCase(s)) {
					playerStats.add(p2);
					opponentStats.add(p1);
				}
			}
		}
		calculateStats();
	}

	// Fills up the private variables
	public void calculateStats() {
		for (Player p : playerStats) {
			numGames++;
			numTurns += p.getNumTurns();
			oppTurns += p.getOpponent().getNumTurns();
			score += p.getScore();
			oppScore += p.getOpponent().getScore();
			turnover += p.getTurnover();
			oppTurnover += p.getOpponent().getTurnover();
			numBingos += p.getNumBingos();
			oppBingos += p.getOpponent().getNumBingos();
			if (p.getBingoFirst() == 0) {
				noBingos++;
			} else if (p.getBingoFirst() == 1) {
				bingoFirst++;
			} else if (p.getBingoFirst() == 2) {
				oppBingoFirst++;
			}
			if (p.isWon()) {
				numWins++;
			} else {
				numLosses++;
			}
			for (int i = 0; i < p.getTiles().length; i++) {
				tiles[i] += p.getTiles()[i];
				oppTiles[i] += p.getOpponent().getTiles()[i];
			}
		}
	}

	// Prints the averages from the games
	public void printAverages() {
		name = names.get(0);
		System.out.println("Stats for " + name);
		System.out.println("Number of games Played: " + (int) numGames);
		System.out.println("Final Record: " + (int) numWins + " - " + (int) numLosses);
		System.out.println(name + " Win%: " + (double) (100.0 * numWins / (numWins + numLosses)));
		double averageScore = score / numGames;
		System.out.println(name + " Average score: " + averageScore);
		double averageOppScore = oppScore / numGames;
		System.out.println("Total spread: " + (int) (score - oppScore));
		System.out.println("Opponent Average score: " + averageOppScore);
		System.out.println(name + " Number of turns: " + numTurns);
		System.out.println("Opponent Number of turns: " + oppTurns);
		System.out.println(name + " Average Score per Turn: " + (score / numTurns));
		System.out.println("Opponent Average Score per Turn: " + (oppScore / oppTurns));
		System.out.println(name + " Turnover: " + (int) turnover);
		System.out.println(name + " Turnover%: " + (100.0 * turnover / (turnover + oppTurnover)));
		System.out.println("Opponent Turnover: " + (int) oppTurnover);
		System.out.println("Total Turnover: " + (int) (turnover + oppTurnover));
		System.out.println(name + " Blanks: " + tiles[26]);
		System.out.println(name + " Blank percentage: " + ((double) tiles[26] * 100.0 / numGames / 2));
		System.out.println("Opponent Blanks: " + oppTiles[26]);
		System.out.println("Opponent Blank percentage: " + ((double) oppTiles[26] * 100.0 / numGames / 2));
		System.out.println(name + " Bingos: " + (int) numBingos);
		System.out.println("Opponent Bingos: " + (int) oppBingos);
		System.out.println(name + " Bingo First %: " + (100.0 * bingoFirst / numGames));
		System.out.println("Opponent Bingos First %: " + (100.0 * oppBingoFirst / numGames));
		System.out.println("No one bingos %: " + (100.0 * noBingos / numGames));
	}
}
