public class GravityGamePlayer implements Comparable {
    String username;
    int score;
    int numberGamesPlayed;
  
  
    public GravityGamePlayer(String username){
        score = 0;
        this.username = username;
        numberGamesPlayed = 0;
    }
    public String getUsername(){
        return username;
    }
  
    public void setName(String newUsername) {
    	username = newUsername;
    }
    public int getScore() {
        return score;
    }
  
    public void setNumGamesPlayed(int num) {
    	numberGamesPlayed = num;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
  
    @Override
    public int compareTo(Object arg0) {
        if (getScore() > ((GravityGamePlayer)arg0).getScore()){
            return -1;
        }
        else if (getScore() < ((GravityGamePlayer)arg0).getScore()){
            return 1;
        }
        else
            return 0;
        }
    
    public String toString()
    {
        return (new StringBuilder("<")).append(username).append(", ").append(score).append(">").toString();
    }
} 