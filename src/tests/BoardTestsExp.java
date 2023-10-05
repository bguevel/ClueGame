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

}
