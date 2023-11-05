package clueGame;

import java.awt.Color;
import java.util.function.BooleanSupplier;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String c, int row, int column, boolean isHuman) {
		super(name, c, row, column, isHuman);
	}
	
	public Solution createSuggestion() {
		return null;
	}
	
	public BoardCell selectTargets() {
		return null;
	}
}
