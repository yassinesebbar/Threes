package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class GameView {
	
	private Footer footer;
	private int WIDTH = 410, HEIGHT = 560;
	private JDialog gameOverView;
	private JFrame window;
	private Board board;
	private JButton yesButton;
	private JButton noButton;
		
	public GameView(int[] boardData) {
		
		backGroundMusic.start();
		
		this.footer = new Footer(0);
		
		this.board = new Board(boardData);
		
		this.setWindow();
		
	}
	
	public void setWindow() {
	
		this.window = new JFrame();
		
		window.setTitle("Current score: 0");
		window.setBackground(new Color(128,128,128));
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
		board.setAlignmentX(Component.CENTER_ALIGNMENT);
		footer.setAlignmentX(Component.CENTER_ALIGNMENT);
		window.add(board);	
		window.add(footer);
		window.setVisible(true);
	}
	
	

	Thread backGroundMusic = new Thread()
	{
	  public void run() 
	  {
		  try
			{	
				InputStream musicURL = this.getClass().getResourceAsStream("/music.wav");
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicURL);
				
				Clip clip = AudioSystem.getClip();

				clip.open(audioInputStream);
				
				clip.loop(Clip.LOOP_CONTINUOUSLY);
					
				
			} catch (Exception ex)
			{}	  
	  }
	};
	
	public void gameOverWindow(int score) {
		
		gameOverView = new JDialog(window, "Game Over", true);
		
		gameOverView.setPreferredSize(new Dimension(300, 150));
		
		gameOverView.setLocationRelativeTo(null);
		
		gameOverView.setLayout(new GridLayout(3,2));
		
		JLabel modalText = new JLabel("<html>You scored " + score + " points<br/>Would you like to play again?</html>" , SwingConstants.CENTER);
		
		gameOverView.getContentPane().add(modalText);
		gameOverView.getContentPane().add(yesButton);
		gameOverView.getContentPane().add(noButton);
		
		gameOverView.pack();
		gameOverView.setVisible(true);
		
	}
	
	public JButton getYesB(){
		 yesButton = new JButton("Yes");
		return this.yesButton;
	}
	
	public JButton getNoB(){
		 noButton = new JButton("No");
		return this.noButton;
	}
	
	
	public JFrame getWindow() {
		return this.window;
	}
	
	public void updateView(int[] boardData, int score, int command, int roundNum) {
		this.window.setTitle("Current score: " + score);
		
		board.updateBoard(boardData);
		
		footer.updateFooter(command, roundNum);
	}
	
	public void closeGameOverModal() {
		gameOverView.removeAll();
		gameOverView.setVisible(false);
	}
	
	
}
