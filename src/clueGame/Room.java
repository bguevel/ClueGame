package clueGame;

public class Room{
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String n) {
		this.name = n;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return labelCell;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return centerCell;
	}
	
}