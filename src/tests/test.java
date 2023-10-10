
package tests;
import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class test{
	public static void main(String[] args) {
		TestBoard board1 = new TestBoard();
		TestBoardCell cell = board1.getCell(2, 0);
		board1.getCell(2, 1).setOccupation(true);
		board1.getCell(3, 0).setOccupation(true);
		board1.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board1.getTargets();
		
		for(TestBoardCell cel:targets) {
			System.out.println(cel);
		}
}
}