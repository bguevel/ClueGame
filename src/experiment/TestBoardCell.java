package experiment;

import java.util.Set;

public class TestBoardCell{
	public int row;
	public int column;
	private Set<TestBoardCell> adjList;
	private boolean room;
	private boolean occupied;
	
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public void setRoom(boolean b){
		room = b;
	}
	
	public boolean isRoom() {
		return room;
	}
	
	public void setOccupation(boolean b) {
		occupied = b;
	}
	
	public boolean getOccupation() {
		return occupied;
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
	}
}