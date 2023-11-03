package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public abstract class Player {
	private String name;
	private Color color;
	private int row;
	private int column;
	private ArrayList<Card> hand;
	
	public Player(String name, Color c, int row, int column) {
		this.name = name;
		this.color = c;
		this.row = row;
		this.column = column;
	}
	
	public void updateHand(Card card) {
		
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public abstract Boolean isHuman();
	
}
