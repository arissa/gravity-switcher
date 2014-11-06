import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import netgame.common.Client;


public class GravityGameWindow extends JPanel implements ActionListener{
	static int panelWidth = 700;
	static int panelHeight = 600;
	boolean isTouchingPlatform; // user can only switch gravity if the runner is touching a platform
	boolean isGameOver;
	boolean gameHasStarted;
	boolean isFalling;
	static final int TIME_PER_FRAME_FOR_MAIN_TIMER	=  10; // milliseconds
	static final int TIME_PER_FRAME_ANIMATION_TIMER = 120;
	int numObstacles = 30;
	int score;
	int highScore = 0;
	int numGamesPlayed = 0;
	boolean gravityIsDownward = true;
	boolean newHighScore;
	private Timer animationTimer;
	private Timer timer;
	int startingXPos = 300;
	int startingYPos = panelHeight/2 - 70;
	Runner userRunner = new Runner(startingXPos, startingYPos);
	ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	Image backgroundImage;
	static int myID;
	private static final int NUM_FRAMES = 10;
	private int frame = 0;
	
	
	static GravityGameWindow gamePanel;
	private final static int PORT = 37829;
	static ScoreboardPanel scoreboardPanel;
	static String username;
	static GravityGameClient connection;

	public static void main(String[] args) {
		String host = JOptionPane.showInputDialog("Enter the hostname or IP address of the server:");
		if (host == null || host.trim().length() == 0)
			return;
		username = JOptionPane.showInputDialog("Enter your username.");

		try {
			gamePanel = new GravityGameWindow(host, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame window = new JFrame("Gravity Game");
		JPanel content = new JPanel();

		gamePanel.setSize(700, 600);
		scoreboardPanel.setBorder(new EmptyBorder(10, 15, 10, 15));


		content.setLayout(new BorderLayout());
		content.add(gamePanel);
		content.add(scoreboardPanel, "East");
		window.add(content);
		window.pack();
		window.setLocation(0, 0);
		window.setSize(1000,600);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}


	public GravityGameWindow(String hostname, int serverPortNumber ) throws IOException{
		connection = new GravityGameClient(hostname, serverPortNumber);
		myID = connection.getID();
		scoreboardPanel = new ScoreboardPanel();
		this.addMouseListener(new MouseAdapter() { 
			public void mouseClicked(MouseEvent evt) {
				if (!gameHasStarted){
					gameHasStarted = true;
				}
				if (isGameOver){
					
					numGamesPlayed++;
					connection.send(new NewGameMessage(myID, numGamesPlayed));

					if (newHighScore){
						connection.send(new HighScoreMessage(myID, highScore));
					}
					resetGame();
					userRunner.reset(startingXPos, startingYPos);
				}

			}
		});   
		
		int score = 0;
		
		backgroundImage = new ImageIcon(this.getClass().getResource("/background.png")).getImage();
		for(int i = 0; i < numObstacles; i++){
			obstacles.add(new Obstacle((int)(Math.random()* panelWidth), (int)(Math.random() * 3), panelHeight));        	
		}

		Color backgroundColor = new Color(175, 238, 238);
		this.setBackground(backgroundColor);
		setFocusable(true);
		this.addKeyListener(new KeyListener());
		timer = new Timer(TIME_PER_FRAME_FOR_MAIN_TIMER, this);
		animationTimer = new Timer(TIME_PER_FRAME_ANIMATION_TIMER, this);
	
		timer.start();
		animationTimer.start();
	}

	 
	


	public void paintComponent(Graphics g) {

		//super.paintComponent(g);
		if (!gameHasStarted){
			intro(g);
			repaint();
		}
		else{
		g.drawImage(backgroundImage, 0, 0, this);
		for(Obstacle o: obstacles){
			o.draw(g);
		}
		userRunner.draw(g, frame, gravityIsDownward);
		g.setColor(Color.BLACK);
		g.drawString("current score: " + score, 50, 150);
		g.drawString("high score: " + highScore, 50, 180);

		if (isGameOver){
			gameOver(g);
		}
		}

	}

	public void incrementScore(){
		score++;
		newHighScore = false;
		if (score > highScore){
			highScore = score;
			newHighScore = true;
		}
	}



	public void resetGame(){
		isGameOver = false;
		
		score = 0;

	}
	public void intro(Graphics g){

		g.drawImage(backgroundImage, 0, 0, this);
		g.setFont(new Font("Sans Serif", Font.BOLD, 40));
		g.drawString("Welcome to the Gravity Game!", panelWidth/2 -300, 250);
		g.setFont(new Font("Sans Serif", Font.BOLD, 20));

		g.drawString("Switch the direction of gravity by pressing any key.", panelWidth/2 -265, 275);
		g.drawString("Note: you can only switch gravity when touching a platform.", panelWidth/2 -300, 300);
		g.drawString("Click to begin.", panelWidth/2 -75, 325);

		g.setFont(new Font("Sans Serif", Font.PLAIN, 25));      


	}

	public void gameOver(Graphics g) {
        g.setFont(new Font("Sans Serif", Font.BOLD, 40));
		g.drawString("Game over!", panelWidth/2 - 125, 250 );
		g.drawString("Click to play again.", panelWidth/2 - 200, 350);
		g.setFont(new Font("Sans Serif", Font.PLAIN, 25));      

	}


	public void checkCollisions(){
		
		isTouchingPlatform = false;
		isFalling = true;
		Rectangle userBounds = userRunner.getBounds(gravityIsDownward);
		for(int i = 0; i < numObstacles; i++){
			Rectangle obstacleBounds = obstacles.get(i).getBounds();
			if(userBounds.intersects(obstacleBounds)){
			
				isTouchingPlatform = true;
				break;
				
			}  
			
			
		
		System.out.println(isTouchingPlatform);
		if (userBounds.getMinY() < 0 || userBounds.getMaxY() > panelHeight){
			isGameOver = true;
		}

		}
	}

	public class KeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent evt) {
			checkCollisions();
			if(isTouchingPlatform){
				userRunner.setFlipping(true);
				gravityIsDownward = !gravityIsDownward;
				userRunner.flipGravity();

			}
		}

		public void keyReleased(KeyEvent evt){
			userRunner.setFlipping(false);
		}
	}



	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == animationTimer){
			frame++;
			if (frame == 10)
				frame = 0;
		}
		
		else {
			
			checkCollisions();

			if (!isGameOver){
				incrementScore();
			}

			userRunner.move(!isTouchingPlatform);
			for(Obstacle o: obstacles){
				o.move();
			}

			repaint();
			for (int i = 0; i < numObstacles; i++ ){
				if (obstacles.get(i).rightEdgeIsOffscreen()) {
					obstacles.remove(i);
					obstacles.add(i, new Obstacle(panelWidth, (int)(Math.random() * 3), panelHeight));
				}
			}
		}
		
	}
	class GravityGameClient extends Client{



		public GravityGameClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
			setAutoreset(true);

		}


		@Override
		protected void extraHandshake(ObjectInputStream in, ObjectOutputStream out) 
				throws IOException {
			try
			{
				out.writeObject(username);
			}
			catch(Exception e)
			{
				System.err.println("Error while getting game information.");
			}
		}



		@Override
		protected void messageReceived(final Object message) {

			if (message instanceof RankingMessage) {
				System.out.println("ranking message received from hub.");
				scoreboardPanel.scoreboardText.setText(((RankingMessage) message).playerRankings);
			}
		}
	}
	
	
	
}



	
	
	
	