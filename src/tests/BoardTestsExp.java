package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	@Test
	void testAdjLC() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	void testAdjRC() {
		TestBoardCell cell = board.getCell(0,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	void testAdjLE() {
		TestBoardCell cell = board.getCell(2,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(3, testList.size());
	}
	@Test
	void testAdjRE() {
		TestBoardCell cell = board.getCell(2,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
		Assert.assertEquals(3, testList.size());
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
	@Test
	void testMaxRoll() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
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
	@Test
	void testRoom() {
		TestBoardCell cell = board.getCell(2, 2);
		board.getCell(2, 3).setRoom(true);
		board.getCell(3, 0).setRoom(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
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
	@Test
	void testOccupied() {
		TestBoardCell cell = board.getCell(2, 0);
		board.getCell(2, 1).setOccupation(true);
		board.getCell(3, 0).setOccupation(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
	}

	//test from bottom edge and roll a 3 with one room and two occupied cells
	@Test
	void testMixed() {
		TestBoardCell cell = board.getCell(3, 1);
		board.getCell(2, 3).setRoom(true);
		board.getCell(3, 2).setOccupation(true);
		board.getCell(1, 0).setOccupation(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 3))); //room
	}

}
