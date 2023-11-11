package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
//		board.clearDeck();
//		board.clearHands();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	//testing that people and weapon data was stored correctly

	//testing each player is in the player list and is the correct type of player, 1 human 5 computers
	@Test
	public void testPlayers(){
		//first player from setup is human
		assertTrue(board.containsPlayer("Prof Strong"));
		assertTrue(board.getPlayer("Prof Strong").getIsHuman());
		//rest of players are cpu
		assertTrue(board.containsPlayer("Prof CPW"));
		assertFalse(board.getPlayer("Prof CPW").getIsHuman());
		assertTrue(board.containsPlayer("Prof Kelly"));
		assertFalse(board.getPlayer("Prof Kelly").getIsHuman());
		assertTrue(board.containsPlayer("Prof Swanson"));
		assertFalse(board.getPlayer("Prof Swanson").getIsHuman());
		assertTrue(board.containsPlayer("Prof Bridgeman"));
		assertFalse(board.getPlayer("Prof Bridgeman").getIsHuman());
		assertTrue(board.containsPlayer("Prof Can"));
		assertFalse(board.getPlayer("Prof Can").getIsHuman());
	}
	
	//testing the colors of the players are correct
	@Test
	public void testPlayerColor() {
		assertEquals("Yellow", board.getPlayer("Prof Strong").getColor());
		assertEquals("Blue", board.getPlayer("Prof CPW").getColor());
		assertEquals("Green", board.getPlayer("Prof Kelly").getColor());
		assertEquals("Red", board.getPlayer("Prof Swanson").getColor());
		assertEquals("Pink", board.getPlayer("Prof Bridgeman").getColor());
		assertEquals("Orange", board.getPlayer("Prof Can").getColor());
	}
	
	//testing the starting locations are correct
	@Test
	public void testPlayerStartLocation() {
		assertEquals(0, board.getPlayer("Prof Strong").getRow());
		assertEquals(0, board.getPlayer("Prof Strong").getColumn());
		assertEquals(0, board.getPlayer("Prof CPW").getRow());
		assertEquals(15, board.getPlayer("Prof CPW").getColumn());
		assertEquals(6, board.getPlayer("Prof Kelly").getRow());
		assertEquals(11, board.getPlayer("Prof Kelly").getColumn());
		assertEquals(15, board.getPlayer("Prof Swanson").getRow());
		assertEquals(7, board.getPlayer("Prof Swanson").getColumn());
		assertEquals(16, board.getPlayer("Prof Bridgeman").getRow());
		assertEquals(20, board.getPlayer("Prof Bridgeman").getColumn());
		assertEquals(13, board.getPlayer("Prof Can").getRow());
		assertEquals(8, board.getPlayer("Prof Can").getColumn());
	}
	
	@Test
	public void testWeaponLabels() {
		ArrayList<String> testLabels = new ArrayList<String>();
		//loop through deck and if weapon add to list
		for(int i=0; i < Board.getDeck().size(); i++) {
			if(Board.getDeck().get(i).getType() == CardType.WEAPON) {
				testLabels.add(Board.getDeck().get(i).getCardName());
			}
		}
		assertTrue(testLabels.contains("Catapult"));
		assertTrue(testLabels.contains("Flamethrower"));
		assertTrue(testLabels.contains("Butterknife"));
		assertTrue(testLabels.contains("Lego"));
		assertTrue(testLabels.contains("Textbook"));
		assertTrue(testLabels.contains("Chair"));
	}
	
	
	//testing that the deck was made correctly
	@Test
	public void testDeck() {
		ArrayList<Card> testDeck = new ArrayList<Card>();
		testDeck = Board.getDeck();
		assertEquals(21, testDeck.size());
	}
	
	//testing that the cards were dealed to the players
	@Test
	public void testDeal() {
		ArrayList<Card> testDeck = new ArrayList<Card>(); 
		//add all cards from player hands
		for(Player p: board.getPlayerList()) {
			ArrayList<Card> pHand =  p.getHand();
			assertEquals(3, pHand.size());
			for(Card c: pHand) {
				testDeck.add(c);
			}
		}
		testDeck.add(board.getTheAnswer().getRoom()); //add the cards from the solution
		testDeck.add(board.getTheAnswer().getPerson());
		testDeck.add(board.getTheAnswer().getWeapon());
		assertEquals(testDeck.size(), Board.getDeck().size()); //all cards dealt
		Set<Card> cardSet = new HashSet<Card>(testDeck);
		assertEquals(cardSet.size(), testDeck.size());
		
	}
}
