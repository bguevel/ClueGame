package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int column;
	private ArrayList<Card> hand;
	private boolean isHuman;
	
	public Player(String name, String color, int row, int column, boolean isHuman) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		this.isHuman = isHuman;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public  Boolean getIsHuman() {
		return isHuman;
	}
	
}
