package clueGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for(int r=0; r< Board.getRow();r++) {
			for(int c=0; c< Board.getColumn();c++) {
				Board.getCell(r,c).draw(g);
			}
		}
	}
}