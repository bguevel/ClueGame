package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	private static Board board;
	private CardDisplay cardPanel;
	private GameControlPanel gamePanel;
	private BoardPanel boardPanel;
	public void setTurn(String name, String color) {
		gamePanel.setTurn(name, color);
	}
	public void setRoll(int roll) {
		gamePanel.setRoll(roll);
	}
	public void repaintEverything() {
		cardPanel.updateCards();
		boardPanel.repaint();
		gamePanel.repaint();
		gamePanel.revalidate();
		cardPanel.repaint();
		cardPanel.revalidate();
	}
	public ClueGame(Board board) {
		this.board=board;
		this.setSize(1300, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mines Clue");
		
		//board = Board.getInstance();
		//board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		//board.initialize();
		System.out.println(board);
		boardPanel = new BoardPanel(board);
		this.add(boardPanel, BorderLayout.CENTER);
		
		cardPanel = new CardDisplay(board);
		cardPanel.updateCards();
		this.add(cardPanel, BorderLayout.EAST);
		
		gamePanel = new GameControlPanel(board);
		this.add(gamePanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	/*
	 * 	public static void main(String []args) {
		ClueGame game = new ClueGame(board);
	}
	 * 
	 */

}