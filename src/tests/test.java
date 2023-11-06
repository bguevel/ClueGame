
package tests;
import java.util.Iterator;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class test{
	public static void main(String[] args) {
		// Board is singleton, get the only instance
		Board board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		board.calcTargets(board.getCell(5, 3), 3);
		Set<BoardCell> targets = board.getTargets();
		System.out.print(targets);
	}

}