import java.io.Serializable;


public class HighScoreMessage implements Serializable{
	int playerID;	
	int score;

	public HighScoreMessage(int playerID, int score) {
		this.playerID = playerID;
		this.score = score;
	}

	public int getPlayerID(){
		return playerID;
	}
	
}
