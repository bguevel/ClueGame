package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	private int cellHeight;
	private int cellWidth;
	private boolean targetsDraw =false;
	private Board board;
	private Set<BoardCell> targets;
	public void setTargetsDraw() {
		targetsDraw=true;
	}
	public void setTargets(Set<BoardCell> targs) {
		targets=targs;
	}
	public BoardPanel(Board board) {
		this.board =board;
		addMouseListener(new PlayerSelection());
	}
	private class PlayerSelection implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			System.out.println(e.getX()/cellWidth);
			System.out.println(e.getY()/cellHeight);
			for(BoardCell c: board.getTargets()) {
				System.out.println("row :" +  c.getRow());
				System.out.println();
				if(e.getX()/cellWidth == c.getColumn() && e.getY()/cellHeight == c.getRow()) {
					System.out.println("made it here");
					board.getPlayerList().get(board.getTurn()%6).updatePosition(c.getRow(), c.getColumn());
					repaint();
					board.setMove(true);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "You can't go there");
			// if it gets through the for loop then an invalid target was selected
			
		}


		public void mousePressed(MouseEvent e) {
			
		}


		public void mouseReleased(MouseEvent e) {

			
		}


		public void mouseEntered(MouseEvent e) {

			
		}


		public void mouseExited(MouseEvent e) {

			
		}
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setCellHeight();
		this.setCellWidth();
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
		for(Player player: board.getPlayerList()) {
			player.draw(cellWidth, cellHeight, g);
		}
		targetsDraw = false;
	}
	public void setCellHeight() {
		this.cellHeight = this.getHeight()/24;
	}
	public void setCellWidth() {
		this.cellWidth = this.getWidth()/24;
	}
}