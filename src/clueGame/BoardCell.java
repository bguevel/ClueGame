package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell{
	int row;
	int column;
	char initial;
	DoorDirection doorDirection;
	boolean roomLabel;
	boolean roomCenter;
	char secretPassage;
	Set<BoardCell> adjList;
	private boolean room;
	private boolean occupied;
	public BoardCell(int row, int col, char init, DoorDirection doorD, boolean roomLbl, boolean roomCen, boolean room, boolean occupied) {
		this.row=row;
		this.column = col;
		adjList = new HashSet<BoardCell>();
		this.initial=init;
		this.doorDirection=doorD;
		this.roomLabel=roomLbl;
		this.roomCenter=roomCen;
		this.room=room;
		this.occupied=occupied;
		
	}
	public void addAdj(BoardCell cell) {
		adjList.add(cell);
	}
	public void setRoom(boolean b){
		room=b;
	}
	public boolean isRoom() {
		return room;
	}
	public void setOccupation(boolean b) {
		occupied =b;
	}
	public boolean getOccupation() {
		return occupied;
	}
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}
	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}

}