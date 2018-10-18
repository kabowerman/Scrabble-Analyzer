package main;

// A Game Object holds the data for both players for the game

public class Game {
	
	private Player player1; // Player who went first
	private Player player2; // Player who went second
	private int score; // Total score of the game
	private int turnover; // Total number of tiles seen
	private int[] tiles; // The array of tiles seen
	private int spread; // Spread of the game
		// Positive if player1 wins, Negative if player2 wins
	private boolean player1Win; // True if player1 wins, False if player2 wins OR game is a tie
	private boolean tie; // True if the game is a tie, False if a player won
	private int numBingos; // Number of bingos in the game
	private int numSevens; // Number of 7 letter bingos
	private int numEights; // Number of 8 letter bingos
	private int numTurns; // Number of turns in the game

	public Game(Player p1, Player p2) {
		this.player1 = p1;
		this.player2 = p2;
		this.player1.setOpponent(p2);
		this.player2.setOpponent(p1);
		this.score = p1.getScore() + p2.getScore();
		this.turnover = p1.getTurnover() + p2.getTurnover();
		this.tiles = new int[27];
		addTiles(p1.getTiles());
		addTiles(p2.getTiles());
		this.spread = p1.getScore() - p2.getScore();
		this.player1Win = (p1.getScore() > p2.getScore());
		this.tie = (spread == 0);
		this.numBingos = p1.getNumBingos() + p2.getNumBingos();
		this.numSevens = p1.getNumSevens() + p2.getNumSevens();
		this.numEights = p1.getNumEights() + p2.getNumEights();
		this.numTurns = p1.getNumTurns() + p2.getNumTurns();
	}
	
	//
	public void addTiles(int[] t) {
		for (int i = 0; i < 27; i++) {
			tiles[i] += t[i];
		}
	}
	
	public int[] getTiles() {
		return tiles;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
}
