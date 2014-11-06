import java.io.Serializable;


public class RankingMessage implements Serializable{
	String playerRankings;
	
	public RankingMessage(String str) { 
		playerRankings = str; 
	}

}
