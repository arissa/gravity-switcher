import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;


  
  
  
public class GravityGameState implements Serializable {
    public TreeMap<Integer, GravityGamePlayer> playerMap = new TreeMap<Integer, GravityGamePlayer>();
      
  
      
  
      
    public ArrayList<GravityGamePlayer> getSortedPlayers()
	{
		ArrayList<GravityGamePlayer> players = new ArrayList<GravityGamePlayer>(playerMap.values());
		Collections.sort(players);
		return players;
	}
      
    public int getNumPlayers(){
    	return playerMap.size();
    }
    public void setPlayerName(int playerID, String name)
	{
		((GravityGamePlayer)playerMap.get(Integer.valueOf(playerID))).setName(name);
	}
      
    public void addPlayer(int playerID){
    	playerMap.put(Integer.valueOf(playerID), new GravityGamePlayer(null) );
    } 
    
    public void removePlayer(int playerID) {
		playerMap.remove(Integer.valueOf(playerID));
    }
      
    public String getPlayerStats()
	{
    	
		String str = "Gravity Game Scoreboard!\n\nPlayer\tScore\t#Games\n\n";
		ArrayList players = getSortedPlayers();
		for(Iterator iterator = players.iterator(); iterator.hasNext();)
		{
			GravityGamePlayer player = (GravityGamePlayer)iterator.next();
			str = (new StringBuilder(String.valueOf(str))).append(player.username).append("\t").append(player.score).append("\t").append(player.numberGamesPlayed).append("\n").toString();
		}

		return str;
	}
      
} 