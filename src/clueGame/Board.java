package clueGame;

public class Board{
    /*
    * variable and methods used for singleton pattern
    */
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
    public void initialize()
    {
    }
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	public void loadLayoutConfig() {
		// TODO Auto-generated method stub
		
	}
}