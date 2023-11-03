package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

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
	@Test
	public void testPeopleLabels(){
		
	}
	
	@Test
	public void testPeopleColor() {
		
	}
	
	@Test
	public void testPeopleStartLocation() {
		
	}
	
	@Test
	public void testWeaponLabels() {
		
	}
	
	//testing that the players were made, 1 person and 5 computers
	@Test
	public void testGamePlayers() {
		
	}
	
	//testing that the deck was made correctly
	@Test
	public void testDeck() {
		
	}
	
	//testing that the cards were dealed to the players
	@Test
	public void testDeal() {
		
	}
}
