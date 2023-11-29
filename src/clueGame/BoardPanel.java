package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardPanel extends JPanel{
	private int cellHeight;
	private int cellWidth;
	private Board board;
	public void setTargetsDraw() {
	}
	public void setTargets(Set<BoardCell> targs) {
	}
	public BoardPanel(Board board) {
		this.board =board;
		addMouseListener(new PlayerSelection());
	}
	private class PlayerSelection implements MouseListener{
		JButton makeSuggestion = null;
		JComboBox<String> weapons = null;
		JComboBox<String> players = null;
		JDialog suggest = null;
		private class ButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==makeSuggestion) {
					Card roomS = null;
        			Card personS = null;
        			Card weaponS = null;
					int row = board.getPlayerList().get(board.getTurn()%6).getRow();
					int column = board.getPlayerList().get(board.getTurn()%6).getColumn();
        			for(Card crd: board.getDeck()) {
        				if(crd.getCardName()==board.getRoom(board.getCell(row, column)).getName()){
        					roomS = crd;
        				}
        				if(crd.getCardName()==players.getSelectedItem()) {
        					personS = crd;
        				}
        				if(crd.getCardName()==weapons.getSelectedItem()) {
        					weaponS=crd;
        				}
        			}
        			Solution suggestd = new Solution(roomS, personS, weaponS);
                    board.getPlayerList().get(board.getTurn()%6).updateSeen(board.handleSuggestion(suggestd, board.getPlayerList().get(board.getTurn()%6).getName()));
                    suggest.dispose();
					board.setMove(true); // allows for the player to move on to next move
				}
				
			}
			
		}
		public void mouseClicked(MouseEvent e) {
			for(BoardCell c: board.getTargets()) {
				if(e.getX()/cellWidth == c.getColumn() && e.getY()/cellHeight == c.getRow()) { // divides the x by the width of the drawn cell and y by height of drawn cell

					board.getPlayerList().get(board.getTurn()%6).updatePosition(c.getRow(), c.getColumn()); // updates position on board
					repaint();
					int row = board.getPlayerList().get(board.getTurn()%6).getRow();
					int column = board.getPlayerList().get(board.getTurn()%6).getColumn();
					// huge check to see if it matches the room
					if(board.getCell(c.getRow(), c.getColumn()).isRoomCenter()) {
                        players = new JComboBox<String>();
                        for(Player p: board.getPlayerList()) {
                            players.addItem(p.getName());
                        }
                        weapons = new JComboBox<String>();
                        for(Card w: board.getDeck()) {
                            if(w.getType() == CardType.WEAPON) {
                                weapons.addItem(w.getCardName());
                            }
                        }
                        suggest = new JDialog(board.getGame());
                        suggest.setLayout(new GridLayout(0,1));
                        suggest.setSize(200, 300);
                        JTextField room = new JTextField();
                        room.setText("Room: "+board.getRoom(c).getName()); //how to get name of room they are in
                        suggest.add(room);
                        suggest.add(players);
                        suggest.add(weapons);
                        ButtonListener listen = new ButtonListener();
                        makeSuggestion = new JButton("Make Suggestion");
                        makeSuggestion.addActionListener(listen);
                        suggest.add(makeSuggestion);
                        suggest.setVisible(true);
                         
					return;
				}
					board.setMove(true);
					return;
			}
			}
			// if it gets through the for loop then an invalid target was selected
			JOptionPane.showMessageDialog(null, "You can't go there");
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
	}
	public void setCellHeight() {
		this.cellHeight = this.getHeight()/24;
	}
	public void setCellWidth() {
		this.cellWidth = this.getWidth()/24;
	}
}