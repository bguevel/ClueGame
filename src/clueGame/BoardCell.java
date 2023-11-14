package clueGame;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell{
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean room;
	private boolean occupied;
	private boolean door;

	
	public BoardCell(int row, int col, char init) {
		this.row = row;
		this.column = col;
		adjList = new HashSet<BoardCell>();
		this.initial = init;
	}
	public void draw(Graphics g) {
		
		if(this.getInitial()=='W') {
			
		}
	}

	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}
	public void addAdj(BoardCell cell) {
		adjList.add(cell);
	}
	public void setRoom(boolean b){
		room = b;
	}
	public boolean isRoom() {
		return room;
	}
	public void setOccupied(boolean b) {
		occupied = b;
	}
	public boolean getOccupation() {
		return occupied;
	}
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public boolean isDoorway() {
		return door;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	public void setSecretPassage(char c) {
		this.secretPassage=c;
	}
	public void setDoor(boolean door) {
		this.door = door;
	}
	public char getInitial() {
		return initial;
	}
	@Override
	public String toString() {
		return "BoardCell: " + row + ", " + column;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

}