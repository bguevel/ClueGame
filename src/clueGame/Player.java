package clueGame;

import java.awt.Color;

public abstract class Player {
	private String name;
	private Color color;
	private int row;
	private int column;
	
	public Player(String name, Color c, int row, int column) {
		this.name = name;
		this.color = c;
		this.row = row;
		this.column = column;
	}
	public void updateHand(Card card) {
		
	}
}
