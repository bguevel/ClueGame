package clueGame;

import java.awt.Color;
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
	private boolean highlighted;
	
	public void setHighlight(boolean b) {
		highlighted = b;
	}
	public boolean isHighlighted() {
		return highlighted;
	}
	
	public BoardCell(int row, int col, char init) {
		this.row = row;
		this.column = col;
		adjList = new HashSet<BoardCell>();
		this.initial = init;
	}
	public void draw(int cellWidth, int cellHeight, Graphics g) {
		int xOffset = column * cellWidth;
		int yOffset = row * cellHeight;
		if(highlighted) {
			g.setColor(Color.red);
			if(!door) {
				g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
				g.drawRect(xOffset, yOffset, cellWidth, cellHeight);
				return;
			}
			if(door == true) {
				switch(doorDirection) {
				case DOWN:
					int doorY = yOffset + cellHeight/3 * 2 + cellHeight % 3 + 4;
					g.fillRect(xOffset, doorY, cellWidth, cellHeight/6);
					break;
				case RIGHT:
					int doorX = xOffset + cellWidth/3 * 2 + cellWidth % 3 + 10;
					g.fillRect(doorX, yOffset, cellWidth/6, cellHeight);
					break;
				case UP:
					g.fillRect(xOffset, yOffset, cellWidth, cellHeight/6);
					break;
				case LEFT:
					g.fillRect(xOffset, yOffset, cellWidth/8, cellHeight);
					break;
				default:
					break;
				}
			}
			return;
		}
		switch(initial) {
		case 'W':
			g.setColor(Color.white);
			g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
			g.setColor(Color.black);
			g.drawRect(xOffset, yOffset, cellWidth, cellHeight);
			g.setColor(Color.blue);
			if(door == true) {
				switch(doorDirection) {
				case DOWN:
					int doorY = yOffset + cellHeight/3 * 2 + cellHeight % 3 + 4;
					g.fillRect(xOffset, doorY, cellWidth, cellHeight/6);
					break;
				case RIGHT:
					int doorX = xOffset + cellWidth/3 * 2 + cellWidth % 3 + 10;
					g.fillRect(doorX, yOffset, cellWidth/6, cellHeight);
					break;
				case UP:
					g.fillRect(xOffset, yOffset, cellWidth, cellHeight/6);
					break;
				case LEFT:
					g.fillRect(xOffset, yOffset, cellWidth/8, cellHeight);
					break;
				default:
					break;
				}
			}
			break;
			
		case 'X':
			g.setColor(Color.black);
			g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
			break;
			
		default:
			g.setColor(Color.gray);
			g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
			break;
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