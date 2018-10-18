package main;

// The player class is a single instance of a game, NOT the totals

public class Player implements PlayerInterface {

	private int[] tiles; // Tiles Drawn
	private String name; // Name of the Player
	private int[] leave; // Current rack of the player
	private int numTurns; // Number of turns
	private int turnover; // Number of tiles played
	private int score; // Total number of points scored
	private int numExchanges; // Total number of exchanges made
	private int zeroScore; // Total number of turns where they score 0
	private int bingos; // Total number of bingos
	private boolean won; // True if won, false if lost
	private int numSevens; // Number of 7 letter bingos
	private int numEights; // Number of 8 letter bingos
	private int numLongBingos; // Number of bingos over 8 letters
	private Player opponent; // The Player Object of the opponent

	private int bingoFirst; // Int giving detail on who bingoed first
	// // 0 if no one bingoed
	// // 1 if player bingoed first and opponent doesn't bingo
	// // 2 if player bingoed first and opponent bingos
	// // 3 if opponent bingoed first and player doesn't bingo
	// // 4 if opponent bingoed first and player also bingos

	// Default Constructor
	public Player() {
		this.tiles = new int[27];
		this.leave = new int[27];
		this.numTurns = 0;
		this.turnover = 0;
		this.score = 0;
		this.numExchanges = 0;
		this.zeroScore = 0;
		this.bingos = 0;
		this.won = false;
		this.numSevens = 0;
		this.numEights = 0;
		this.numLongBingos = 0;
		this.bingoFirst = 0;
		this.opponent = null;
	}

	// Constructor with tiles and name given
	public Player(int[] tiles, String name, int[] rack) {
		this.tiles = tiles;
		this.name = name;
		this.leave = rack;
	}

	@Override
	public int[] getTiles() {
		return tiles;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setTiles(int[] t) {
		this.tiles = t;
	}

	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public int[] getLeave() {
		return leave;
	}

	@Override
	public void setLeave(int[] n) {
		leave = n;
	}

	// TODO recalculates every time
	@Override
	public int getTurnover() {
		int retVal = 0;
		for (int i = 0; i < this.tiles.length; i++) {
			retVal += tiles[i];
		}
		return retVal;
	}

	@Override
	public void setTurnover(int turnover) {
		this.turnover = turnover;
	}

	// Given a string, adds the tiles in the string to tiles[]
	public int[] addToTiles(String string, int[] tiles) {
		for (int i = 0; i < string.length(); i++) {
			char temp = string.charAt(i);
			if (temp != '.') {
				if (temp < 95) { // if not a blank
					tiles[temp - 65]++;
				} else if (temp <= 'z' && temp >= 'a') { // if a blank
					tiles[26]++;
				}
			}
		}
		return tiles;
	}

	// Given an array of tiles, adds them to the tiles array
	public void addBag(int[] bag) {
		for (int i = 0; i < 27; i++) {
			tiles[i] += bag[i];
		}
	}

	// Given the array of tiles, prints them out in order
	public void printTiles(int[] tiles) {
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

	@Override
	public int getNumTurns() {
		return numTurns;
	}

	@Override
	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int points) {
		this.score = points;
	}

	@Override
	public int getNumExchanges() {
		return numExchanges;
	}

	@Override
	public void setNumExchanges(int numExchanges) {
		this.numExchanges = numExchanges;
	}

	@Override
	public int getZeroScore() {
		return zeroScore;
	}

	@Override
	public void setZeroScore(int zeroScore) {
		this.zeroScore = zeroScore;
	}

	@Override
	public int getNumBingos() {
		return bingos;
	}

	@Override
	public void setNumBingos(int bingos) {
		this.bingos = bingos;
	}

	@Override
	public boolean isWon() {
		return score > opponent.getScore();
	}

	@Override
	public void setWon(boolean won) {
		this.won = won;
	}

	public int getNumSevens() {
		return numSevens;
	}

	public void setNumSevens(int numSevens) {
		this.numSevens = numSevens;
	}

	public int getNumEights() {
		return numEights;
	}

	public void setNumEights(int numEights) {
		this.numEights = numEights;
	}

	public int getNumLongBingos() {
		return numLongBingos;
	}

	public void setNumLongBingos(int num) {
		this.numLongBingos = num;
	}

	public int getBingoFirst() {
		return bingoFirst;
	}

	public void setBingoFirst(int num) {
		this.bingoFirst = num;
	}

	public void setOpponent(Player opp) {
		this.opponent = opp;
	}

	public Player getOpponent() {
		return opponent;
	}

}
