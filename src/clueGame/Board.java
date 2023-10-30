package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board{
	private String layoutConfigFile;
	private String setUpConfigFile;
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
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
	public void initialize() { // exceptions are thrown to this method, also calls both load functions
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}catch(BadConfigFormatException error){
			System.out.println("Setup/Layout Failed");
		}
	}

	//sets up the HashMap for the rooms using the setup files
	public void loadSetupConfig() throws BadConfigFormatException {
		Scanner reader = null;
		this.roomMap = new HashMap<Character, Room>();

		try {
			FileReader file = new FileReader(this.setUpConfigFile); // creating a filereader object with the setup file
			reader = new Scanner(file); // passing the scanner the filereader obj
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(reader.hasNext()) {//while there is a line to be taken in
			String temp = reader.nextLine(); //read the whole line into temp string

			if(temp.charAt(0)=='/') { // to avoid reading in comments in the file
				continue;
			}

			String [] arr = temp.split(", "); // takes the temp string that was the whole line and splits it based off of commas
			if(arr.length!=3) { // if the length isn't 3 then there must be some info missing in that line
				throw new BadConfigFormatException("Bad format or missing information from setup file");
			}

			if(!(arr[0].equals("Room"))) { // if what we are reading in isn't called a room
				if(!(arr[0].equals("Space"))) {	//and it's not a space, throw an exception
					throw new BadConfigFormatException("Bad format or wrong information from setup file");
				}
			}
			String roomName = arr[1]; //this is the name of the room
			// this is the char we care about to make the rooms
			this.roomMap.put(arr[2].charAt(0), new Room(roomName)); //adds the room to the map

		}
		reader.close();
	}

	public void loadLayoutConfig() throws BadConfigFormatException {
		//variables that will be needed multiple times
		String temp = null;
		String[] fileLine;
		BoardCell tempCell;
		Scanner reader = null;
		ArrayList<String> tempArr = new ArrayList<String>();

		int rows=0;
		int columns=0;
		int prevCol =0;

		try {
			FileReader file = new FileReader(this.layoutConfigFile); // creating a filereader object with the setup file
			reader = new Scanner(file); // passing the scanner the filereader obj
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(reader.hasNext()) { // these two loops will be used to calculate the dimensions of the board and make sure that its formatted correctly
			temp = reader.nextLine();
			tempArr.add(temp); // adding each string line to an arraylist of strings
			fileLine = temp.split(","); // splitting the string into rows
			
			if(rows==0) { // doing this so we have an initial value to compare the rest of the columns to
				prevCol = fileLine.length;
				rows++;
				continue;
			}
			
			columns = fileLine.length; // getting columns a value one ahead of prevcol
			if(prevCol != columns) { // if the columns aren't equal config format error
				throw new BadConfigFormatException("Bad format config file");
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
			fileLine = temp.split(","); // splits the string along the commas
			for(int c=0; c<numColumns; c++) {
					// the character that is stored in that column number 
				grid[r][c] = new BoardCell(r, c, fileLine[c].charAt(0));
				tempCell = grid[r][c];
				if(fileLine[c].length()>1) {
					if(fileLine[c].charAt(0)=='W') {
						tempCell.setDoor(true); // lets the cell know its a door
						if(fileLine[c].charAt(1)=='>') {
							tempCell.setDoorDirection(DoorDirection.RIGHT);
						}
						if(fileLine[c].charAt(1)=='<') {
							tempCell.setDoorDirection(DoorDirection.LEFT);
						}
						if(fileLine[c].charAt(1)=='^') {
							tempCell.setDoorDirection(DoorDirection.UP);
						}
						if(fileLine[c].charAt(1)=='v') {
							tempCell.setDoorDirection(DoorDirection.DOWN);
						}
					} // should I have a check to throw a bad file config if every room doesn't have a center and a label?
					if(fileLine[c].charAt(1)=='*'){ // checks if its the center of the room
						tempCell.setRoomCenter(true); // makes sure the cell knows its a room center
						this.roomMap.get(fileLine[c].charAt(0)).setCenterCell(tempCell); // find the room in the map and give it its center cell
					}
					if(fileLine[c].charAt(1)=='#') { // checks if its the room label
						tempCell.setRoomLabel(true); // makes sure the cell knows its a label
						this.roomMap.get(fileLine[c].charAt(0)).setLabelCell(tempCell); // find the room in the map and give it its label cell
					}
					if(roomMap.containsKey(fileLine[c].charAt(1))) { // checks to make sure it is a secret passage
						tempCell.setSecretPassage(fileLine[c].charAt(1));
					}
				}else if(roomMap.containsKey(fileLine[c].charAt(0))) { // this is for all regular rooms
					tempCell.setRoom(true);
				}else if(fileLine[c].charAt(0) != 'W' || fileLine[c].charAt(0) != 'X') { // we don't need ot do anything with walkway and unused tiles, and if the cell isn't one of those it must be a bad config
					throw new BadConfigFormatException("Bad format or incorrect information from config file");
				}
			}
		}
		for(int r=0; r<this.numRows; r++) {
			for(int c=0; c<this.numColumns; c++) {
					if(grid[r][c].isDoorway()) {
						if(c+1<this.numColumns) { // the following 4 outter if statements make sure that other walkway tiles that are adj are in bounds
							if(grid[r][c+1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c+1]);
							}
						}
						if(c-1>=0) {
							if(grid[r][c-1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c-1]);
							}
						}
						if(r+1<this.numRows) {
							if(grid[r+1][c].getInitial()=='W') {
								grid[r][c].addAdj(grid[r+1][c]);
							}
						}
						if(r-1>=0) {
							if(grid[r-1][c].getInitial()=='W') {
								grid[r][c].addAdj(grid[r-1][c]);
							}
						}
						if(grid[r][c].getDoorDirection() == DoorDirection.UP) { //logic for doors
							grid[r][c].addAdj(this.getRoom(grid[r-1][c]).getCenterCell());
							this.getRoom(grid[r-1][c]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.RIGHT) {
							grid[r][c].addAdj(this.getRoom(grid[r][c+1]).getCenterCell());
							this.getRoom(grid[r][c+1]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.LEFT) {
							grid[r][c].addAdj(this.getRoom(grid[r][c-1]).getCenterCell());
							this.getRoom(grid[r][c-1]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.DOWN) {
							grid[r][c].addAdj(this.getRoom(grid[r+1][c]).getCenterCell());
							this.getRoom(grid[r+1][c]).getCenterCell().addAdj(grid[r][c]);
						}
					}
					if(this.roomMap.containsKey(grid[r][c].getSecretPassage())) { //basically I am setting two room centers to be in eachother's adj list
						this.getRoom(grid[r][c].getInitial()).getCenterCell().addAdj(this.getRoom(grid[r][c].getSecretPassage()).getCenterCell());
						this.getRoom(grid[r][c].getSecretPassage()).getCenterCell().addAdj(this.getRoom(grid[r][c]).getCenterCell());
					}
					if(grid[r][c].getInitial() == 'W' && !grid[r][c].isDoorway()) { //logic for dealing with regular walkways same logic for doors
						if(c+1<this.numColumns) {
							if(grid[r][c+1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c+1]);
							}
						}
						if(c-1>=0) {
							if(grid[r][c-1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c-1]);
							}
						}
						if(r+1<this.numRows) {
							if(grid[r+1][c].getInitial()=='W') {
								grid[r][c].addAdj(grid[r+1][c]);
							}
						}
						if(r-1>=0) {
							if(grid[r-1][c].getInitial()=='W') {
								grid[r][c].addAdj(grid[r-1][c]);
							}
						}
					}

			}
		}
	}
	
	public void calcTargets(BoardCell strtCell, int pathLen) { // need to instantiate the arraylists
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.clear();
		targets.clear();
		visited.add(strtCell);
		findAllTargets(strtCell, pathLen);
	}
	
	public void findAllTargets(BoardCell strtCell, int pathLen){ // also from previous test files
		for(BoardCell cell:strtCell.getAdjList()) {
			if(visited.contains(cell) || cell.getOccupation() && !cell.isRoomCenter()) {
				continue;
			}
			visited.add(cell);
			if(pathLen == 1 || cell.isRoomCenter()) { //changed this to room center instead of room. Should this be isroom or isroomcenter? if a tile is a room center should i also make it know its a room?
				
				targets.add(cell);
				
			}else {
				findAllTargets(cell, pathLen-1);
			}
			visited.remove(cell);
			
		}
	}

	//the files are set here with a method, not in a constructor
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setUpConfigFile = string2;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	public Set<BoardCell> getTargets() {
		return this.targets;
	}


}