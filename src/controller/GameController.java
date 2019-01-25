package controller;

import view.GameView;
import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener  {
	
	private GameView gameView;
	private Board board;
	
	public GameController() {
		
		board = new Board(); //Model
		
		gameView = new GameView(board.getBoard()); // View
		
		gameView.getWindow().addKeyListener(this);
	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {

        if(!board.isGameOver()) {  // if player has moves left

			board.moveBoard(e.getKeyCode());
			
            gameView.updateView(board.getBoard(), board.getCurrentScore(), e.getKeyCode(), board.getRoundNum());
            
            if(board.isGameOver()) { // if player is game over
            	this.handleGameOver();   
           }
            
        }
	}

	   private void handleGameOver() {  
		   
            gameView.getYesB().addActionListener((ActionListener) new ActionListener() { // add listener to yes button (replay game)
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				
    	            gameView.updateView(board.reset(), board.getCurrentScore(), 4, board.getRoundNum()); // reset board and update view
    	            
    	            gameView.closeGameOverModal(); 
    			}
            });
    		
            gameView.getNoB().addActionListener((ActionListener) new ActionListener() { // add listener to no button (close game)
    			@Override
    			public void actionPerformed(ActionEvent e) {
				    System.exit(0);	
    			}	
            });
    		
            gameView.gameOverWindow(board.getCurrentScore()); // show option modal (to replay a game or exit applications)
       }
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
