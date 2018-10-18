package main;

// A move is the line of the text file that denotes a move (AKA the lines that start with >)
public class Move {

	private String string; // The full string of the move
	private Player player; // The player making the move
	private int playNumber; // The current play's number
	private String[] plays; // The array of all plays
	private int[] bag; // Array containing the tiles in the bag
	private int type; // The type of move
	// Valid play putting tiles on board = 0
	// Exchange = 1
	// Phony challenged off = 2
	// Pass = 3
	// Valid play challenged +5 = 4
	// Last play

	public Move(String string, Player player) {
		this.string = string;
		this.player = player;
	}

	public Move(String[] plays, int num, Player player, int[] bag) {
		this.plays = plays;
		this.playNumber = num;
		this.player = player;
		this.bag = bag;
	}

	public int[] getBag() {
		return bag;
	}
	
	public int getType() {
		return type;
	}

	public int getPlayNumber() {
		return playNumber;
	}

	// string is the full line of the move, player is the player Object making the move
	// returns false if at end of game, true otherwise
	public boolean parseMove() {
		int numTotalBingos = player.getNumBingos() + player.getOpponent().getNumBingos();
		string = plays[playNumber];
		string = string.substring(string.indexOf(" ") + 1); // Gets rid of ">(Player_Name) "
		while (string.charAt(0) == ' ') {
			string = string.substring(1);
		}
		String tilesPlayed = string.substring(string.indexOf(" ") + 1); // tilesPlayed is everything after the rack
		if (playNumber == plays.length - 1) { // if last play
			this.type = 5;
			lastPlay();
			return false;
		}
		if (string.contains("(challenge)")) {
			return true;
		}
		if (tilesPlayed.indexOf("-") == -1) { // if they made a move putting tiles on board
			if (plays[playNumber + 1].contains(player.getName())) { // If next move has the name of the player,
																	// something is off
				if (plays[playNumber + 1].contains("-")) { // Phony that is challenged off
					this.type = 2; // type 2 = Phony challenged off
					player.setZeroScore(player.getZeroScore() + 1); // Increment turns where they score 0 by 1
				} else if (plays[playNumber + 1].contains("(challenge)")) { // If challenge +5
					this.type = 4; // type 4 = valid play challenged +5
					tilesPlayed = tilesPlayed.substring(tilesPlayed.indexOf(" ") + 1);
					tilesPlayed = tilesPlayed.substring(0, tilesPlayed.indexOf(" "));					// tilesPlayed now has the string of the play
					player.setTiles(player.addToTiles(tilesPlayed, player.getTiles())); // Add the tiles played to the player's tiles[] array
					removeFromBag(tilesPlayed, bag);
					checkBingo(tilesPlayed);
				} else if (plays[playNumber + 1].contains("(")) { // If end of game
					this.type = 5; // type 5 = end of game, countback on tiles.
					tilesPlayed = tilesPlayed.substring(tilesPlayed.indexOf(" ") + 1);
					tilesPlayed = tilesPlayed.substring(0, tilesPlayed.indexOf(" "));
					// tilesPlayed now has the string of the play
					player.setTiles(player.addToTiles(tilesPlayed, player.getTiles())); // Add the tiles played to the player's tiles[] array
					removeFromBag(tilesPlayed, bag);
					checkBingo(tilesPlayed);
				}
			}
			else { // If normal play putting tiles on board (not challenged)
				this.type = 0;
				tilesPlayed = tilesPlayed.substring(tilesPlayed.indexOf(" ") + 1);
				tilesPlayed = tilesPlayed.substring(0, tilesPlayed.indexOf(" "));				// tilesPlayed now has the string of the play
				player.setTiles(player.addToTiles(tilesPlayed, player.getTiles())); // Add the tiles played to the player's tiles[] array
				removeFromBag(tilesPlayed, bag);
				checkBingo(tilesPlayed);
			}
		}
		
		else { // if exchange, pass
			player.setZeroScore(player.getZeroScore() + 1); // Increment turns where they score 0 by 1
			if (string.charAt(string.indexOf('-') + 1) != ' ') { // if the character after the '-' is not a space, this
																	// is an exchange
				this.type = 1; // type 1 = Exchange
				player.setNumExchanges(player.getNumExchanges() + 1); // Increment number of exchanges by 1
				tilesPlayed = string.substring(0, string.indexOf(' '));
				player.setTiles(player.addToTiles(tilesPlayed, player.getTiles())); // Add the tiles played to the player's tiles[] array
			} else { // if a pass / failed challenge in double, and maybe something else I didn't
						// catch?
				this.type = 3; // type 3 = pass
			}
		}
		player.setNumTurns(player.getNumTurns() + 1); // Increment number of turns of the player
		if (player.getNumBingos() + player.getOpponent().getNumBingos() > numTotalBingos && player.getBingoFirst() == 0) {
			checkBingoFirst();
		}
		return true;
	}
	
	// Checks if bingoFirst
	public void checkBingoFirst() {
		if (player.getNumBingos() > 0 && player.getOpponent().getNumBingos() == 0) {
			player.setBingoFirst(1);
			player.getOpponent().setBingoFirst(2);
		}
		else if (player.getNumBingos() == 0 && player.getOpponent().getNumBingos() > 0) {
			player.setBingoFirst(2);
			player.getOpponent().setBingoFirst(1);
		}
	}
	
	// Returns the actual string of tiles played from the concatenated string of the play
	public String getTilesPlayed(String tilesPlayed) {
		tilesPlayed = tilesPlayed.substring(tilesPlayed.indexOf(" ") + 1);
		return tilesPlayed.substring(0, tilesPlayed.indexOf(" "));
	}
	
	// If the play is the last play of the game
	public void lastPlay() {
		if (plays[playNumber - 2].contains(player.getName())) { // if there was a chall +5
			playNumber -= 3;
			return;
		} else {
			playNumber -= 2;
			return;
		}
	}

	// Given a string and an array of tiles, adds the tiles in the string to tiles[]
	public static int[] removeFromBag(String string, int[] tiles) {
		for (int i = 0; i < string.length(); i++) {
			char temp = string.charAt(i);
			if (temp != '.') {
				if (temp < 95) { // if not a blank
					tiles[temp - 65]--;
				} else { // if a blank
					tiles[26]--;
				}
			}
		}
		return tiles;
	}
	
	// Given the string of tiles played, returns how many tiles were played through
	public static int numTilesThrough(String tilesPlayed) {
		int retVal = 0;
		for (int i = 0; i < tilesPlayed.length(); i++) {
			if (tilesPlayed.charAt(i) == '.') {
				retVal++;
			}
		}
		return retVal;
	}
	
	// Given the tiles played, determines the length of the bingo
	// 0 = not a bingo
	// 7 = 7 letter bingo
	// 8 = 8 letter bingo
	// 9 = 9 letter bingo
	// etc
	public static int bingoLength(String string) {
		if (string.length() <= 6) {
			return 0;
		}
		int lettersThrough = numTilesThrough(string);
		if (string.length() >= 7 && string.length() - 7 == lettersThrough) {
			return string.length();
		}
		return 0;
	}
	
	// Given the tiles played, increments player's bingo related variables
	public void checkBingo(String tiles) {
		int bingo = bingoLength(tiles);
		if (bingo != 0) {
			player.setNumBingos(player.getNumBingos() + 1); // Increment number of bingos by 1
			if (bingo == 7) { // 7 letter bingo
				player.setNumSevens(player.getNumSevens() + 1); // Increment number of 7s by 1
			}
			if (bingo == 8) { // 8 letter bingo
				player.setNumEights(player.getNumEights() + 1); // Increment number of 8s by 1
			}
			if (bingo >= 9) { // Bingo longer than 8 letters
				player.setNumLongBingos(player.getNumLongBingos() + 1); // Increment number of long bingos by 1
			}
		}
	}
}
