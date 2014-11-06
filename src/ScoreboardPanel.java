import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;



import java.util.ArrayList;






import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ScoreboardPanel extends JPanel{

	public int panelWidth = GravityGameWindow.panelWidth-300;
	public int panelHeight = GravityGameWindow.panelHeight;
	public JTextArea scoreboardText;
	//GravityGameState state;



	public ScoreboardPanel(){
		scoreboardText = new JTextArea("", 30,25);
		
		this.add(scoreboardText);
		scoreboardText.setBackground(Color.lightGray);
		//scoreboardText.setFont(new Font);
		scoreboardText.setEditable(false);
		scoreboardText.setCursor(null);
		scoreboardText.setFocusable(false);





	}



	public void paintComponent(Graphics g) {


	}   

}

