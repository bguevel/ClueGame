package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

public class Board{
	private String layoutConfigFile;
	private String setUpConfigFile;
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	Map<Character, Room> roomMap;
    private static Board theInstance = new Board();
	public void setConfigFiles(String string, String string2) {
		this.layoutConfigFile = string;
		this.setUpConfigFile = string2;
		
	}
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
    public void initialize()
    {
    }
	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return null;
	}
	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}
	public void loadSetupConfig() {
		FileReader file;
		String temp;
		String[] arr;
		try {
			file = new FileReader(setUpConfigFile);
			Scanner reader = new Scanner(file);
			while(reader.hasNext()) {
				temp = reader.nextLine();
				if(temp.charAt(0)=='/') {
					continue;
				}
				arr = temp.split(", ");
				Character temp2 = arr[3].charAt(0);
				this.roomMap.put(temp2, new Room(arr[1]));
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void loadLayoutConfig() {
		
		
	}
}