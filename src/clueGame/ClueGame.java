package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	private static Board board;
	private CardDisplay cardPanel;
	private GameControlPanel gamePanel;
	private BoardPanel boardPanel;
	
	public ClueGame() {
		this.setSize(1300, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mines Clue");
		
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		
		boardPanel = new BoardPanel();
		this.add(boardPanel, BorderLayout.CENTER);
		
		cardPanel = new CardDisplay();
		this.add(cardPanel, BorderLayout.EAST);
		
		gamePanel = new GameControlPanel();
		this.add(gamePanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public static void main(String []args) {
		ClueGame game = new ClueGame();
	}
}