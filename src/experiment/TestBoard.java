package experiment;

import java.util.Set;

public class TestBoard{
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;

	public TestBoard() {
		//populate grid
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				TestBoardCell cell = new TestBoardCell(r, c);
				grid[r][c] = cell;
			}
		}

		//calculate adj lists
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				TestBoardCell cell = grid[r][c];
				if(r-1>=0) { // one above
					cell.addAdjacency(grid[r-1][c]);
				}
				if(r+1<ROWS) { // one below
					cell.addAdjacency(grid[r+1][c]);
				}
				if(c-1>=0) { // one to the left
					cell.addAdjacency(grid[r][c-1]);
				}
				if(c+1<COLS) { // one to the right
					cell.addAdjacency(grid[r][c+1]);
				}
			}
		}


	}

	public void calcTargets(TestBoardCell strtCell, int pathLen) {
		visited.clear();
		targets.clear();
		findAllTargets(strtCell, pathLen);
	}

	public void findAllTargets(TestBoardCell strtCell, int pathLen) {
		for(TestBoardCell cell: strtCell.getAdjList()) {
		if(visited.contains(strtCell) || strtCell.getOccupation()) {
			return;
		}
		visited.add(strtCell);
		if(pathLen == 1 || strtCell.isRoom()) {
			targets.add(strtCell);
		}else {
			findAllTargets(strtCell, pathLen-1);
		}
		visited.remove(strtCell);
	}
	}



	public Set<TestBoardCell> getTargets(){
		return targets;
	}

	public TestBoardCell getCell(int row, int column) {
		return grid[row][column];
	}
}