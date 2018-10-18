package main;

public interface PlayerInterface {

	public int[] getTiles();
	public String getName();
	public void setTiles(int[] t);
	public void setName(String n);
	public int[] getLeave();
	public void setLeave(int[] n);
	public int getTurnover();
	public void setTurnover(int turnover);
	public int getNumTurns();
	public void setNumTurns(int numTurns);
	public int getScore();
	public void setScore(int points);
	public int getNumExchanges();
	public void setNumExchanges(int numExchanges);
	public int getZeroScore();
	public void setZeroScore(int zeroScore);
	public int getNumBingos();
	public void setNumBingos(int bingos);
	public boolean isWon();
	public void setWon(boolean won);
//	
//	public void setWon(boolean won) {
//		this.won = won;
//	}
//
//	public int getNumSevens() {
//		return numSevens;
//	}
//
//	public void setNumSevens(int numSevens) {
//		this.numSevens = numSevens;
//	}
//
//	public int getNumEights() {
//		return numEights;
//	}
//
//	public void setNumEights(int numEights) {
//		this.numEights = numEights;
//	}
//
//	public int getNumLongBingos() {
//		return numLongBingos;
//	}
//
//	public void setNumLongBingos(int num) {
//		this.numLongBingos = num;
//	}
//
//	public int getBingoFirst() {
//		return bingoFirst;
//	}
//
//	public void setBingoFirst(int num) {
//		this.bingoFirst = num;
//	}
//
//	public void setOpponent(Player opp) {
//		this.opponent = opp;
//	}
//
//	public Player getOpponent() {
//		return opponent;
//	}

}
