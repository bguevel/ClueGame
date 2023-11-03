package clueGame;

import java.awt.Color;
import java.util.function.BooleanSupplier;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, Color c, int row, int column) {
		super(name, c, row, column);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BooleanSupplier isHuman() {
		// TODO Auto-generated method stub
		return null;
	}

}
