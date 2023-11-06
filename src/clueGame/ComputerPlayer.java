package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.BooleanSupplier;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String c, int row, int column, boolean isHuman) {
		super(name, c, row, column, isHuman);
	}
	
	public Solution createSuggestion(Card room) { // not sure if we should be giving the function Room objs or Card objs?
		// if the input needs to be a Room then we could do for(Card c:Board.getDeck())  if(room.getName() == c.getCardName) Card Room = c; break;
		ArrayList<Card> tempDeck = Board.getDeck();
		Random rand = new Random();
		int randNum;
		Card person = null;
		Card weapon = null;
		boolean foundP =false;
		boolean foundW =false;
		while(true) {
			randNum =rand.nextInt((20+1)); // random number between 0 and 20 to get a random card in the deck
			for(Card c : this.getSeen()) { // this to make sure that we aren't suggesting a card that we have seen already
				if(!(tempDeck.get(randNum).equals(c)) && tempDeck.get(randNum).getType()==CardType.PERSON && foundP==false) {
					// check above is to make sure that we are gettiing non seen people for the 
					person = tempDeck.get(randNum);
					foundP=true;
					break;
				}
				if(!(tempDeck.get(randNum).equals(c)) && tempDeck.get(randNum).getType()==CardType.WEAPON && foundW==false) {
					// check above is to make sure that we are getting non seen weapons for the suggestion
					weapon = tempDeck.get(randNum);
					foundW=true;
					break;
				}
			}
			if(foundP && foundW) { // to exit the loop
				break;
			}
		}
		return new Solution(room, person, weapon); // making the suggestion
	}
	
	public BoardCell selectTargets() {
		return null;
	}
}
