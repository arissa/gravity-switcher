import java.io.Serializable;


public class NewGameMessage implements Serializable{

	private int playerID;
	private int numGamesPlayed;
	
	public NewGameMessage(int playerID, int numGamesPlayed) {
		this.playerID = playerID;
		this.numGamesPlayed = numGamesPlayed;
	}
	
	public int getPlayerID(){
		return playerID;
	}
	
	public int getNumGamesPlayed() {
		return numGamesPlayed;
	}

}
