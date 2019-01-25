package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Board {
	
	private Tile[] tiles;
	private BufferedReader reader;
	private int round;
	private Boolean gameOver;
	private String filePath;
	private int totalScore;
	private int[] westRow = {0, 4, 8, 12};
	private int[] northRow = {0, 1, 2, 3};
	private int[] eastRow = {3, 7, 11, 15};
	private int[] southRow = {12, 13, 14, 15};
	
	public Board() {
		
		this.filePath = "/file.txt";
		
		this.gameOver = false;
		
		createBoard();
		
		getFileBoard(this.filePath);
	}
	
	/* creates a board and instantiates tiles */
	
	private void createBoard() {
		
		round = 0;
		
		totalScore = 0;
		
		tiles = new Tile[16];
		
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile();
		}
	}
	
	/* checks every tile from the file.txt */

	
	private Boolean invalidTile(String line,String[] tileContent) {
				
		int lengteArr = tileContent.length;
		Boolean invalidTile = true;
		
		if(lengteArr == 3) {
			for(int i = 0; i < tileContent.length; i++) {
				for (char c : tileContent[i].toCharArray())
			    {
			        if (!Character.isDigit(c)) return true; 
			    }
			}
			
			int y =  Integer.parseInt(tileContent[0]);
			int x =  Integer.parseInt(tileContent[1]);
			
			if(y < 4 && x < 4) {
				invalidTile = false;
			}
		}
		
		return invalidTile;
	}
	
	/* loads the file.txt */

	
	private void getFileBoard(String filePath) {
		
		try{
				
			InputStream fileURL = this.getClass().getResourceAsStream(filePath);
						
			this.reader = new BufferedReader(new InputStreamReader(fileURL, "UTF-8"));
			
			String line;
				
			while ((line = reader.readLine()) != null) {
				
				String[] lineExpl = line.split("\\s+");
				
				if(invalidTile(line, lineExpl)) {
					
					for(int i = 0; i < tiles.length; i++) {
						tiles[i].emptyVal();
					}
					
					placeSevenTiles();
					break;
				}
				
				int y =  Integer.parseInt(lineExpl[0]);
				
				int x =  Integer.parseInt(lineExpl[1]);
				
				
				int pos = (y * 4) + x;
				
				int val = Integer.parseInt(lineExpl[2]);
				
				
								
				tiles[pos].setVal(val);
				
		    }

				
		}catch(IOException  ex){			
			placeSevenTiles(); 	/* if file is missing*/
		}
	}
	
	public int[] getBoard() {
		
		int[] boardInt = new int[16];
		
		for(int i = 0; i < boardInt.length; i++) {
			boardInt[i] = tiles[i].getValue();
		}
		
		return boardInt;
	}
	
	/* moves the board 
	 * 
	 * keynumber determines which direction the board should be moved to
	 * 
	 * 37 = left
	 * 38 = up
	 * 39 = right
	 * 40 = down
	 *  
	 *  */
	
	public void moveBoard(int keyNumber) {
		
		switch(keyNumber) {
			case 37: 
				    moveNW(1, westRow, false);
			        placeRandomTiles(eastRow);
			break;
			case 38:
				    moveNW(4, northRow, false);
		            placeRandomTiles(southRow);
			break;
			
			case 39:
				 moveSE(1, eastRow, false);
			     placeRandomTiles(westRow);
			break;
			
			case 40:
			    moveSE(4, southRow, false);
		        placeRandomTiles(northRow);
			break;
		}
		
		int[] validKeynum = {37, 38, 39, 40};
		
		if(contains(validKeynum,  keyNumber)) {
			this.round++;
		}
		
		checkGameOver();
	}
	
	/* moves the board to left or up
	 * 
	 * the direction determines if you should add the number to loop through the board or not
	 * 
	 * as you can c is that if the directions are up or left you need to add the number in the loop
	 * 
	 * int[] firstRow  the row that shouldnt move because its the first row in the chosen direction
	 * 
	 * Boolean checkGame 
	 * 
	 * I use the function moveNW and moveSE to check if a user has moves left
	 * I use the boolean checkgame to let the function know not to change the board status.
	 * 
	 * 
	 *  
	 *  */
	
	private Boolean moveNW(int num, int[] firstRow, Boolean checkGame) {
		
		Boolean hasMoved = false; 
		
		int score = 0;
		
		for(int i = 0; i < tiles.length; i++) { 
			
			if(!contains(firstRow, i) && tiles[i].getValue() != 0) { // check if the tile is not from the first row and the tile is not empty
				
				if(tiles[i - num].getValue() == 0) {
					
					if(checkGame) {
						return false;
					}
					
					tiles[i - num].setVal(tiles[i].getValue());
					
					tiles[i].emptyVal();
					
					hasMoved = true;
																	// check if the tiles are smaller/similar then/to 2 and not equal 
					
				}else if((tiles[i - num].getValue() <= 2 && tiles[i].getValue() <= 2) && (tiles[i].getValue() != tiles[i - num].getValue())) { 
					
					if(checkGame) {
						return false;
					}
					
					score = score + 1;  // add 1 point because a 2 and a 1 has been merged
					
					tiles[i - num].addVal(tiles[i].getValue());
					
					tiles[i].emptyVal();
					
					hasMoved = true;
																	
																	// check if the tiles are bigger than 2 and similar
					
				}else if(tiles[i - num].getValue() == tiles[i].getValue() && tiles[i].getValue() > 2) {
					
					if(checkGame) {
						return false;
					}
						score = score + tiles[i].getValue();  // add x point because a tile has been multiplied
					
					tiles[i - num].multiplyVal(); 
					
					tiles[i].emptyVal();
					
					hasMoved = true;

				}
			}
			
		}
		
		if(checkGame) {
			return true;
		}else {
			this.addScore(score);
		}
		
		return hasMoved;	
	}
	
	
	// look at function moveNW
	private Boolean moveSE(int num, int[] firstRow, Boolean checkGame) {
		
		Boolean hasMoved = false;
		int score = 0;

		
		for(int i = tiles.length - 1; i > -1; i--) {
			if(!contains(firstRow, i) && tiles[i].getValue() != 0) {

				if(tiles[i + num].getValue() == 0) {
					
					if(checkGame) {
						return false;
					}
					
					tiles[i + num].setVal(tiles[i].getValue());
					
					tiles[i].emptyVal();
					
					hasMoved = true;
				}else if((tiles[i + num].getValue() <= 2 && tiles[i].getValue() <= 2) && (tiles[i].getValue() != tiles[i + num].getValue())) {
					
					if(checkGame) {
						return false;
					}
						score = score + 1; 
					
					tiles[i + num].addVal(tiles[i].getValue());
					
					tiles[i].emptyVal();
					
					hasMoved = true;
				}else if(tiles[i + num].getValue() == tiles[i].getValue() && tiles[i].getValue() > 2) {
					
					if(checkGame) {
						return false;
					}
						score = score + tiles[i].getValue(); 
					
					tiles[i + num].multiplyVal();
					
					tiles[i].emptyVal();
					
					hasMoved = true;
				}
			}
			
		}
		
		if(checkGame) {
			return true;
		}else {
			if(hasMoved) {
				this.addScore(score);
			}
		}
		
		return hasMoved;
		
	}
	
	
	private void placeSevenTiles() {
		
		int tile = 0;
		
		while(tile != 7) { 
			
			int randomNumA = (int)(Math.random() * 15) + 1;
			
			int randomNumB = (int)(Math.random() * 3) + 1;
			
			if(tiles[randomNumA].getValue() == 0) {
				
				tiles[randomNumA].setVal(randomNumB);
				
				tile++;
			}
		}
	}
	
	private void placeRandomTiles(int[] coordinates) {
		
		Boolean emptyTile = false;
		
		// check if the given coordinates are empty
		
		for(int i = 0; i < coordinates.length; i++) { 
			
			if(tiles[coordinates[i]].getValue() == 0) {
				
				emptyTile = true;
				
				break;
			}
		}
		
		// if the gives coordinates are empty place a random tile
		
		while(emptyTile) {
			
			int randomNumA = (int)(Math.random() * 4) + 0;
			int randomNumB = (int)(Math.random() * 3) + 1;
			
			if(tiles[coordinates[randomNumA]].getValue() == 0) {
				
				tiles[coordinates[randomNumA]].setVal(randomNumB);
				break;
				
			}
		}
	}
	
	//help function to check if a array contains a specific value
	
	  private boolean contains( int[] array,  int v) {

	        boolean result = false;

	        for(int i : array){
	        	
	            if(i == v){
	            	
	                result = true;
	                
	                break;
	            }
	        }

	        return result;
	    }
	  
	  // reset board and other game variables 
	  
	  public int[] reset() {
		  
		  	for(int i = 0; i < tiles.length; i++) {
				tiles[i].emptyVal();
			}
		  	
		  	this.round = 0;
		  	
		    this.getFileBoard(this.filePath);
		    
		    this.totalScore = 0;
		    
		    this.gameOver = false;
		  
		  return this.getBoard();
		  
	  }
	  
	  // checks if the player has moves left
	  // if you can see i use the moveNW/moveSE functions
	  
	  private void checkGameOver(){
		  
		  if(moveNW(1, westRow, true)) {
			  
			  if(moveNW(4, northRow, true)) {
				  
				  if(moveSE(1, eastRow, true)) {
					  
					  if(moveSE(4, southRow, true)) {
						  
						  this.gameOver = true;
						  
					  }
				  }
			  }
		  }
	  }
	  
	  public Boolean isGameOver() {
		  return this.gameOver;
	  }
	  
	  public int getCurrentScore() {
		  return totalScore;
	  }
	  
	  private void addScore(int score) {
		  this.totalScore = this.totalScore + score;
	  }
	  
	  public int getRoundNum(){
		  return this.round;
	  }
}
