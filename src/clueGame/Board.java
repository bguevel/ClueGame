package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

public class Board{
	private String layoutConfigFile;
	private String setUpConfigFile;
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	Map<Character, Room> roomMap;
    private static Board theInstance = new Board();
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
           
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() { // should the exceptions be dealt with in initialize? , also does it call both load functions?
    	try {
    		loadSetupConfig();
    		loadLayoutConfig();
    	}catch(BadConfigFormatException error){
    		System.out.println("Setup Failed");
    	}
    }
    
    public void setConfigFiles(String string, String string2) {
    	this.layoutConfigFile = string;
    	this.setUpConfigFile = string2;
    	
    }
	public BoardCell getCell(int row, int col) {
		// TODO Auto-generated method stub
		return grid[row][col];
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return roomMap.get(c);
	}
	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return roomMap.get(cell.getInitial());
	}
	public void loadSetupConfig() throws BadConfigFormatException {
		FileReader file;
		String temp;
		String[] arr;
		Scanner reader = null;
		Room tempRoom = null;
		this.roomMap = new HashMap<Character, Room>();
		try {
			file = new FileReader(this.setUpConfigFile); // creating a filereader object with the setup file
			reader = new Scanner(file); // passing the scanner the filereader obj
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(reader.hasNext()) {//while there is a line to be taken in
			temp = reader.nextLine(); //read the whole line into temp string
			if(temp.charAt(0)=='/') { // to avoid reading in comments in the file
				continue;
			}
			arr = temp.split(", "); // takes the temp string that was the whole line and splits it based off of commas
			if(arr.length!=3) { // if the length isn't 3 then there must be some info missing in that line
				throw new BadConfigFormatException(this.setUpConfigFile);
			}
			if(arr[0]=="space") { // we don't want spaces in our room map
				continue;
			}
			if(arr[0]!="Room") { // if what we are reading in isn't called a room then there is some bad format
				throw new BadConfigFormatException(this.setUpConfigFile);
			}
			Character temp2 = arr[2].charAt(0); // this is the char we care about to make the rooms
			tempRoom = new Room(arr[1]);
			tempRoom.setName(arr[0]); // sets the name of the room to be the first thing in the array
			this.roomMap.put(temp2, tempRoom); //adds the room to the map
			
		}
	}
	public void loadLayoutConfig() throws BadConfigFormatException {
		FileReader file;
		String temp = null;
		String[] arr;
		BoardCell tempCell;
		Character temp2;
		Scanner reader = null;
		ArrayList<String> tempArr = new ArrayList<String>();
		int rows=0;
		int columns=0;
		int prevCol =0;
		try {
			file = new FileReader(this.layoutConfigFile); //tries opening the files
			reader = new Scanner(this.layoutConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(reader.hasNext()) { // these two loops will be used to calculate the dimensions of the board and make sure that its formatted correctly
			temp = reader.nextLine();
			tempArr.add(temp); // adding each string line to an arraylist of strings
			arr = temp.split(","); // splitting the string into rows
			if(rows==0) { // doing this so we have an initial value to compare the rest of the columns to
				prevCol = arr.length;
				rows++;
				continue;
			}
			columns = arr.length; // getting columns a value one ahead of prevcol
			if(prevCol != columns) { // if the columns aren't equal config format error
				throw new BadConfigFormatException(this.layoutConfigFile);
			}
			prevCol = columns; // keeps prevcol trailing behind
			columns=0;
			rows++; // upadtes row by row
		}
		this.numColumns = prevCol; // if there is no bad config exception any column number should work
		this.numRows = rows; 
		this.grid = new BoardCell[this.numRows][this.numColumns];
		for(int r=0; r<numRows; r++) {
			temp = tempArr.get(r);
			arr = temp.split(","); // splits the string along the commas
			for(int c=0; c<numColumns; c++) {
				temp2 = arr[c].charAt(0); // the character that is stored in that column number 
				grid[r][c] = new BoardCell(r, c, temp2);
				tempCell = grid[r][c];
				if(arr[c].length()>1) {
					if(temp2=='W') {
						tempCell.setDoor(true); // lets the cell know its a door
						if(arr[c].charAt(1)=='>') {
							tempCell.setDoorDirection(DoorDirection.RIGHT);
						}
						if(arr[c].charAt(1)=='<') {
							tempCell.setDoorDirection(DoorDirection.LEFT);
						}
						if(arr[c].charAt(1)=='^') {
							tempCell.setDoorDirection(DoorDirection.UP);
						}
						if(arr[c].charAt(1)=='v') {
							tempCell.setDoorDirection(DoorDirection.DOWN);
						}
					} // should I have a check to throw a bad file config if every room doesn't have a center and a label?
					if(arr[c].charAt(1)=='*'){ // checks if its the center of the room
						tempCell.setRoomCenter(true); // makes sure the cell knows its a room center
						this.roomMap.get(temp2).setCenterCell(tempCell); // find the room in the map and give it its center cell
					}
					if(arr[c].charAt(1)=='#') { // checks if its the room label
						tempCell.setRoomLabel(true); // makes sure the cell knows its a label
						this.roomMap.get(temp2).setLabelCell(tempCell); // find the room in the map and give it its label cell
					}
					if(roomMap.containsKey(arr[c].charAt(1))) { // checks to make sure it is a secret passage
						tempCell.setSecretPassage(arr[c].charAt(1));
					}
				}else if(roomMap.containsKey(temp2)) { // this is for all regular rooms
					tempCell.setRoom(true);
				}else if(temp2 != 'W' || temp2 != 'X') { // we don't need ot do anything with walkway and unused tiles, and if the cell isn't one of those it must be a bad config
					throw new BadConfigFormatException(this.layoutConfigFile);
				}
			}
		}
	}
	
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	
	public void calcTargets(BoardCell strtCell, int pathLen) {
		
	}
	public Set<BoardCell> getTargets() {
		return null;
	}
	
	
}