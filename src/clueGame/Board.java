package clueGame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Board{
	private static int turn = -1;
	private String layoutConfigFile;
	private String setUpConfigFile;
	private static BoardCell[][] grid;
	private static int numRows;
	private static int numColumns;
	private static Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private static Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private static ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private static Solution theAnswer; //Initialize this with 3 cards before dealing the rest
	// make a new hashset of cards for the deck and other card functionality
	// make some array/list of players
	private GameControlPanel cntrlPanel = new GameControlPanel(theInstance);
	private BoardPanel boardPanel = new BoardPanel(theInstance);
	private CardDisplay crdDisplay = new CardDisplay(theInstance);
	static ClueGame game;
	private boolean madeMove = true;
	private boolean suggestion;
	public void clearSeen() {
		for(Player p:this.getPlayerList()) {
			p.clearSeen();
		}
	}
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() { // exceptions are thrown to this method, also calls both load functions
		clearDeck();
		clearHands();
		clearSeen();
		players.clear();
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}catch(BadConfigFormatException error){
			System.out.println("Setup/Layout Failed");
		}
	}
	public ClueGame getGame() {
		return this.game;
	}
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		game = new ClueGame(board);
		board.nextPlayer();
	}
	public int getTurn() {
		return Board.turn;
	}
	public void setMove(boolean b) {
		this.madeMove=b;
	}
	public void setCreateSuggestion(boolean b) {
		this.suggestion=b;
	}
	public void nextPlayer() {
		this.setCreateSuggestion(false);
		if(!this.madeMove) {
			JOptionPane.showMessageDialog(null, "Please move/finish suggestion");
			// print out error that they haven't preformed move (maybe a splash screen displaying the error)
			return;
		}
		for(int r=0; r<Board.numRows;r++) {
			for(int c=0; c<Board.numColumns;c++) {
				Board.getCell(r, c).setHighlight(false);
			}
		}
		
		//player index
		turn=turn+1;
		String name = this.getPlayerList().get(turn%6).getName();
		String color = this.getPlayerList().get(turn%6).getColor();
		
		game.setTurn(name, color); // updates the turn panel
		Random roll = new Random();
		int rollD = roll.nextInt(6)+1;
		
		game.setRoll(rollD); // updates the roll panel
		calcTargets(Board.getCell(this.getPlayerList().get(turn%6).getRow(), this.getPlayerList().get(turn%6).getColumn()), rollD);
		if(this.getPlayerList().get(turn%6).getIsHuman()) {
			for(BoardCell c:getTargets()) {
				c.setHighlight(true);
			}
			this.madeMove=false;
			// need to check that player's turn is over
			
			
			
		}else {
			BoardCell target = this.getPlayerList().get(turn%6).selectTargets(getTargets()); // gets a valid target for the AI
			this.getPlayerList().get(turn%6).updatePosition(target.getRow(), target.getColumn()); // updates the position of the AI in the board
			if(this.getRoom(target).getCenterCell()==target) { // if the target is a room we want to create a suggestion
				for(Card c: this.getDeck()) { // get the room card that the computer is in
					if(c.getCardName().equals(this.getRoom(target).getName())) { // create and handle the suggestion
						this.getPlayerList().get(turn%6).updateSeen(handleSuggestion(this.getPlayerList().get(turn%6).createSuggestion(c), this.getPlayerList().get(turn%6).getName()));
						break;
					}
				}
			}
		}
		game.repaintEverything();
		
	}

	//sets up the HashMap for the rooms using the setup files
	public void loadSetupConfig() throws BadConfigFormatException {
		Scanner reader = null;
		Board.roomMap = new HashMap<Character, Room>();
		String fileLine = null;
		boolean humanP =false;

		try {
			FileReader file = new FileReader(this.setUpConfigFile); // creating a filereader object with the setup file
			reader = new Scanner(file); // passing the scanner the filereader obj
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(reader.hasNext()) {//while there is a line to be taken in
			fileLine = reader.nextLine(); //read the whole line into temp string

			if(fileLine.charAt(0)=='/') { // to avoid reading in comments in the file
				continue;
			}

			String [] arr = fileLine.split(", "); // takes the temp string that was the whole line and splits it based off of commas
			if(arr.length!=3 && ((arr[0].equals("Space") || arr[0].equals("Room")))) { // if the length isn't 3 or other conditions then there must be some info missing in that line
				throw new BadConfigFormatException("Bad format or missing information from setup file");
			}
			if(arr.length!=5 && arr[0].equals("Player")) {
				throw new BadConfigFormatException("Bad format or missing information from setup file");
			}
			if((arr.length!=2 && arr[0].equals("Weapon"))) {
				throw new BadConfigFormatException("Bad format or missing information from setup file");
			}

			if(!(arr[0].equals("Room"))) { // if what we are reading in isn't called a room
				if(!(arr[0].equals("Space")) && !(arr[0].equals("Player")) && !(arr[0].equals("Weapon"))) {	//and it's not a space, throw an exception
					throw new BadConfigFormatException("Bad format or wrong information from setup file");
				}
				if((arr[0].equals("Player"))) { // logic for initializing the players that are present in the file
					deck.add(new Card(arr[1] , CardType.PERSON)); // add player card
					// add the player to the players list
					if(humanP==false) {
						Player tempPlayer = new HumanPlayer(arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), true);
						players.add(tempPlayer); // Integer.parseInt(string) gives a integer from the string
					}else {
						Player tempPlayer = new ComputerPlayer(arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), false);
						players.add(tempPlayer);
					}
					humanP =true;
					continue; // so that we move on to a new interation of the loop
				}
				if((arr[0].equals("Weapon"))) {
					// add weapon to the deck
					deck.add(new Card(arr[1], CardType.WEAPON));
					continue; // so that we move on to a new interation of the loop
				}
				
			}
			if(!(arr[0].equals("Space"))){
				deck.add(new Card(arr[1], CardType.ROOM)); // add room card to the deck not spaces
			}
			String roomName = arr[1]; //this is the name of the room
			// this is the char we care about to make the rooms
			// make the room a card
			Board.roomMap.put(arr[2].charAt(0), new Room(roomName)); //adds the room to the map

		}
		reader.close();
		if(Board.deck.size()==21) {
			this.dealCards();
		}
	}
	
	public void dealCards() {
	
		Collections.shuffle(deck); // this line shuffles the deck
		Card[] soln = new Card[3]; // array of size 3 for the solution cards
		for(Card c:deck) {
			if(soln[0]!=null && soln[1]!=null && soln[2]!=null) { // exit loop if solution has all necessary cards
				break;
			}
			if(c.getType()==CardType.PERSON && soln[0]==null) { // the following three checks are to make sure that the solution only has one type of each card
				soln[0]=c;
			}
			if(c.getType()==CardType.ROOM && soln[1]==null) {
				soln[1]=c;
			}
			if(c.getType()==CardType.WEAPON && soln[2]==null) {
				soln[2]=c;
			}
		}
		this.theAnswer = new Solution(soln[1], soln[2], soln[0]);
		int dealCount = 0;
		for(int i=0; i<21; i++) {
			if(deck.get(i) == soln[0] || deck.get(i) == soln[1] || deck.get(i) == soln[2]) { // this is a check to make sure that we aren't redistributing the cards involved in the solution
				continue;
			}else {
				players.get(dealCount/3).updateHand(deck.get(i)); // doing int division so that it gives each player 3 cards at a time since they are shuffled it doesn't matter how they are distributed
				dealCount++;
			}
		}
	}

	private void getAdjs() {
		for(int r=0; r<Board.numRows; r++) {
			for(int c=0; c<Board.numColumns; c++) {
					if(grid[r][c].isDoorway()) {
						if(c+1<Board.numColumns) { // the following 4 outter if statements make sure that other walkway tiles that are adj are in bounds
							if(grid[r][c+1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c+1]);
							}
						}
						if(c-1>=0) {
							if(grid[r][c-1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c-1]);
							}
						}
						if(r+1<Board.numRows) {
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
							grid[r][c].addAdj(Board.getRoom(grid[r-1][c]).getCenterCell());
							Board.getRoom(grid[r-1][c]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.RIGHT) {
							grid[r][c].addAdj(Board.getRoom(grid[r][c+1]).getCenterCell());
							Board.getRoom(grid[r][c+1]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.LEFT) {
							grid[r][c].addAdj(Board.getRoom(grid[r][c-1]).getCenterCell());
							Board.getRoom(grid[r][c-1]).getCenterCell().addAdj(grid[r][c]);
						}else if(grid[r][c].getDoorDirection() == DoorDirection.DOWN) {
							grid[r][c].addAdj(Board.getRoom(grid[r+1][c]).getCenterCell());
							Board.getRoom(grid[r+1][c]).getCenterCell().addAdj(grid[r][c]);
						}
					}
					if(Board.roomMap.containsKey(grid[r][c].getSecretPassage())) { //basically I am setting two room centers to be in eachother's adj list
						this.getRoom(grid[r][c].getInitial()).getCenterCell().addAdj(this.getRoom(grid[r][c].getSecretPassage()).getCenterCell());
						this.getRoom(grid[r][c].getSecretPassage()).getCenterCell().addAdj(Board.getRoom(grid[r][c]).getCenterCell());
					}
					if(grid[r][c].getInitial() == 'W' && !grid[r][c].isDoorway()) { //logic for dealing with regular walkways same logic for doors
						if(c+1<Board.numColumns) {
							if(grid[r][c+1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c+1]);
							}
						}
						if(c-1>=0) {
							if(grid[r][c-1].getInitial()=='W') {
								grid[r][c].addAdj(grid[r][c-1]);
							}
						}
						if(r+1<Board.numRows) {
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
	
	private ArrayList<String> loadBoard() throws BadConfigFormatException{
		Scanner reader = null;
		ArrayList<String> fileLines = new ArrayList<String>();
		String fileLine = null;
		String[] splitFileLine;
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
			fileLine = reader.nextLine();
			fileLines.add(fileLine); // adding each string line to an arraylist of strings
			splitFileLine = fileLine.split(","); // splitting the string into rows
			
			if(rows==0) { // doing this so we have an initial value to compare the rest of the columns to
				prevCol = splitFileLine.length;
				rows++;
				continue;
			}
			
			columns = splitFileLine.length; // getting columns a value one ahead of prevcol
			if(prevCol != columns) { // if the columns aren't equal config format error
				throw new BadConfigFormatException("Bad format config file");
			}
			
			prevCol = columns; // keeps prevcol trailing behind
			columns=0;
			rows++; // upadtes row by row
		}

		Board.numColumns = prevCol; // if there is no bad config exception any column number should work
		Board.numRows = rows; 
		Board.grid = new BoardCell[Board.numRows][Board.numColumns];
		return fileLines;
	}
	
	private void makeGrid(ArrayList<String> fileLines) throws BadConfigFormatException {
		String fileLine = null;
		String[] splitFileLine;
		BoardCell tempCell;
		for(int r=0; r<numRows; r++) {
			fileLine = fileLines.get(r);
			splitFileLine = fileLine.split(","); // splits the string along the commas
			for(int c=0; c<numColumns; c++) {
					// the character that is stored in that column number 
				grid[r][c] = new BoardCell(r, c, splitFileLine[c].charAt(0));
				tempCell = grid[r][c];
				if(splitFileLine[c].length()>1) {
					if(splitFileLine[c].charAt(0)=='W') {
						tempCell.setDoor(true); // lets the cell know its a door
						if(splitFileLine[c].charAt(1)=='>') {
							tempCell.setDoorDirection(DoorDirection.RIGHT);
						}
						if(splitFileLine[c].charAt(1)=='<') {
							tempCell.setDoorDirection(DoorDirection.LEFT);
						}
						if(splitFileLine[c].charAt(1)=='^') {
							tempCell.setDoorDirection(DoorDirection.UP);
						}
						if(splitFileLine[c].charAt(1)=='v') {
							tempCell.setDoorDirection(DoorDirection.DOWN);
						}
					} // should I have a check to throw a bad file config if every room doesn't have a center and a label?
					if(splitFileLine[c].charAt(1)=='*'){ // checks if its the center of the room
						tempCell.setRoomCenter(true); // makes sure the cell knows its a room center
						Board.roomMap.get(splitFileLine[c].charAt(0)).setCenterCell(tempCell); // find the room in the map and give it its center cell
					}
					if(splitFileLine[c].charAt(1)=='#') { // checks if its the room label
						tempCell.setRoomLabel(true); // makes sure the cell knows its a label
						Board.roomMap.get(splitFileLine[c].charAt(0)).setLabelCell(tempCell); // find the room in the map and give it its label cell
					}
					if(roomMap.containsKey(splitFileLine[c].charAt(1))) { // checks to make sure it is a secret passage
						tempCell.setSecretPassage(splitFileLine[c].charAt(1));
					}
				}else if(roomMap.containsKey(splitFileLine[c].charAt(0))) { // this is for all regular rooms
					tempCell.setRoom(true);
				}else if(splitFileLine[c].charAt(0) != 'W' || splitFileLine[c].charAt(0) != 'X') { // we don't need ot do anything with walkway and unused tiles, and if the cell isn't one of those it must be a bad config
					throw new BadConfigFormatException("Bad format or incorrect information from config file");
				}
			}
		}
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException {
		//variables that will be needed multiple times
		ArrayList<String> fileLines = new ArrayList<String>();
		fileLines = this.loadBoard();
		this.makeGrid(fileLines);
		this.getAdjs();
		
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
	
	public static boolean checkAccusation(Solution accusation) {
		if((accusation.getRoom() == theAnswer.getRoom()) && (accusation.getPerson() == theAnswer.getPerson()) && (accusation.getWeapon() == theAnswer.getWeapon())) {
			return true;
		}
		return false;
	}
	
	public Card handleSuggestion(Solution suggestion, String player) {
		
		int row = this.getPlayer(player).getRow();
		int column = this.getPlayer(player).getColumn();
		for(Player p: this.getPlayerList()) { // moves the player that is the card to the room of the suggestion
			if(p.getName().equals(suggestion.getPerson().getCardName())) {
				p.updatePosition(row, column);
				game.repaintEverything();
			}
		}
		
		GameControlPanel.setGuess(suggestion.getPerson().getCardName() + ", " + suggestion.getRoom().getCardName() + ", " + suggestion.getWeapon().getCardName());
		
		
		for(Player p:this.players) {
			if(p.disproveSuggestion(suggestion)!=null) {
				if(player.equals("Prof Strong")) {
					GameControlPanel.setGuessResult("Disproven by: " + p.getName() +" With: " + p.disproveSuggestion(suggestion).getCardName());
					JOptionPane.showMessageDialog(null, "Disproven by: " + p.getName() +" With: " + p.disproveSuggestion(suggestion).getCardName());
				}else {
					GameControlPanel.setGuessResult("Disproven by: " + p.getName());
					JOptionPane.showMessageDialog(null, "Disproven by: " + p.getName());
				}
				// check if player is human or not
				// display correct splash screen
				return p.disproveSuggestion(suggestion);
			}
		}
		// guess panel
		GameControlPanel.setGuessResult("Not Disproven");
		JOptionPane.showMessageDialog(null, "Not Disproven");
		return null;
	}
	
	
	//the files are set here with a method, not in a constructor
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setUpConfigFile = string2;
	}
	
	public Boolean containsPlayer(String name) {
		for(int i=0; i<6; i++) {
			if(this.players.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Boolean containsWeapon(String string) {
		for(Card c: deck) {
			if(c.getType() == CardType.WEAPON) {
				if(c.getCardName() == string) {
					return true;
				}
			}
		}
		return false;
	}

	public static BoardCell getCell(int row, int col) {
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

	public static Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}


	public Player getPlayer(String name) {
		for(int i=0; i<6; i++) {
			if(this.players.get(i).getName().equals(name)) {
				return this.players.get(i);
			}
		}
		return null;
	}


	public static ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Player> getPlayerList(){
		return players;
	}

	public Solution getTheAnswer() {
		return theAnswer;
	}
	
	public void setTheAnswer(Card room, Card person, Card weapon) {
		Solution answer = new Solution(room, person, weapon);
		this.theAnswer = answer;
	}

	public void clearDeck() {
		deck.clear();
		
	}

	public void clearHands() {
		for(Player p: players) {
			p.clearHand();
		}
		
	}
	public static int getRow() {
		return numRows;
	}
	public static int getColumn() {
		return numColumns;
	}

}