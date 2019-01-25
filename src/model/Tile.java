package model;

public class Tile {
	
	private int value;
	
	public Tile() {
		this.value = 0;
	}
	
	public void emptyVal() {
		this.value = 0;
	}
	
	// multiplies its own value
	public void multiplyVal() {
		
		if(this.value >= 2) {
			
			this.value = this.value * 2;
		}
		
	}
	
	public void addVal(int value) {
		
		if(this.value <= 2) {
			
			this.value = this.value + value;
			
		}
		
	}
	
	public int getValue() {
		return this.value;
	}
	
	
	public void setVal(int value) {
		
		if(this.value == 0) {
			
			this.value = value;
			
		}
	}
	
}
