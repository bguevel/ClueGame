package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the CTLM that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(3, 4);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 4)));
		assertTrue(testList.contains(board.getCell(20, 11)));
		
		// now test the spruce 
		testList = board.getAdjList(20, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(16, 1)));
		
		// one more room, the kafadar, 3 doors and a secret passage
		testList = board.getAdjList(20, 11);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 4)));
		assertTrue(testList.contains(board.getCell(16, 9)));
		assertTrue(testList.contains(board.getCell(16, 11)));
		assertTrue(testList.contains(board.getCell(16, 13)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(21, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 6))); 
		assertTrue(testList.contains(board.getCell(20, 11)));
		assertTrue(testList.contains(board.getCell(20, 8)));
		assertTrue(testList.contains(board.getCell(22, 8)));
		
		testList = board.getAdjList(5, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2, 20)));
		assertTrue(testList.contains(board.getCell(5, 20)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Purple on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(23, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 3)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(15, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 3)));
		assertTrue(testList.contains(board.getCell(15, 1)));
		assertTrue(testList.contains(board.getCell(16, 2)));
		assertTrue(testList.contains(board.getCell(14, 2)));

		// Test adjacent to walkways
		testList = board.getAdjList(22, 8);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 8)));
		assertTrue(testList.contains(board.getCell(23, 8)));

		// Test next to closet
		testList = board.getAdjList(10,14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 14)));
		assertTrue(testList.contains(board.getCell(10, 15)));
		assertTrue(testList.contains(board.getCell(11, 14)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInBrown() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(10, 15)));	

		// test a roll of 3
		board.calcTargets(board.getCell(10, 20), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 14)));
		assertTrue(targets.contains(board.getCell(8, 15)));	
		assertTrue(targets.contains(board.getCell(11, 14)));
		assertTrue(targets.contains(board.getCell(12, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 20), 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(8, 14)));	
		assertTrue(targets.contains(board.getCell(10, 14)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(11, 15)));
	}
	
	@Test
	public void testTargetsInAlderson() {
		// test a roll of 1
		board.calcTargets(board.getCell(20, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(16, 6)));
		assertTrue(targets.contains(board.getCell(20, 8)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(20, 6), 3);
		targets= board.getTargets();
		
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 6), 4);
		targets= board.getTargets();
			
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(10, 15), 1);
		Set<BoardCell> targets= board.getTargets();
		

		// test a roll of 3
		board.calcTargets(board.getCell(10, 15), 3);
		targets= board.getTargets();
		
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 15), 4);
		targets= board.getTargets();
			
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(16, 14), 1);
		Set<BoardCell> targets= board.getTargets();
		
		
		// test a roll of 3
		board.calcTargets(board.getCell(16, 14), 3);
		targets= board.getTargets();
		
		
		// test a roll of 4
		board.calcTargets(board.getCell(16, 14), 4);
		targets= board.getTargets();
		
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 8), 1);
		Set<BoardCell> targets= board.getTargets();
			
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 8), 3);
		targets= board.getTargets();
			
		
		// test a roll of 4
		board.calcTargets(board.getCell(2, 8), 4);
		targets= board.getTargets();
		
	}

	
	//Haven't changed occupied at all yet
	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(8, 15).setOccupied(true);
		board.calcTargets(board.getCell(10, 15), 4);
		board.getCell(8, 15).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
//		assertEquals(13, targets.size()); figure these out with occupied cell
//		assertTrue(targets.contains(board.getCell(14, 2)));
//		assertTrue(targets.contains(board.getCell(15, 9)));
//		assertTrue(targets.contains(board.getCell(11, 5)));	
//		assertFalse( targets.contains( board.getCell(15, 7))) ;
//		assertFalse( targets.contains( board.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(10, 20).setOccupied(true);
		board.getCell(11, 15).setOccupied(true);
		board.calcTargets(board.getCell(10, 20), 1);
		board.getCell(10, 20).setOccupied(false);
		board.getCell(11, 15).setOccupied(false);
		targets= board.getTargets();
//		assertEquals(3, targets.size());
//		assertTrue(targets.contains(board.getCell(7, 17)));	
//		assertTrue(targets.contains(board.getCell(8, 16)));	
//		assertTrue(targets.contains(board.getCell(12, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(14, 22).setOccupied(true);
		board.calcTargets(board.getCell(10, 20), 3);
		board.getCell(14, 22).setOccupied(false);
		targets= board.getTargets();
//		assertEquals(5, targets.size());
//		assertTrue(targets.contains(board.getCell(6, 17)));
//		assertTrue(targets.contains(board.getCell(8, 19)));	
//		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}
