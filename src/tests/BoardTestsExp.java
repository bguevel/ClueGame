package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	//Tests for adjacency lists
	
	//test from the left corner
	@Test
	void testAdjLC() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	//test from the right corner
	@Test
	void testAdjRC() {
		TestBoardCell cell = board.getCell(0,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertEquals(2, testList.size());
	}
	
	
	//test from one of the left edges
	@Test
	void testAdjLE() {
		TestBoardCell cell = board.getCell(2,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertEquals(3, testList.size());
	}
	
	
	//test from one of the right edges
	@Test
	void testAdjRE() {
		TestBoardCell cell = board.getCell(2,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertEquals(3, testList.size());
	}
	
	//Test for calculating Target
	
	//test from left corner and roll a 1
	@Test
	void testSimple() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	
	//test from left corner and roll a 6
	void testMaxRoll() {
		TestBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	
	//test from middle of board and roll a 3 with two rooms
	void testRoom() {
		TestBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		board.getCell(2, 3).setRoom(true);
		board.getCell(3, 0).setRoom(true);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3))); //room
		assertTrue(targets.contains(board.getCell(3, 0))); //room
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	
	//test from left edge and roll a 3 with two occupied cells
	void testOccupied() {
		TestBoardCell cell = board.getCell(2, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		board.getCell(2, 1).setOccupation(true);
		board.getCell(3, 0).setOccupation(true);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	//test from bottom edge and roll a 3 with one room and two occupied cells
	void testMixed() {
		TestBoardCell cell = board.getCell(3, 1);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		board.getCell(2, 3).setRoom(true);
		board.getCell(3, 2).setOccupation(true);
		board.getCell(1, 0).setOccupation(true);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 3))); //room
	}
	
}
