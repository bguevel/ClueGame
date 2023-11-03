package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;

public class GameSetupTests {
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
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
		assertTrue(board.getPlayer("Prof Strong").isHuman());
		//rest of players are cpu
		assertTrue(board.containsPlayer("Prof CPW"));
		assertFalse(board.getPlayer("Prof CPW").isHuman());
		assertTrue(board.containsPlayer("Prof Kelly"));
		assertFalse(board.getPlayer("Prof Kelly").isHuman());
		assertTrue(board.containsPlayer("Prof Swanson"));
		assertFalse(board.getPlayer("Prof Swanson").isHuman());
		assertTrue(board.containsPlayer("Prof Bridgeman"));
		assertFalse(board.getPlayer("Prof Bridgeman").isHuman());
		assertTrue(board.containsPlayer("Prof Can"));
		assertFalse(board.getPlayer("Prof Can").isHuman());
	}
	
	//testing the colors of the players are correct
	@Test
	public void testPlayerColor() {
		assertEquals(Color.YELLOW, board.getPlayer("Prof Strong").getColor());
		assertEquals(Color.BLUE, board.getPlayer("Prof CPW").getColor());
		assertEquals(Color.GREEN, board.getPlayer("Prof Kelly").getColor());
		assertEquals(Color.RED, board.getPlayer("Prof Swanson").getColor());
		assertEquals(Color.PINK, board.getPlayer("Prof Bridgeman").getColor());
		assertEquals(Color.ORANGE, board.getPlayer("Prof Can").getColor());
	}
	
	//testing the starting locations are correct
	@Test
	public void testPlayerStartLocation() {
		
	}
	
	@Test
	public void testWeaponLabels() {
		assertTrue(board.containsWeapon("Catapult"));
		assertTrue(board.containsWeapon("Flamethrower"));
		assertTrue(board.containsWeapon("Butterknife"));
		assertTrue(board.containsWeapon("Lego"));
		assertTrue(board.containsWeapon("Textbook"));
		assertTrue(board.containsWeapon("Chair"));
	}
	
	
	//testing that the deck was made correctly
	@Test
	public void testDeck() {
		ArrayList<Card> testDeck = new ArrayList<Card>();
		testDeck = board.getDeck();
		assertEquals(21, testDeck.size());
		//do we have to check that every card is in the deck?
	}
	
	//testing that the cards were dealed to the players
	@Test
	public void testDeal() {
		
	}
}
