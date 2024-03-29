package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.function.BooleanSupplier;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String c, int row, int column, boolean isHuman) {
		super(name, c, row, column, isHuman);
	}

	public void makeAccusation() {
		boolean check;
		Solution guess = new Solution();
		int count=0;
		if(seen.size() == Board.getDeck().size() - 3) {
			for(Card c: Board.getDeck()) {
				check = false;
				for(Card s: seen) {
					if(c == s) {
						check = true;
						continue;
					}
				}
				if(check == false) {
					count++;
					if(c.getType() == CardType.ROOM) {
						guess.setRoom(c);
					} else if(c.getType() == CardType.PERSON) {
						guess.setPerson(c);
					} else if(c.getType() == CardType.WEAPON){
						guess.setWeapon(c);
					}
				}
				if(count==3) {
					break;
				}
			}
			
			if(Board.checkAccusation(guess)) {
				JOptionPane.showMessageDialog(null, "The computer wins. You lose");
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "That was not correct. " + this.getName() + " is out.");
				this.setOut(true);
			}
		}
	}
	
	@Override
	public Solution createSuggestion(Card room) { // not sure if we should be giving the function Room objs or Card objs?
		// if the input needs to be a Room then we could do for(Card c:Board.getDeck()){  if(room.getName() == c.getCardName){ Card Room = c; break;}}
		ArrayList<Card> tempDeck = Board.getDeck();
		ArrayList<Card> notSeenW = new ArrayList<Card>();
		ArrayList<Card> notSeenP = new ArrayList<Card>();
		Random rand = new Random();
		boolean addCard = true;
		for(Card c: tempDeck) {
			for(Card card: this.getSeen()) {
				if(card.getCardName().equals(c.getCardName())) {
					addCard=false;
				}
			}
			if(addCard) {
				if(c.getType() == CardType.PERSON) {
					notSeenP.add(c);					
				} else if (c.getType() == CardType.WEAPON) {
					notSeenW.add(c);
				}
			}
			addCard=true;
/*
 * 
			if(!(this.getSeen().contains(c))) { //this check isn't working so it's adding too much to the lists
				if(c.getType() == CardType.PERSON) {
					notSeenP.add(c);					
				} else if (c.getType() == CardType.WEAPON) {
					notSeenW.add(c);
				}
			}
 * 
 * 
 */
		}
		Card person = notSeenP.get(rand.nextInt(notSeenP.size()));
		Card weapon = notSeenW.get(rand.nextInt(notSeenW.size()));
		
		
		
//		int randNum;
//		boolean foundP =false;
//		boolean foundW =false;
//		while(!foundP && !foundW) {
//			randNum = rand.nextInt((20+1)); // random number between 0 and 20 to get a random card in the deck
//			if(!(this.getSeen().contains(tempDeck.get(randNum))) && tempDeck.get(randNum).getType()==CardType.PERSON && foundP==false) {
//				// check above is to make sure that we are gettiing non seen people for the 
//				person = tempDeck.get(randNum);
//				foundP=true;
//				break;
//			}
//			if(!(this.getSeen().contains(tempDeck.get(randNum))) && tempDeck.get(randNum).getType()==CardType.WEAPON && foundW==false) {
//				// check above is to make sure that we are getting non seen weapons for the suggestion
//				weapon = tempDeck.get(randNum);
//				foundW=true;
//				break;
//			}
//		}
	return new Solution(room, person, weapon); // making the suggestion
}

@Override
public BoardCell selectTargets(Set<BoardCell> targets) {
	ArrayList<BoardCell> actualTargets = new ArrayList<BoardCell>();
	Random rand = new Random();
	boolean roomSeen = false;

	BoardCell[] targs;
	for(BoardCell cell: targets) {
		if(cell.isRoomCenter()) {
			for(Card c: this.getSeen()) {
				if(c.getCardName().equals(Board.getRoom(cell).getName())) { //even when equal it doesn't go into this statement, this might be due to .equals()
					roomSeen = true;
				}
			}
			if(roomSeen == false ) {
				actualTargets.add(cell);
			}
			roomSeen = false;
		}
	}
	if(actualTargets.size()==1) {
		return actualTargets.get(0);
	}

	if(actualTargets.size()>1) {
		return actualTargets.get(rand.nextInt(actualTargets.size()));
	}

	if(actualTargets.size()==0) {
		targs =  targets.toArray(new BoardCell[targets.size()]);
		return targs[rand.nextInt(targs.length)];

	}

	return null;
}

@Override
public void makeMove() {
	// TODO Auto-generated method stub
	
}
}
