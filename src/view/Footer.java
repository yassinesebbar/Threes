package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Footer extends JPanel {

	private JLabel labelRoundnum;
	private JLabel labelCommand;
	private JLabel labelStudent;

	public Footer(int score) {
		this.footerSettings();
	}
	
	private void footerSettings() {
		
		this.setLayout(new GridLayout(3,1));
		this.setBackground(new Color(128,128,128));
		this.setPreferredSize((new Dimension(400, 5)));

		
		labelRoundnum = new JLabel(String.valueOf(0));
		labelRoundnum.setFont(new Font("Tahoma", Font.BOLD, 40));
		labelRoundnum.setHorizontalAlignment(JLabel.CENTER);
		labelRoundnum.setVerticalAlignment(JLabel.CENTER);
		labelRoundnum.setVerticalTextPosition(JLabel.CENTER);
		
		labelCommand = new JLabel(this.getCommand(4));
		labelCommand.setFont(new Font("Tahoma", Font.BOLD, 40));
		labelCommand.setForeground(new Color(231,238,12));
		labelCommand.setHorizontalAlignment(JLabel.CENTER);
		labelCommand.setVerticalAlignment(JLabel.CENTER);
		labelCommand.setVerticalTextPosition(JLabel.CENTER);

		labelStudent = new JLabel("Yassine Sebbar, 2084051");
		labelStudent.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelStudent.setForeground(new Color(73,182,81));
		labelStudent.setHorizontalAlignment(JLabel.CENTER);
		labelStudent.setVerticalAlignment(JLabel.TOP);
		labelStudent.setVerticalTextPosition(JLabel.CENTER);
		
		this.updateFooter(4, 0);
	}
	
	private String getCommand(int command) {
		
		String stringCmd;
		
		switch(command) {
		case 37: stringCmd = "hands LEFT!";
			break;
		case 38: stringCmd = "hands UP!";
			break;
		case 39: stringCmd = "hands RIGHT!";
			break;
		case 40: stringCmd = "hands DOWN!";
			break;
		default: stringCmd = "";
			break;
		}
		
		return stringCmd;
		
	}
	
	public void updateFooter(int command,int roundNum) {
		
		labelCommand.setText(this.getCommand(command));
		labelRoundnum.setText(String.valueOf(roundNum));
		
		this.add(labelRoundnum);
		this.add(labelCommand);
		this.add(labelStudent);
		
		this.repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
