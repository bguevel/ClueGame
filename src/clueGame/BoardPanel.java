package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int height = this.getHeight();
		int width = this.getWidth();
		int cellHeight = height/24;
		int cellWidth = width/24;
		
		//drawing the cells
		for(int r=0; r< Board.getRow();r++) {
			for(int c=0; c< Board.getColumn();c++) {
				Board.getCell(r,c).draw(cellWidth, cellHeight, g);
			}
		}
		
		//drawing the labels
		for(int r=0; r< Board.getRow();r++) {
			for(int c=0; c< Board.getColumn();c++) {
				BoardCell temp =Board.getCell(r, c);
				if(Board.getCell(r, c).isLabel()) {
					g.setColor(Color.blue);
					if(r ==0) {
						g.drawString(Board.getRoom(Board.getCell(r, c)).getName(),cellWidth*c , 15);
						continue;
					}
					g.drawString(Board.getRoom(Board.getCell(r, c)).getName(),cellWidth*c , cellHeight*r);
				}
			}
		}
		
		//drawing the players
		for(Player player: Board.getPlayerList()) {
			player.draw(cellWidth, cellHeight, g);
		}
		
	}
}