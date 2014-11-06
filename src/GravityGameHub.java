import java.io.IOException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import netgame.common.Hub;


public class GravityGameHub extends Hub{
	public static GravityGameState state;

	private final static int PORT = 37829;

	public static void main(String[] args) {
		try {
			new GravityGameHub(PORT);
			JOptionPane.showMessageDialog(null, "Server has started.");
		}
		catch (IOException e) {
			System.out.println("Can't create listening socket.  Shutting down.");
		}

	}



	public GravityGameHub(int port) throws IOException {
		super(port);
		state = new GravityGameState();
		setAutoreset(true);
	}

	@Override
	protected void messageReceived(int playerID, Object message){

		if (message instanceof HighScoreMessage) {
			state.playerMap.get(((HighScoreMessage) message).playerID).setScore(((HighScoreMessage) message).score);


		}
		if (message instanceof NewGameMessage) {
			state.playerMap.get(((NewGameMessage)message).getPlayerID()).setNumGamesPlayed(((NewGameMessage) message).getNumGamesPlayed());
		}
		
		sendToAll(new RankingMessage(state.getPlayerStats()));

	}

	@Override
	protected void extraHandshake(int playerID, ObjectInputStream in, 
			ObjectOutputStream out) throws IOException {
		String name = "Unnamed User";
		try {
			name = (String)in.readObject();
			
		}
		catch(ClassNotFoundException e)
		{
			return;
		}
		state.addPlayer(playerID);
		state.setPlayerName(playerID, name);
		
		
		System.out.println(name);
	
	}

	@Override
	protected void playerConnected(int playerID){

		sendToAll(new RankingMessage(state.getPlayerStats()));
	}

	@Override
	protected void playerDisconnected(int playerID) {
		state.removePlayer(playerID);
		sendToAll(new RankingMessage(state.getPlayerStats()));
	}

} 