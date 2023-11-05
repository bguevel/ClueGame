package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
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
		hand = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		// need random logic
		ArrayList<Card> involved = new ArrayList<Card>();
		boolean foundP =false;
		boolean foundR =false;
		boolean foundW =false;
		for(Card c: hand) {
			if(c.equals(suggestion.getPerson()) && foundP == false) { // check to see if person in suggestion is in player's hand
				involved.add(c);
				foundP=true;
			}
			if(c.equals(suggestion.getRoom()) && foundR == false) { // check to see if room in suggestion is in player's hand
				involved.add(c);
				foundR =true;
			}
			if(c.equals(suggestion.getWeapon()) && foundW == false) {// check to see if weapon in suggestion is in player's hand
				involved.add(c);
				foundW=true;
			}
		}
		if(involved.size()>1) { // if there are more than 2 cards from the suggestion present in the player's hand then we return a rand one
			Random rand = new Random();
			return this.hand.get(rand.nextInt(2 +1));
		}else if(involved.size() == 1){ // should only be one thing in the arraylist at idx 0
			return involved.get(0);
		}else { // if there is nothing from the suggestion in the player's hand
			return null;
		}
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void setHand(Card roomCard, Card personCard, Card weaponCard) {
		hand.clear();
		hand.add(roomCard);
		hand.add(personCard);
		hand.add(weaponCard);
	}
	
	public ArrayList<Card> getHand() {
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
