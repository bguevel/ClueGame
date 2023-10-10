package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard{
	public TestBoardCell[][] grid; //I think we likely need to have a board so we can at least look at adj cells
	public int rows=4;
	public int columns=4;
	//private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> visited;
	private Set<TestBoardCell> targets; // seems like these need to be class vars so that the recursion can use them
	public TestBoard() { //needs to create the entire board right?
		grid = new TestBoardCell[rows][columns];
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		for(int r=0; r<rows; r++) { //need double for loop for the dimensions
			for(int c=0; c<columns; c++) {
				TestBoardCell cell = new TestBoardCell(r,c);
				grid[r][c]=cell;
			}
		}// once they're all added we can then make the cell's adj lists
		for(int r=0;r<rows; r++) {
			for(int c=0; c<columns; c++) {
				TestBoardCell cell = grid[r][c];
				if(r-1>=0) { // one above
					cell.addAdjacency(grid[r-1][c]);
				}
				if(r+1<rows) { // one below
					cell.addAdjacency(grid[r+1][c]);
				}
				if(c-1>=0) { // one to the left
					cell.addAdjacency(grid[r][c-1]);
				}
				if(c+1<columns) { // one to the right
					cell.addAdjacency(grid[r][c+1]);
				}
			}
			//adjMtx.put(cell, cell.getAdjList()); // adding the cell and it corresponding adj list to the ajdMtx map
		}
	}
	public void calcTargets(TestBoardCell strtCell, int pathLen) {
		visited.clear();
		targets.clear();
		visited.add(strtCell);
		findAllTargets(strtCell, pathLen);
	}
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	public TestBoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	public void findAllTargets(TestBoardCell strtCell, int pathLen){
		for(TestBoardCell cell:strtCell.getAdjList()) {
			if(visited.contains(cell) || cell.getOccupation()) {
				continue;
			}
			visited.add(cell);
			if(pathLen == 1 || cell.isRoom()) {

				targets.add(cell);

			}else {
				findAllTargets(cell, pathLen-1);
			}
			visited.remove(cell);

		}
		}
}
