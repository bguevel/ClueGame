
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
		Set<BoardCell> testList = board.getAdjList(5, 21);
		Iterator<BoardCell> itr = testList.iterator();
		System.out.println(testList.size());
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		System.out.println(board.getCell(5, 23).getInitial());
		

	}

}