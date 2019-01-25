package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	private Tiles[] tiles;
	
	public Board(int[] boardData) {
		
		this.boardSettings();
		
		this.updateBoard(boardData);
	}
	
	private void boardSettings() {
		
		this.setBackground(Color.GRAY);
		
		this.setPreferredSize((new Dimension(400, 300)));
		
		tiles = new Tiles[16];
		
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tiles();
		}
		
		this.setLayout(new GridLayout(4, 4));
	}
	
	// this function is called when the board needs to be repainted after an update
	
	public void updateBoard(int[] boardData) {
				
		for(int i = 0; i < boardData.length; i++) { // loop through board
			
			tiles[i].updateTile(boardData[i]); // update tile backgroundColor and value
			
			this.add(tiles[i]); 
		}
		
		this.revalidate();
		this.repaint();
	}
}
