package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tiles extends JPanel{
	
	private int number;
		
	private JLabel labelVal;
	
	public Tiles() {	
		this.setLayout(new GridLayout());
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void updateTile(int value) {
		
		this.removeAll(); // remove all previous components from tile

		this.number = value;
		
		this.setBackground(this.getColorbyVal());   //  backgroundColor determined by value

		if(this.number != 0) { // if the value not is 0/ 0 is no score
			
			labelVal = new JLabel(String.valueOf(this.number));
			labelVal.setFont(new Font("Tahoma", Font.BOLD, 40));
			labelVal.setHorizontalAlignment(JLabel.CENTER);
			labelVal.setVerticalAlignment(JLabel.CENTER);
			labelVal.setVerticalTextPosition(JLabel.CENTER);
			
			this.add(this.labelVal);
		}
		
		this.revalidate();
		this.repaint();
	}
	

	
	// returns a color value determined on value
	
	private Color getColorbyVal() {
	
		Color color;
		
		switch(this.number) {
			case 0: color = new Color(64,64,64);  // if value is 0 BgC = dark grey
			break;
			
			case 1: color = new Color(200,200,255); // if value is 1 BgC = blue
			break;
			
			case 2: color = new Color(255,200,200); // if value is 2 BgC = red
			break;
			
			default : color = new Color(238,238,238); // if value is > 2 BgC = white
			break;
		}
		return color;
	}
	
}
