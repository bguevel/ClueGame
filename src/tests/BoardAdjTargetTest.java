package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clueGame.Board;

class BoardAdjTargetTest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
	}

}
